package estado;

// Concrete State: cancelado. Estado final: no avanza ni se vuelve a cancelar (todo por defecto).
public class EstadoCancelado extends EstadoPedido {
    @Override public String getNombre() { return "CANCELADO"; }
}
