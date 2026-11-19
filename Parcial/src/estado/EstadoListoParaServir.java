package estado;

import modelo.Pedido;
// Concrete State: listo en el mostrador. Puede avanzar, pero NO cancelarse (hereda el rechazo por defecto).
public class EstadoListoParaServir extends EstadoPedido {
    @Override public String getNombre() { return "LISTO_PARA_SERVIR"; }
    @Override public String getSiguiente() { return "ENTREGADO"; }
    @Override public void avanzar(Pedido p) { p.cambiarEstado(new EstadoEntregado()); }
}
