package grafica;

import modelo.EventoPedido;
import observer.Observador;
import java.util.ArrayList;
import javax.swing.JOptionPane;

// PATRÓN OBSERVER - Concrete Observer para la interfaz gráfica.
// Va guardando los avisos y los muestra juntos en una ventanita cuando termina la acción.
public class PantallaGrafica implements Observador<EventoPedido> {
    private ArrayList<String> mensajes = new ArrayList<>();

    @Override public void actualizar(EventoPedido e) {
        String cambio = e.esCreacion() ? "NUEVO" : e.getEstadoAnterior().getNombre() + " -> " + e.getEstadoNuevo().getNombre();
        mensajes.add("Pedido #" + e.getPedido().getId() + ": " + cambio);
    }

    public void mostrarYLimpiar() {
        if (mensajes.isEmpty()) return;
        String texto = "";
        for (String m : mensajes) texto += m + "\n";
        JOptionPane.showMessageDialog(null, texto, "Avisos de pedidos", JOptionPane.INFORMATION_MESSAGE);
        mensajes.clear();
    }
}
