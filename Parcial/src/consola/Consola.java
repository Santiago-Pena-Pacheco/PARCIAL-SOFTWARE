package consola;

import java.util.ArrayList;
import estado.EstadoPedido;
import modelo.*;
import servicio.Restaurante;

// Interfaz de consola. Usa el MISMO núcleo (Restaurante) que la interfaz gráfica.
public class Consola {
    private final Restaurante r;

    public Consola(Restaurante r) {
        this.r = r;
        r.agregarPantalla(new PantallaConsola());
    }

    // Lectura de teclado sin Scanner (solo System.in)
    public static String leer(String mensaje) {
        System.out.print(mensaje);
        String linea = "";
        try {
            int c = System.in.read();
            if (c == -1) System.exit(0);
            while (c != -1 && c != '\n') {
                if (c != '\r') linea += (char) c;
                c = System.in.read();
            }
        } catch (Exception e) { System.exit(1); }
        return linea;
    }
    private int leerInt(String mensaje) { return Integer.parseInt(leer(mensaje)); }

    public void ejecutar() {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- RESTAURANTE (" + r.getDia() + ") ---");
            System.out.println("1 Ver menú  2 Pedido combo  3 Pedido personalizado  4 Ver pedidos");
            System.out.println("5 Cambiar estado  6 Promociones  7 Cambiar precio  8 Atendidos  0 Salir");
            try {
                switch (leer("> ")) {
                    case "1": verMenu(); break;
                    case "2": pedidoCombo(); break;
                    case "3": pedidoPersonalizado(); break;
                    case "4": mostrar(r.getPedidos()); break;
                    case "5": cambiarEstado(); break;
                    case "6": promociones(); break;
                    case "7": cambiarPrecio(); break;
                    case "8": mostrar(r.atendidos()); break;
                    case "0": salir = true; break;
                    default: System.out.println("Opción inválida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Muestra una lista numerada y devuelve la posición elegida (-1 si elige 0 = ninguno)
    private int elegir(String titulo, ArrayList<?> opciones) {
        System.out.println(titulo);
        for (int i = 0; i < opciones.size(); i++) System.out.println("  " + (i + 1) + ". " + opciones.get(i));
        int op = leerInt("  Opción (0 = ninguno): ");
        if (op < 0 || op > opciones.size()) throw new IllegalArgumentException("Opción fuera de rango");
        return op - 1;
    }

    private void verMenu() {
        for (Producto p : r.getMenu()) System.out.println("  " + p.getTipo() + ": " + p);
        System.out.println("  Combos: " + r.getNombresCombos());
    }

    private void mostrar(ArrayList<Pedido> lista) {
        if (lista.isEmpty()) System.out.println("Sin pedidos.");
        for (Pedido p : lista) System.out.println("  " + p);
    }

    // El combo se elige por número (1, 2, 3...)
    private void pedidoCombo() {
        ArrayList<String> combos = r.getNombresCombos();
        int i = elegir("Combos:", combos);
        if (i < 0) return;
        System.out.println("Registrado: " + r.crearCombo(combos.get(i)));
    }

    private Producto elegirProducto(String tipo) {
        ArrayList<Producto> lista = r.productosPorTipo(tipo);
        int i = elegir("Elige " + tipo + ":", lista);
        return i < 0 ? null : lista.get(i);
    }

    private void pedidoPersonalizado() {
        PedidoBuilder b = r.nuevoBuilder();                  // BUILDER paso a paso
        Producto plato = elegirProducto("plato");
        if (plato != null) b.agregarPlato(plato);
        Producto bebida = elegirProducto("bebida");
        if (bebida != null) b.agregarBebida(bebida, leerInt("¿Cuántas bebidas?: "));
        Producto compl = elegirProducto("complemento");
        if (compl != null) b.agregarComplemento(compl);
        System.out.println("Registrado: " + r.registrar(b.construir()));   // construir() valida
    }

    // Solo se ofrecen los estados que el estado actual permite (STATE)
    private void cambiarEstado() {
        Pedido p = r.buscarPedido(leerInt("ID del pedido: "));
        if (p == null) { System.out.println("No existe."); return; }
        EstadoPedido actual = p.getEstado();
        ArrayList<String> opciones = new ArrayList<>();
        if (actual.getSiguiente() != null) opciones.add(actual.getSiguiente());
        if (actual.puedeCancelar()) opciones.add("CANCELADO");
        if (opciones.isEmpty()) {
            System.out.println("El pedido está " + actual.getNombre() + " y ya no admite cambios de estado.");
            return;
        }
        int i = elegir("Estado actual: " + actual.getNombre() + ". Nuevo estado:", opciones);
        if (i < 0) return;
        if (opciones.get(i).equals("CANCELADO")) cancelar(p);
        else p.avanzar();
    }

    private void cancelar(Pedido p) {
        EstadoPedido e = p.getEstado();
        if (e.getCargoCancelacion() > 0) {
            System.out.println("El pedido está " + e.getNombre() + ": se cobrará el " + (int) (e.getCargoCancelacion() * 100)
                + "% ($" + (long) (p.getTotal() * e.getCargoCancelacion()) + ").");
            if (!leer("¿Cancelar de todas formas? (s/n): ").equalsIgnoreCase("s")) return;
        }
        p.cancelar(leer("Motivo de la cancelación: "));
        System.out.println(p);
    }

    private void promociones() {
        GestorPromociones g = GestorPromociones.getInstancia();   // SINGLETON
        System.out.println("Actuales: " + g.describir());
        if (!leer("¿Cambiar? (s/n): ").equalsIgnoreCase("s")) return;
        g.actualizar(Double.parseDouble(leer("% combos (viernes): ")), Double.parseDouble(leer("% total (domingo): ")));
        System.out.println("Actualizado: " + g.describir());
    }

    private void cambiarPrecio() {
        ArrayList<Producto> menu = r.getMenu();
        int i = elegir("Producto:", menu);
        if (i < 0) return;
        menu.get(i).setPrecio(Double.parseDouble(leer("Nuevo precio: ")));
    }
}
