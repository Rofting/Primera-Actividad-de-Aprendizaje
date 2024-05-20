import java.util.Scanner;

public class Juego {
    int vidasJugador1 = 3;
    int vidasJugador2 = 3;

    public static void main(String[] args) {
        Juego juego = new Juego();
        char[][] tableroJugador1 = new char[6][6];
        char[][] tableroJugador2 = new char[6][6];

        // Inicializar tableros
        Tablero.inicializarTablero(tableroJugador1);
        Tablero.inicializarTablero(tableroJugador2);

        // Agregar Enemigos
        Tablero.agregarEnemigos(tableroJugador1, 8);
        Tablero.agregarEnemigos(tableroJugador2, 8);

        // Mostrar Usuario
        Tablero.agregarUsuario(tableroJugador1, 'A');
        Tablero.agregarUsuario(tableroJugador2, 'B');

        // Mostrar tableros al inicio
        System.out.println("Tablero del Jugador 1:");
        Tablero.mostrarTablero(tableroJugador1, false);

        System.out.println("\nTablero del Jugador 2:");
        Tablero.mostrarTablero(tableroJugador2, false);

        // Comienza el juego por turnos
        Scanner scanner = new Scanner(System.in);
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
        System.out.println("Ingresa tu movimiento (número de casillas dirección):");
        int cantidadCasillas = scanner.nextInt();
        char direccion = scanner.next().charAt(0);

        int nuevaFila = filaActual;
        int nuevaColumna = columnaActual;

        // Movimiento según la dirección especificada
        switch (direccion) {
            case 'D':
                nuevaColumna = (columnaActual + cantidadCasillas) % tablero[0].length;
                break;
            case 'A':
                nuevaColumna = (columnaActual - cantidadCasillas + tablero[0].length) % tablero[0].length;
                break;
            case 'W':
                nuevaFila = (filaActual - cantidadCasillas + tablero.length) % tablero.length;
                break;
            case 'S':
                nuevaFila = (filaActual + cantidadCasillas) % tablero.length;
                break;
            default:
                System.out.println("Dirección no válida");
                return false;
        }

        if (tablero[nuevaFila][nuevaColumna] == 'E') {
            if (jugador == 'A') {
                vidasJugador1--;
                System.out.println("¡Jugador A ha encontrado un enemigo y ha perdido una vida! Vidas restantes: " + vidasJugador1);
            } else {
                vidasJugador2--;
                System.out.println("¡Jugador B ha encontrado un enemigo y ha perdido una vida! Vidas restantes: " + vidasJugador2);
            }
            tablero[nuevaFila][nuevaColumna] = jugador;
        } else {
            tablero[filaActual][columnaActual] = 'L';
            tablero[nuevaFila][nuevaColumna] = jugador;
        }

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
