package estructuras;

public class Agrupacion {
    private String nombre, cod;
    private  int votos;
    public Agrupacion (String codigo, String nombre) {
        this.nombre = nombre;
        this.cod = codigo;
        this.votos = 0;
    }
    public void sumarVotos(String votos){
        Integer n = Integer.parseInt(votos);
        if(n>0){
            this.votos+=n;
        }

    }

    public String getNombre() {
        return nombre;
    }

    public int getVotos() {
        return votos;
    }

    @Override
    public String toString() {
        return "Agrupación{" +
                "Nombre='" + nombre + '\'' +
                ", Código='" + cod+ '\'' +
                ", Votos=" + votos +
                '}';
    }
}
