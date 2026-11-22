# Sistema de gestión de pedidos de un restaurante

Aplicación en Java para gestionar los pedidos de un restaurante, con interfaz por consola e interfaz gráfica sencilla (JOptionPane). Ambas usan el mismo núcleo y aplican cinco patrones de diseño.

## Requisitos
- JDK 8 o superior (comprueba con `javac -version`).
- No se necesitan librerías externas. Solo se usan `java.util.ArrayList` y, en la versión gráfica, `javax.swing.JOptionPane`.

## Compilar
Desde la carpeta del proyecto (la que contiene `src`):

**Linux / macOS**
```
javac -encoding UTF-8 -d out src/*/*.java
```

**Windows (cmd)**
```
dir /s /b src\*.java > fuentes.txt
javac -encoding UTF-8 -d out @fuentes.txt
```

## Ejecutar
```
java -cp out app.MainConsola     (versión por consola)
java -cp out app.MainGrafica     (versión con interfaz gráfica)
```
- Usuario: `admin`
- Clave: `1234`
- Al iniciar se elige el día de la semana de la jornada (de él depende la promoción que se aplica).

## Funcionalidades implementadas
- **Acceso:** inicio de sesión con usuario y contraseña.
- **Menú:** ver los platos, bebidas, complementos y combos disponibles.
- **Pedido con combo:** el combo se elige de una lista (Hamburguesa, Familiar o Infantil). (USUARIO SIMULADO)
- **Pedido personalizado:** se elige un plato, una bebida con cantidad y un complemento. El pedido debe tener al menos un producto. (USUARIO SIMULADO)
- **Consulta de pedidos:** ver todos los pedidos con su estado y total, y ver los pedidos atendidos (entregados).
- **Cambio de estado:** el administrador avanza el pedido en el orden Registrado, En preparación, Listo para servir y Entregado. Solo se ofrecen los cambios que el estado actual permite.
- **Cancelación con motivo** (desde «Cambiar estado»):
  - Registrado: se cancela sin costo.
  - En preparación: se cancela y se cobra el 25 % del total.
  - Listo para servir, Entregado y Cancelado: no se puede cancelar.
- **Promociones por día:** martes 2x1 en bebidas, viernes 15 % en combos y domingo 10 % sobre el total. El cálculo se hace automáticamente en el total del pedido.
- **Administración:** cambiar los porcentajes de descuento de combos y del total, y cambiar el precio de los productos del menú.
- **Avisos:** cada cambio de estado, y cada pedido nuevo, genera un aviso en pantalla (Observer).

## No incluido
- Persistencia de datos (todo vive en memoria durante la ejecución).
- Gestión de varios usuarios (el acceso es un único usuario fijo).
- Simulación de tiempo y tiempo estimado de preparación.
- Pruebas unitarias (pendientes).

## Estructura del proyecto
| Paquete | Contenido |
|---|---|
| `modelo` | `Producto`, `Plato`, `Bebida`, `Complemento`, `Pedido`, `PedidoBuilder`, `EventoPedido`, `GestorPromociones` |
| `estado` | `EstadoPedido` y los estados concretos (patrón State) |
| `observer` | `Observable` y `Observador` (patrón Observer) |
| `fabrica` | `FabricaCombo` y las fábricas de cada combo (patrón Factory Method) |
| `servicio` | `Restaurante`, el núcleo del negocio |
| `consola` | `Consola`, `PantallaConsola` |
| `grafica` | `Grafica`, `PantallaGrafica` |
| `app` | `MainConsola`, `MainGrafica` (puntos de entrada) |

Los paquetes `modelo`, `estado`, `observer`, `fabrica` y `servicio` forman el **núcleo**, que no depende de ninguna interfaz. `consola` y `grafica` solo se encargan de la presentación y usan el mismo `Restaurante`.

## Patrones de diseño
| Patrón | Categoría | Clases |
|---|---|---|
| Factory Method | Creacional | `FabricaCombo` y sus fábricas concretas |
| Builder | Creacional | `PedidoBuilder` |
| Singleton | Creacional | `GestorPromociones` |
| Observer | Comportamiento | `Observable`, `Observador`, `Pedido`, `PantallaConsola`, `PantallaGrafica` |
| State | Comportamiento | `EstadoPedido` y sus estados concretos |
