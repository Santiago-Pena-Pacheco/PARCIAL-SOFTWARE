package modelo;

public abstract class Producto {
    private String nombre;
    private double precio;

    protected Producto(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }
    public abstract String getTipo();
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
    @Override public String toString() { return nombre + " ($" + (long) precio + ")"; }
}
