package estructuras;

import java.io.File;
import java.util.Scanner;

public class Archivos {
    private String path;
    private static String filtroCargo = "000100000000000";
    public Archivos(String path){ this.path=path; };

    public TSBHashtableDA cargaAgrupaciones(){
        File file = new File(path);
        TSBHashtableDA agrupaciones = new TSBHashtableDA();
        Agrupacion agrupacion;
        try {
            Scanner scanner = new Scanner(file).useDelimiter("\\n");
            while (scanner.hasNext()){
                Object table [] = scanner.next().split("\\|");
                if(table[0].equals(filtroCargo)){
                    agrupacion = new Agrupacion((String)table[2], (String) table[3]);
                    agrupaciones.put((String)table[2], agrupacion);
                }
            }

        }catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
        return agrupaciones;
    }
    public TSBHashtableDA cargaRegiones(){
        File file = new File(path);
        TSBHashtableDA distritos = new TSBHashtableDA();

        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] campos = linea.split("\\|");
                String codigo = campos[0];
                String nombre = campos[1];
                if(codigo.length() == 2){
                    Region distrito = (Region) distritos.get(codigo);
                    if(distrito == null){
                        distrito = new Region((String) codigo, (String) nombre);
                        distritos.put(codigo, distrito);

                    }else{
                        distrito.setNombre(nombre);
                    }
                }else if(codigo.length()==5){
                    String codDistrito = codigo.substring(0, 2);
                    String codSeccion = codigo.substring(2);
                    Region distrito = (Region) distritos.get(codDistrito);
                    if (distrito == null) {
                        distrito = new Region(codDistrito, "");
                        distritos.put(codDistrito, distrito);
                    }
                    Region seccion = (Region) distrito.getSubregiones().get(codSeccion);
                    if (seccion == null) {
                        seccion = new Region(codSeccion, campos[1]);
                        distrito.getSubregiones().put(codSeccion, seccion);
                    } else {
                        seccion.setNombre(campos[1]);
                    }
                }else if(codigo.length() == 11){
                    String codDistrito = codigo.substring(0, 2);
                    String codSeccion = codigo.substring(2, 5);
                    String codCircuito = codigo.substring(5);
                    Region distrito = (Region) distritos.get(codDistrito);
                    if (distrito == null) {
                        distrito = new Region(codDistrito, "");
                        distritos.put(codDistrito, distrito);
                    }
                    Region seccion = (Region) distrito.getSubregiones().get(codSeccion);
                    if (seccion == null) {
                        seccion = new Region(codSeccion, campos[1]);
                        distrito.getSubregiones().put(codSeccion, seccion);
                    }
                    Region circuito = (Region) seccion.getSubregiones().get(codCircuito);
                    if(circuito == null){
                        circuito = new Region(codCircuito, nombre);
                        seccion.getSubregiones().put(codCircuito, circuito);
                    }
                }

            }

        }catch (Exception e){
            System.out.println("Error: " + e);
        }
        return distritos;
    }

    public void escrutinio(TSBHashtableDA regiones, TSBHashtableDA agrupaciones){
        File file = new File(path);
        try {
            String codigoDistrito, codigoSeccion, codigoCircuito, codigoMesa, codigoCategoria, codigoAgrupacion;
            String votosAgrupacion;
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String linea = scanner.nextLine();
                String[] campos = linea.split("\\|");
                codigoDistrito = campos[0];
                codigoSeccion = campos[1].substring(2, 5);
                codigoCircuito = campos[2].substring(5);
                codigoCategoria = campos[4];
                codigoAgrupacion = campos[5];
                votosAgrupacion = campos[6];
                if(codigoCategoria.equals(filtroCargo)){
                    Agrupacion agrupacion = (Agrupacion) agrupaciones.get(codigoAgrupacion);
                    agrupacion.sumarVotos(votosAgrupacion);

                    Region distrito = (Region) regiones.get(codigoDistrito);
                    Agrupacion agrupacionDistrito = (Agrupacion) distrito.getAgrupaciones().get(codigoAgrupacion);
                    if(agrupacionDistrito == null){
                        agrupacionDistrito = new Agrupacion(codigoAgrupacion, agrupacion.getNombre());
                        distrito.getAgrupaciones().put(codigoAgrupacion, agrupacionDistrito);
                    }
                    agrupacionDistrito.sumarVotos(votosAgrupacion);

                    Region seccion = (Region) distrito.getSubregiones().get(codigoSeccion);
                    Agrupacion agrupacionSeccion = (Agrupacion) seccion.getAgrupaciones().get(codigoAgrupacion);
                    if(agrupacionSeccion == null){
                        agrupacionSeccion = new Agrupacion(codigoAgrupacion, agrupacion.getNombre());
                        seccion.getAgrupaciones().put(codigoAgrupacion, agrupacionSeccion);
                    }
                    agrupacionSeccion.sumarVotos(votosAgrupacion);

                    Region circuito = (Region) seccion.getSubregiones().get(codigoCircuito);
                    Agrupacion agrupacionCircuito = (Agrupacion) circuito.getAgrupaciones().get(codigoAgrupacion);
                    if(agrupacionCircuito == null){
                        agrupacionCircuito = new Agrupacion(codigoAgrupacion, agrupacion.getNombre());
                        circuito.getAgrupaciones().put(codigoAgrupacion, agrupacionCircuito);
                    }
                    agrupacionCircuito.sumarVotos(votosAgrupacion);

                }
            }
        }catch (Exception e){
            System.out.println("Error: " + e);
        }


    }
}
