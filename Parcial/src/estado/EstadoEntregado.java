package estado;

// Concrete State: ya está en la mesa. Estado final: no avanza ni se cancela (todo por defecto).
public class EstadoEntregado extends EstadoPedido {
    @Override public String getNombre() { return "ENTREGADO"; }
}
