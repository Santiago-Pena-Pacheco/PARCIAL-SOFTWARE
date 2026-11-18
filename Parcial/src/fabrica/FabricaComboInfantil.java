package fabrica;

import java.util.ArrayList;
import modelo.*;

// Concrete Creator: Combo Infantil = nuggets + jugo + helado
public class FabricaComboInfantil extends FabricaCombo {
    @Override protected void armar(PedidoBuilder b, ArrayList<Producto> menu) {
        b.agregarPlato(buscar(menu, "Nuggets")).agregarBebida(buscar(menu, "Jugo"), 1)
         .agregarComplemento(buscar(menu, "Helado"));
    }
    @Override public String getNombre() { return "infantil"; }
}
