package soporte;

import entidades.Agrupacion;
import entidades.Region;
import soporte.TSBHashtableDA;

import java.io.File;
import java.util.Scanner;

public class LectorEscrutinio {
    private String path;
    private static String filtroCargo = "000100000000000";
    public LectorEscrutinio(String path){ this.path=path; };


    public void contabilizarVotos(TSBHashtableDA regiones, TSBHashtableDA agrupaciones){
        File file = new File(path);
        try {
            String codigoDistrito, codigoSeccion, codigoCircuito, codigoCategoria, codigoAgrupacion;
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
