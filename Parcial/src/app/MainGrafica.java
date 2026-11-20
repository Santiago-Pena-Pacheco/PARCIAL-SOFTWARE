package app;

import grafica.Grafica;
import servicio.Restaurante;
import javax.swing.JOptionPane;

// Punto de entrada de la versión gráfica
public class MainGrafica {
    public static void main(String[] args) {
        String usuario = JOptionPane.showInputDialog("Usuario");
        String clave = JOptionPane.showInputDialog("Clave");
        if (usuario == null || clave == null || !usuario.equals("admin") || !clave.equals("1234")) {
            JOptionPane.showMessageDialog(null, "Acceso denegado");
            return;
        }
        Object[] dias = { "lunes", "martes", "miércoles", "jueves", "viernes", "sábado", "domingo" };
        Object dia = JOptionPane.showInputDialog(null, "Día de la jornada", "Día",
                JOptionPane.QUESTION_MESSAGE, null, dias, dias[0]);
        if (dia == null) return;
        new Grafica(new Restaurante((String) dia)).ejecutar();
    }
}
