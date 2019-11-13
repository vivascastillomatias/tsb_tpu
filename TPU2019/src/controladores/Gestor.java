package controladores;

import entidades.Region;
import soporte.LectorAgrupaciones;
import soporte.LectorEscrutinio;
import soporte.LectorRegiones;
import soporte.TSBHashtableDA;

import java.util.Collection;

public class Gestor {

    private TSBHashtableDA agrupaciones;
    private TSBHashtableDA regiones;


    public Collection<Region> obtenerAgrupaciones(String path){
        LectorAgrupaciones lector = new LectorAgrupaciones(path);
        agrupaciones = lector.cargaAgrupaciones();
        return (Collection<Region>) agrupaciones.values();
    }

    public Collection<Region> obtenerRegiones(String path){
        LectorRegiones lector = new LectorRegiones(path);
        regiones = lector.cargaRegiones();
        return (Collection<Region>) regiones.values();
    }

    public void contarVotos(String path){
        LectorEscrutinio lector = new LectorEscrutinio(path);
        lector.contabilizarVotos(regiones, agrupaciones);
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
