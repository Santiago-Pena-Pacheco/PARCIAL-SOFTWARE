package modelo;

import java.util.ArrayList;

// PATRÓN BUILDER - rol: Builder (el producto es Pedido)
public class PedidoBuilder {
    private final int id;
    private final String dia;
    private final ArrayList<Producto> items = new ArrayList<>();
    private String nombreCombo = "Personalizado";
    private boolean esCombo;

    public PedidoBuilder(int id, String dia) { this.id = id; this.dia = dia; }

    public PedidoBuilder agregarPlato(Producto plato) { items.add(plato); return this; }
    public PedidoBuilder agregarBebida(Producto bebida, int cantidad) {
        for (int i = 0; i < cantidad; i++) items.add(bebida);
        return this;
    }
    public PedidoBuilder agregarComplemento(Producto complemento) { items.add(complemento); return this; }
    public PedidoBuilder marcarCombo(String nombre) { this.esCombo = true; this.nombreCombo = nombre; return this; }

    public Pedido construir() {
        if (items.isEmpty()) throw new IllegalStateException("El pedido debe tener al menos un producto");
        return new Pedido(id, dia, new ArrayList<>(items), esCombo, nombreCombo);
    }
}
