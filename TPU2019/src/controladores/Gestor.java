package controladores;

import estructuras.Agrupacion;
import estructuras.Region;
import estructuras.TSBHashtableDA;
import estructuras.Archivos;

import java.util.Collection;
import java.util.Iterator;

public class Gestor {
    private TSBHashtableDA agrupaciones;
    private TSBHashtableDA regiones;


    public Collection<Region> obtenerAgrupaciones(String path){
        Archivos filesUploader = new Archivos(path);
        agrupaciones = filesUploader.cargaAgrupaciones();
        return (Collection<Region>) agrupaciones.values();
    }

    public Collection<Region> obtenerRegiones(String path){
        Archivos filesUploader = new Archivos(path);
        regiones = filesUploader.cargaRegiones();
        return (Collection<Region>) regiones.values();
    }
    public void contarVotos(String path){
        Archivos filesUploader = new Archivos(path);
        filesUploader.escrutinio(regiones, agrupaciones);
    }
    public TSBHashtableDA getAgrupaciones() {
        return agrupaciones;
    }

    public TSBHashtableDA getRegiones() {
        return regiones;
    }

    public Collection<Region> filtrarSecciones(String cod){
        Region re = (Region)regiones.get(cod);
        return (Collection<Region>) re.getSubregiones().values();

    }

    public Collection<Region> filtrarCircuitos(String codDist,String codSec){
        Region dist = (Region)regiones.get(codDist);
        Region sec = (Region) dist.getSubregiones().get(codSec);
        return (Collection<Region>) sec.getSubregiones().values();
    }

}
