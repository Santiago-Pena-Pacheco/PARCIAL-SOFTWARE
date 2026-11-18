package fabrica;

import java.util.ArrayList;
import modelo.*;

// Concrete Creator: Combo Familiar = 2 pizzas + 2 gaseosas + papas
public class FabricaComboFamiliar extends FabricaCombo {
    @Override protected void armar(PedidoBuilder b, ArrayList<Producto> menu) {
        b.agregarPlato(buscar(menu, "Pizza")).agregarPlato(buscar(menu, "Pizza"))
         .agregarBebida(buscar(menu, "Gaseosa"), 2).agregarComplemento(buscar(menu, "Papas"));
    }
    @Override public String getNombre() { return "familiar"; }
}
