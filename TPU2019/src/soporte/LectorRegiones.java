package soporte;

import entidades.Region;

import java.io.File;
import java.util.Scanner;

public class LectorRegiones {
    private String path;
    public LectorRegiones(String path){ this.path=path; };

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

}
