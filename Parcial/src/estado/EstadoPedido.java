package estado;

import modelo.Pedido;
// PATRÓN STATE - rol: State (clase abstracta)
// Define lo que se puede hacer con un pedido según su fase. Por defecto NO se puede avanzar ni cancelar;
// cada estado concreto sobrescribe solo lo que sí permite. Así no hay if/switch sobre el estado en Pedido.
public abstract class EstadoPedido {
    public abstract String getNombre();

    public void avanzar(Pedido pedido) {
        throw new IllegalStateException("Un pedido " + getNombre() + " no puede avanzar");
    }
    public void cancelar(Pedido pedido, String motivo) {
        throw new IllegalStateException("No se puede cancelar un pedido " + getNombre());
    }
    public boolean puedeCancelar() { return false; }
    public String getSiguiente() { return null; }   // nombre del estado al que puede avanzar (null = ninguno)
    public double getCargoCancelacion() { return 0; }   // fracción del total que se cobra al cancelar
}
