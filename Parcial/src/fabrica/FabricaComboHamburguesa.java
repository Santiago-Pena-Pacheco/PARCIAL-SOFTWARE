package fabrica;

import java.util.ArrayList;
import modelo.*;

// Concrete Creator: Combo Hamburguesa = hamburguesa + gaseosa + papas
public class FabricaComboHamburguesa extends FabricaCombo {
    @Override protected void armar(PedidoBuilder b, ArrayList<Producto> menu) {
        b.agregarPlato(buscar(menu, "Hamburguesa")).agregarBebida(buscar(menu, "Gaseosa"), 1)
         .agregarComplemento(buscar(menu, "Papas"));
    }
    @Override public String getNombre() { return "hamburguesa"; }
}
