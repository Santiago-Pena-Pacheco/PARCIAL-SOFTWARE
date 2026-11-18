package fabrica;

import java.util.ArrayList;
import modelo.*;

// PATRÓN FACTORY METHOD - rol: Creator. crearPedido() usa armar(), que define cada subclase.
public abstract class FabricaCombo {
    public Pedido crearPedido(PedidoBuilder builder, ArrayList<Producto> menu) {
        armar(builder, menu);
        return builder.marcarCombo("Combo " + getNombre()).construir();
    }
    protected abstract void armar(PedidoBuilder b, ArrayList<Producto> menu);
    public abstract String getNombre();

    // Busca un producto del menú por nombre (así el combo usa siempre el precio vigente)
    protected Producto buscar(ArrayList<Producto> menu, String nombre) {
        for (Producto p : menu) if (p.getNombre().equalsIgnoreCase(nombre)) return p;
        throw new IllegalStateException("El producto no existe en el menú: " + nombre);
    }
}
