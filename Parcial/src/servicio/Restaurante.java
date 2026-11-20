package servicio;

import modelo.*;
import estado.EstadoEntregado;
import fabrica.*;
import observer.Observador;
import java.util.ArrayList;

// Núcleo del negocio (sin interfaz)
public class Restaurante {
    private final String dia;
    private final ArrayList<Producto> menu = new ArrayList<>();
    private final ArrayList<FabricaCombo> combos = new ArrayList<>();
    private final ArrayList<Pedido> pedidos = new ArrayList<>();
    private final ArrayList<Observador<EventoPedido>> pantallas = new ArrayList<>();
    private int contador = 1;

    public Restaurante(String dia) {
        this.dia = dia;
        menu.add(new Plato("Hamburguesa", 18000));
        menu.add(new Plato("Pizza", 25000));
        menu.add(new Plato("Nuggets", 12000));
        menu.add(new Bebida("Gaseosa", 5000));
        menu.add(new Bebida("Jugo", 6000));
        menu.add(new Complemento("Papas", 7000));
        menu.add(new Complemento("Helado", 6000));
        // Para un combo nuevo: crear su fábrica y registrarla aquí
        combos.add(new FabricaComboHamburguesa());
        combos.add(new FabricaComboFamiliar());
        combos.add(new FabricaComboInfantil());
    }

    public void agregarPantalla(Observador<EventoPedido> o) { pantallas.add(o); }

    public Pedido crearCombo(String nombre) {
        for (FabricaCombo f : combos)
            if (f.getNombre().equals(nombre)) return registrar(f.crearPedido(nuevoBuilder(), menu));
        return null;
    }
    public PedidoBuilder nuevoBuilder() { return new PedidoBuilder(contador++, dia); }

    public Pedido registrar(Pedido p) {
        for (Observador<EventoPedido> o : pantallas) p.suscribir(o);
        pedidos.add(p);
        p.anunciarCreacion();
        return p;
    }

    public Pedido buscarPedido(int id) {
        for (Pedido p : pedidos) if (p.getId() == id) return p;
        return null;
    }
    public Producto buscarProducto(String nombre) {
        for (Producto p : menu) if (p.getNombre().equalsIgnoreCase(nombre)) return p;
        return null;
    }
    public ArrayList<Pedido> atendidos() {
        ArrayList<Pedido> lista = new ArrayList<>();
        for (Pedido p : pedidos) if (p.getEstado() instanceof EstadoEntregado) lista.add(p);
        return lista;
    }
    public ArrayList<Producto> productosPorTipo(String tipo) {
        ArrayList<Producto> lista = new ArrayList<>();
        for (Producto p : menu) if (p.getTipo().equals(tipo)) lista.add(p);
        return lista;
    }
    public ArrayList<String> getNombresCombos() {
        ArrayList<String> nombres = new ArrayList<>();
        for (FabricaCombo f : combos) nombres.add(f.getNombre());
        return nombres;
    }

    public String getDia() { return dia; }
    public ArrayList<Producto> getMenu() { return menu; }
    public ArrayList<Pedido> getPedidos() { return pedidos; }
}
