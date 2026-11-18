package modelo;

public class Complemento extends Producto {
    public Complemento(String nombre, double precio) { super(nombre, precio); }
    @Override public String getTipo() { return "complemento"; }
}
