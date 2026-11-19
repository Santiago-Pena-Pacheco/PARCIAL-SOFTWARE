package estado;

import modelo.Pedido;
// Concrete State: recién registrado. Puede avanzar y cancelarse sin costo.
public class EstadoRegistrado extends EstadoPedido {
    @Override public String getNombre() { return "REGISTRADO"; }
    @Override public void avanzar(Pedido p) { p.cambiarEstado(new EstadoEnPreparacion()); }
    @Override public String getSiguiente() { return "EN_PREPARACION"; }
    @Override public boolean puedeCancelar() { return true; }
    @Override public void cancelar(Pedido p, String motivo) { p.registrarCancelacion(motivo, getCargoCancelacion()); }
}
