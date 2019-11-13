package soporte;

import entidades.Agrupacion;

import java.io.File;
import java.util.Scanner;

public class LectorAgrupaciones {
    private String path;
    private static String filtroCargo = "000100000000000";
    public LectorAgrupaciones(String path){ this.path=path; };

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
}
