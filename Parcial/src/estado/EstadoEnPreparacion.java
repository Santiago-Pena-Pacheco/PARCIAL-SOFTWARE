package estado;

import modelo.Pedido;
// Concrete State: en cocina. Puede avanzar y cancelarse, pero se cobra el 25 %.
public class EstadoEnPreparacion extends EstadoPedido {
    @Override public String getNombre() { return "EN_PREPARACION"; }
    @Override public void avanzar(Pedido p) { p.cambiarEstado(new EstadoListoParaServir()); }
    @Override public String getSiguiente() { return "LISTO_PARA_SERVIR"; }
    @Override public boolean puedeCancelar() { return true; }
    @Override public double getCargoCancelacion() { return 0.25; }
    @Override public void cancelar(Pedido p, String motivo) { p.registrarCancelacion(motivo, getCargoCancelacion()); }
}
