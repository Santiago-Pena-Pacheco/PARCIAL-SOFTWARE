package grafica;

import estado.EstadoPedido;
import modelo.*;
import servicio.Restaurante;
import java.util.ArrayList;
import javax.swing.JOptionPane;

// Interfaz gráfica sencilla con JOptionPane. Usa el núcleo (Restaurante).
public class Grafica {
    private final Restaurante r;
    private final PantallaGrafica avisos = new PantallaGrafica();

    public Grafica(Restaurante r) {
        this.r = r;
        r.agregarPantalla(avisos);
    }

    private String pedir(String mensaje) {
        String s = JOptionPane.showInputDialog(mensaje);
        return s == null ? "" : s;
    }
    private void decir(String mensaje) { JOptionPane.showMessageDialog(null, mensaje); }
    private String lista(ArrayList<?> datos) {
        if (datos.isEmpty()) return "(vacío)";
        String t = "";
        for (Object o : datos) t += o + "\n";
        return t;
    }

    public void ejecutar() {
        while (true) {
            String op = JOptionPane.showInputDialog(
                "RESTAURANTE (" + r.getDia() + ")\n\n"
                + "1 Ver menú\n2 Pedido combo\n3 Pedido personalizado\n4 Ver pedidos\n"
                + "5 Cambiar estado\n6 Promociones\n7 Cambiar precio\n8 Atendidos\n0 Salir");
            if (op == null || op.equals("0")) return;
            try {
                switch (op) {
                    case "1": decir(lista(r.getMenu()) + "\nCombos: " + r.getNombresCombos()); break;
                    case "2": pedidoCombo(); break;
                    case "3": pedidoPersonalizado(); break;
                    case "4": decir(lista(r.getPedidos())); break;
                    case "5": cambiarEstado(); break;
                    case "6": promociones(); break;
                    case "7": cambiarPrecio(); break;
                    case "8": decir(lista(r.atendidos())); break;
                    default: decir("Opción inválida");
                }
            } catch (Exception e) {
                decir("Error: " + e.getMessage());
            }
            avisos.mostrarYLimpiar();
        }
    }

    // El combo se elige de una lista desplegable (sin escribirlo)
    private void pedidoCombo() {
        Object combo = JOptionPane.showInputDialog(null, "Elige el combo", "Pedido combo",
                JOptionPane.QUESTION_MESSAGE, null, r.getNombresCombos().toArray(), null);
        if (combo == null) return;
        decir("Registrado: " + r.crearCombo((String) combo));
    }

    private Producto elegir(String tipo) {
        ArrayList<Object> opciones = new ArrayList<>();
        opciones.add("Ninguno");
        opciones.addAll(r.productosPorTipo(tipo));
        Object sel = JOptionPane.showInputDialog(null, "Elige " + tipo, "Pedido personalizado",
                JOptionPane.QUESTION_MESSAGE, null, opciones.toArray(), opciones.get(0));
        return sel instanceof Producto ? (Producto) sel : null;
    }

    private void pedidoPersonalizado() {
        PedidoBuilder b = r.nuevoBuilder();
        Producto plato = elegir("plato");
        if (plato != null) b.agregarPlato(plato);
        Producto bebida = elegir("bebida");
        if (bebida != null) b.agregarBebida(bebida, Integer.parseInt(pedir("¿Cuántas bebidas?")));
        Producto compl = elegir("complemento");
        if (compl != null) b.agregarComplemento(compl);
        decir("Registrado: " + r.registrar(b.construir()));
    }

    private Pedido pedirPedido() {
        Pedido p = r.buscarPedido(Integer.parseInt(pedir("ID del pedido")));
        if (p == null) decir("No existe ese pedido");
        return p;
    }

    // Cambiar estado: solo se ofrecen los estados que el estado actual permite (STATE).
    // Incluye CANCELADO cuando el pedido todavía se puede cancelar.
    private void cambiarEstado() {
        Pedido p = pedirPedido();
        if (p == null) return;
        EstadoPedido actual = p.getEstado();
        ArrayList<Object> opciones = new ArrayList<>();
        if (actual.getSiguiente() != null) opciones.add(actual.getSiguiente());
        if (actual.puedeCancelar()) opciones.add("CANCELADO");
        if (opciones.isEmpty()) {
            decir("El pedido está " + actual.getNombre() + " y ya no admite cambios de estado");
            return;
        }
        Object nuevo = JOptionPane.showInputDialog(null, "Estado actual: " + actual.getNombre() + "\nNuevo estado:",
                "Cambiar estado", JOptionPane.QUESTION_MESSAGE, null, opciones.toArray(), opciones.get(0));
        if (nuevo == null) return;
        if (nuevo.equals("CANCELADO")) cancelar(p);
        else p.avanzar();
    }

    // La cancelación ya no es una opción del menú: se hace desde "Cambiar estado"
    private void cancelar(Pedido p) {
        EstadoPedido e = p.getEstado();
        if (e.getCargoCancelacion() > 0) {
            int ok = JOptionPane.showConfirmDialog(null, "El pedido está " + e.getNombre() + ".\nSe cobrará el "
                + (int) (e.getCargoCancelacion() * 100) + "% ($" + (long) (p.getTotal() * e.getCargoCancelacion())
                + ").\n¿Cancelar de todas formas?");
            if (ok != JOptionPane.YES_OPTION) return;
        }
        p.cancelar(pedir("Motivo de la cancelación"));
        decir(p.toString());
    }

    private void promociones() {
        GestorPromociones g = GestorPromociones.getInstancia();
        int cambiar = JOptionPane.showConfirmDialog(null, g.describir() + "\n\n¿Quieres cambiarlas?");
        if (cambiar != JOptionPane.YES_OPTION) return;
        double combos = Double.parseDouble(pedir("% descuento combos (viernes)"));
        double total = Double.parseDouble(pedir("% descuento total (domingo)"));
        g.actualizar(combos, total);
        decir("Actualizado: " + g.describir());
    }

    private void cambiarPrecio() {
        Object sel = JOptionPane.showInputDialog(null, "Producto", "Cambiar precio",
                JOptionPane.QUESTION_MESSAGE, null, r.getMenu().toArray(), null);
        if (sel == null) return;
        ((Producto) sel).setPrecio(Double.parseDouble(pedir("Nuevo precio")));
    }
}
