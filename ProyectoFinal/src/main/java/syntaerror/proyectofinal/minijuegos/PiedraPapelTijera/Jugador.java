package syntaerror.proyectofinal.minijuegos.PiedraPapelTijera;

public class Jugador {

    private String nombre;
    private int puntos;
    private int victorias;
    private int derrotas;
    private int empates;

    public Jugador(String nombre) {
        this.nombre = nombre;
        this.puntos = 0;
        this.victorias = 0;
        this.derrotas = 0;
        this.empates = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public int getPuntos() {
        return puntos;
    }

    public int getVictorias() {
        return victorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public int getEmpates() {
        return empates;
    }

    public void ganar() {
        puntos++;
        victorias++;
    }

    public void perder() {
        derrotas++;
    }

    public void empatar() {
        empates++;
    }

    public void reiniciar() {
        puntos = 0;
        victorias = 0;
        derrotas = 0;
        empates = 0;
    }
}
