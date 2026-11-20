package consola;

import modelo.EventoPedido;
import observer.Observador;

// PATRÓN OBSERVER - Concrete Observer para la consola: imprime cada cambio de estado
public class PantallaConsola implements Observador<EventoPedido> {
    @Override public void actualizar(EventoPedido e) {
        String cambio = e.esCreacion() ? "NUEVO" : e.getEstadoAnterior().getNombre() + " -> " + e.getEstadoNuevo().getNombre();
        System.out.println("  [Aviso] Pedido #" + e.getPedido().getId() + ": " + cambio);
    }
}
