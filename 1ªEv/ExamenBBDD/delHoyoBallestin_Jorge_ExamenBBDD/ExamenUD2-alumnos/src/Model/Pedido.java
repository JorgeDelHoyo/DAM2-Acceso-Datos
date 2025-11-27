package Model;

public class Pedido {
    private int id;
    private double importe;
    private int idCliente; // relación 1-N con Model.Cliente

    public Pedido() {}

    public Pedido(int id, double importe, int idCliente) {
        this.id = id;
        this.importe = importe;
        this.idCliente = idCliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "Model.Pedido{" +
                "id=" + id +
                ", importe=" + importe +
                ", idCliente=" + idCliente +
                '}';
    }
}

