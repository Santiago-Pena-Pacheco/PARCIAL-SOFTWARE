package modelo;

import observer.Observable;
import estado.*;
import java.util.ArrayList;

// PATRÓN OBSERVER - rol: Concrete Subject
// PATRÓN STATE    - rol: Context (delega avanzar/cancelar en su estado actual)
public class Pedido extends Observable<EventoPedido> {
    private final int id;
    private final String dia, nombreCombo;
    private final boolean esCombo;
    private final ArrayList<Producto> items;
    private EstadoPedido estado = new EstadoRegistrado();
    private String motivoCancelacion = "";
    private double cargoCancelacion = 0;

    // Solo debe usarlo PedidoBuilder
    Pedido(int id, String dia, ArrayList<Producto> items, boolean esCombo, String nombreCombo) {
        this.id = id; this.dia = dia; this.items = items;
        this.esCombo = esCombo; this.nombreCombo = nombreCombo;
    }

    public void anunciarCreacion() { notificar(new EventoPedido(this, null, estado)); }

    // ---- Acciones públicas: no preguntan "¿en qué estado estoy?", delegan en el estado (STATE) ----
    public void avanzar() { estado.avanzar(this); }
    public void cancelar(String motivo) { estado.cancelar(this, motivo); }

    // ---- Métodos que usan los estados para cambiar el contexto ----
    public void cambiarEstado(EstadoPedido nuevo) {
        EstadoPedido anterior = estado;
        estado = nuevo;
        notificar(new EventoPedido(this, anterior, nuevo));
    }
    public void registrarCancelacion(String motivo, double porcentajeCargo) {
        this.motivoCancelacion = motivo;
        this.cargoCancelacion = getTotal() * porcentajeCargo;
        cambiarEstado(new EstadoCancelado());
    }

    public double getSubtotal() {
        double s = 0;
        for (Producto p : items) s += p.getPrecio();
        return s;
    }
    public double getTotal() { return getSubtotal() - GestorPromociones.getInstancia().calcularDescuento(this); }

    public int getId() { return id; }
    public String getDia() { return dia; }
    public boolean isEsCombo() { return esCombo; }
    public EstadoPedido getEstado() { return estado; }
    public ArrayList<Producto> getItems() { return items; }

    @Override public String toString() {
        String texto = "#" + id + " " + nombreCombo + " | " + estado.getNombre() + " | total $" + (long) getTotal();
        if (estado instanceof EstadoCancelado) {
            texto += " | motivo: " + motivoCancelacion;
            if (cargoCancelacion > 0) texto += " | cobro por cancelación $" + (long) cargoCancelacion;
        }
        return texto;
    }
}
