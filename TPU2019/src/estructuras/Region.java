package estructuras;

public class Region
{
    private String cod;
    private String nombre;
    private TSBHashtableDA subregiones;
    private TSBHashtableDA agrupaciones;

    public Region(String codigo, String nombre) {
        this.cod = codigo;
        this.nombre = nombre;
        this.subregiones = new TSBHashtableDA();
        this.agrupaciones = new TSBHashtableDA();
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return cod;
    }

    public TSBHashtableDA getSubregiones() {
        return subregiones;
    }

    public TSBHashtableDA getAgrupaciones() {
        return agrupaciones;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
