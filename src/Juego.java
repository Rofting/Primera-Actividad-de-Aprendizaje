import java.util.Scanner;

public class Juego {
    int vidasJugador1 = 3;
    int vidasJugador2 = 3;
    boolean movimientoCircular;

    public static void main(String[] args) {
        Juego juego = new Juego();
        Scanner scanner = new Scanner(System.in);

        // Configuración inicial
        boolean movimientoCircularValido = false;
        char respuesta;

        while (!movimientoCircularValido) {
            System.out.print("¿Deseas que los movimientos sean circulares? (S/N): ");
            respuesta = scanner.next().charAt(0);

            if (respuesta == 'S' || respuesta == 's') {
                juego.movimientoCircular = true;
                movimientoCircularValido = true;
            } else if (respuesta == 'N' || respuesta == 'n') {
                juego.movimientoCircular = false;
                movimientoCircularValido = true;
            } else {
                System.out.println("Entrada no válida. Ingresa 'S' o 'N'.");
            }
        }

        char[][] tableroJugador1 = new char[6][6];
        char[][] tableroJugador2 = new char[6][6];

        // Inicializar tableros
        Tablero.inicializarTablero(tableroJugador1);
        Tablero.inicializarTablero(tableroJugador2);

        // Agregar Enemigos
        Tablero.agregarEnemigos(tableroJugador1, 8);
        Tablero.agregarEnemigos(tableroJugador2, 8);

        // Agregar Vidas Extra
        Tablero.agregarVidasExtra(tableroJugador1, 2);
        Tablero.agregarVidasExtra(tableroJugador2, 2);

        // Agregar Usuarios
        Tablero.agregarUsuario(tableroJugador1, 'A');
        Tablero.agregarUsuario(tableroJugador2, 'B');

        // Mostrar tableros al inicio
        System.out.println("Tablero del Jugador 1:");
        Tablero.mostrarTablero(tableroJugador1, false);

        System.out.println("\nTablero del Jugador 2:");
        Tablero.mostrarTablero(tableroJugador2, false);

        // Comienza el juego por turnos
        boolean finDelJuego = false;
        char jugadorActual = 'A';

        while (!finDelJuego) {
            System.out.println("\nTurno del Jugador " + jugadorActual);
            System.out.println("Estado de tu tablero:");
            if (jugadorActual == 'A') {
                Tablero.mostrarTablero(tableroJugador1, false);
                finDelJuego = juego.moverJugador(tableroJugador1, 'A', scanner);
            } else {
                Tablero.mostrarTablero(tableroJugador2, false);
                finDelJuego = juego.moverJugador(tableroJugador2, 'B', scanner);
            }

            if (!finDelJuego) {
                // Cambiar al siguiente jugador
                jugadorActual = (jugadorActual == 'A') ? 'B' : 'A';
            }
        }

        scanner.close();
    }

    public boolean moverJugador(char[][] tablero, char jugador, Scanner scanner) {
        int[] posicionActual = Tablero.encontrarJugador(tablero, jugador);
        int filaActual = posicionActual[0];
        int columnaActual = posicionActual[1];

        // Solicitar movimiento al usuario
        System.out.print("Ingresa tu movimiento (número de casillas dirección): ");
        int cantidadCasillas = scanner.nextInt();
        String direccionStr = scanner.next().toUpperCase();
        char direccion = direccionStr.charAt(0);

        int nuevaFila = filaActual;
        int nuevaColumna = columnaActual;

        // Movimiento según la dirección especificada
        switch (direccion) {
            case 'D':
                nuevaColumna = columnaActual + cantidadCasillas;
                if (movimientoCircular) {
                    nuevaColumna %= tablero[0].length;
                } else if (nuevaColumna >= tablero[0].length) {
                    nuevaColumna = tablero[0].length - 1;
                }
                break;
            case 'A':
                nuevaColumna = columnaActual - cantidadCasillas;
                if (movimientoCircular) {
                    nuevaColumna = (nuevaColumna + tablero[0].length) % tablero[0].length;
                } else if (nuevaColumna < 0) {
                    nuevaColumna = 0;
                }
                break;
            case 'W':
                nuevaFila = filaActual - cantidadCasillas;
                if (movimientoCircular) {
                    nuevaFila = (nuevaFila + tablero.length) % tablero.length;
                } else if (nuevaFila < 0) {
                    nuevaFila = 0;
                }
                break;
            case 'S':
                nuevaFila = filaActual + cantidadCasillas;
                if (movimientoCircular) {
                    nuevaFila %= tablero.length;
                } else if (nuevaFila >= tablero.length) {
                    nuevaFila = tablero.length - 1;
                }
                break;
            default:
                System.out.println("Dirección no válida");
                return false;
        }

        // Chequear si hay un enemigo en la nueva posición
        if (tablero[nuevaFila][nuevaColumna] == 'E') {
            if (jugador == 'A') {
                vidasJugador1--;
                System.out.println("¡Jugador A ha encontrado un enemigo y ha perdido una vida! Vidas restantes: " + vidasJugador1);
            } else {
                vidasJugador2--;
                System.out.println("¡Jugador B ha encontrado un enemigo y ha perdido una vida! Vidas restantes: " + vidasJugador2);
            }
        }

        // Chequear si hay una vida extra en la nueva posición
        if (tablero[nuevaFila][nuevaColumna] == 'V') {
            if (jugador == 'A') {
                vidasJugador1++;
                System.out.println("¡Jugador A ha encontrado una vida extra! Vidas actuales: " + vidasJugador1);
            } else {
                vidasJugador2++;
                System.out.println("¡Jugador B ha encontrado una vida extra! Vidas actuales: " + vidasJugador2);
            }
        }

        // Verificar si el jugador llego a la salida
        if (tablero[nuevaFila][nuevaColumna] == 'S') {
            if (jugador == 'A') {
                System.out.println("¡Jugador A ha llegado a la salida! Fin del juego");
                return true;
            } else {
                System.out.println("¡Jugador B ha llegado a la salida! Fin del juego");
                return true;
            }
        }

        // Mover jugador y dejar la casilla libre
        tablero[filaActual][columnaActual] = 'L';
        tablero[nuevaFila][nuevaColumna] = jugador;

        if ((jugador == 'A' && vidasJugador1 <= 0) || (jugador == 'B' && vidasJugador2 <= 0)) {
            System.out.println("¡El jugador " + jugador + " ha perdido todas sus vidas! Fin del juego.");
            return true;
        }

        // Mostrar tablero tras el movimiento
        System.out.println("Estado de tu tablero tras el movimiento:");
        Tablero.mostrarTablero(tablero, false);

        return false;
    }
}
