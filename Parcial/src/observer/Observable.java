package observer;

import java.util.ArrayList;

// PATRÓN OBSERVER - rol: Subject reutilizable
public abstract class Observable<T> {
    private ArrayList<Observador<T>> observadores = new ArrayList<>();

    public void suscribir(Observador<T> o) {
        if (o != null && !observadores.contains(o)) observadores.add(o);
    }
    public void desuscribir(Observador<T> o) { observadores.remove(o); }

    protected void notificar(T evento) {
        ArrayList<Observador<T>> copia = new ArrayList<>(observadores); // permite (des)suscribir mientras se notifica
        for (Observador<T> o : copia) {
            try {
                o.actualizar(evento);
            } catch (RuntimeException e) { // un observador que falla no impide avisar a los demás
                System.out.println("  [Aviso] Un observador falló: " + e.getMessage());
            }
        }
    }
}
