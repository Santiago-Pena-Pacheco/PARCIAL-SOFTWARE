package app;

import consola.Consola;
import servicio.Restaurante;

// Punto de entrada de la versión por consola
public class MainConsola {
    public static void main(String[] args) {
        String usuario = Consola.leer("Usuario: ");
        String clave = Consola.leer("Clave: ");
        if (!usuario.equals("admin") || !clave.equals("1234")) {
            System.out.println("Acceso denegado.");
            return;
        }
        String dia = Consola.leer("Día de la semana (lunes..domingo): ").toLowerCase();
        new Consola(new Restaurante(dia)).ejecutar();
    }
}
