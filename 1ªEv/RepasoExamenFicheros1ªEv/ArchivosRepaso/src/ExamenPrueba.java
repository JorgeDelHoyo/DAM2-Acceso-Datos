import java.io.Serializable;

public class ExamenPrueba implements Serializable {
    private int id;                 // Identificador único
    private String nombre;          // Nombre del examen
    private float notaMaxima;       // Nota máxima
    private String fecha;        // Fecha del examen
    private boolean aprobado;       // Si se aprobó o no

    // Constructor
    public ExamenPrueba(int id, String nombre, float notaMaxima, String fecha, boolean aprobado) {
        this.id = id;
        this.nombre = nombre;
        this.notaMaxima = notaMaxima;
        this.fecha = fecha;
        this.aprobado = aprobado;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public float getNotaMaxima() { return notaMaxima; }
    public String getFecha() { return fecha; }
    public boolean isAprobado() { return aprobado; }

    // toString para depuración
    @Override
    public String toString() {
        return id + " - " + nombre + " - " + notaMaxima + " - " + fecha + " - " + aprobado;
    }
}
