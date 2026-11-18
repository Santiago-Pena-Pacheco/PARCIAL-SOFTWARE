package modelo;

import java.util.ArrayList;

// PATRÓN SINGLETON - una única configuración de promociones para todo el sistema
public class GestorPromociones {
    private static final GestorPromociones INSTANCIA = new GestorPromociones();
    private double pctCombos = 15, pctTotal = 10;

    private GestorPromociones() { }
    public static GestorPromociones getInstancia() { return INSTANCIA; }

    public synchronized void actualizar(double pctCombos, double pctTotal) {
        this.pctCombos = pctCombos; this.pctTotal = pctTotal;
    }

    public synchronized String describir() {
        return "martes: 2x1 bebidas | viernes: " + pctCombos + "% combos | domingo: " + pctTotal + "% total";
    }

    public synchronized double calcularDescuento(Pedido p) {
        double subtotal = p.getSubtotal();
        switch (p.getDia()) {
            case "martes": return descuento2x1(p);
            case "viernes": return p.isEsCombo() ? subtotal * pctCombos / 100 : 0;
            case "domingo": return subtotal * pctTotal / 100;
            default: return 0;
        }
    }

    // De cada par de bebidas, la más barata es gratis
    private double descuento2x1(Pedido p) {
        ArrayList<Double> precios = new ArrayList<>();
        for (Producto x : p.getItems()) if (x instanceof Bebida) precios.add(x.getPrecio());
        double descuento = 0;
        for (int gratis = precios.size() / 2; gratis > 0; gratis--) {
            int menor = 0;
            for (int i = 1; i < precios.size(); i++) if (precios.get(i) < precios.get(menor)) menor = i;
            descuento += precios.remove(menor);
        }
        return descuento;
    }
}
