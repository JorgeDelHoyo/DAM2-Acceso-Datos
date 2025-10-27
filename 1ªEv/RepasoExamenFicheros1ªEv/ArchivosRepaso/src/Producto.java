import java.io.Serializable;

public class Producto implements Serializable{
    private int id;
    private String nombre;
    private double precio;

    public Producto(){}

    Producto(int id, String nombre, double precio){
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() {return id;}
    public String getNombre() {return nombre;}
    public double getPrecio() {return precio;}

    @Override
    public String toString() {
        return id+" - "+nombre+" - "+precio;
    }
}
