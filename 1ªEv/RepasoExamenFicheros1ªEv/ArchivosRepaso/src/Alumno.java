import java.io.Serializable;

public class Alumno implements Serializable {
    private int idAlumno;
    private String nombreAumno;
    private double notaAlumno;

    public Alumno(int idAlumno, String nombreAumno, double notaAlumno) {
        this.idAlumno = idAlumno;
        this.nombreAumno = nombreAumno;
        this.notaAlumno = notaAlumno;
    }

    public int getIdAlumno() { return idAlumno; }
    public String getNombreAlumno() { return nombreAumno; }
    public double getNotaAlumno() { return notaAlumno; }

    @Override
    public String toString() {
        return idAlumno + " - " +nombreAumno+ " - " + notaAlumno;
    }
}

