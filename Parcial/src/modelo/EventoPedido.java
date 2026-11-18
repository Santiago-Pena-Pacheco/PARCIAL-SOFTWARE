package modelo;

import estado.EstadoPedido;
// Evento inmutable que viaja del Subject a los Observers (estadoAnterior es null al crear el pedido)
public final class EventoPedido {
    private final Pedido pedido;
    private final EstadoPedido estadoAnterior, estadoNuevo;

    public EventoPedido(Pedido pedido, EstadoPedido estadoAnterior, EstadoPedido estadoNuevo) {
        this.pedido = pedido; this.estadoAnterior = estadoAnterior; this.estadoNuevo = estadoNuevo;
    }
    public Pedido getPedido() { return pedido; }
    public EstadoPedido getEstadoAnterior() { return estadoAnterior; }
    public EstadoPedido getEstadoNuevo() { return estadoNuevo; }
    public boolean esCreacion() { return estadoAnterior == null; }
}
