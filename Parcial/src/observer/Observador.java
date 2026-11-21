package observer;

// PATRÓN OBSERVER - rol: Observer (T es el tipo de evento que recibe)
public interface Observador<T> {
    void actualizar(T evento);
}
