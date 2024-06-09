import java.util.Scanner;
import static com.diogonunes.jcolor.Ansi.colorize;
import static com.diogonunes.jcolor.Attribute.*;

public class Juego {
    int vidasJugador1 = 3;
    int vidasJugador2 = 3;
    boolean movimientoCircular;
    Sounds sounds = new Sounds();
    Colors colors = new Colors();

    public static void main(String[] args) {
        Juego juego = new Juego();
        Scanner scanner = new Scanner(System.in);

        int tamanoTablero;

        // Solicitar el tamaño del tablero al inicio
        do {
            System.out.print("Ingresa el tamaño del tablero (por ejemplo, 6 para un tablero 6x6): ");
            tamanoTablero = scanner.nextInt();
            if (tamanoTablero <= 0) {
                System.out.println("Tamaño de tablero no válido. Debe ser un número positivo.");
            }
        } while (tamanoTablero <= 0);

        char[][] tableroJugador1 = new char[tamanoTablero][tamanoTablero];
        char[][] tableroJugador2 = new char[tamanoTablero][tamanoTablero];

        // Configuración inicial de dificultad
        int nivelDificultad = 0;
        while (nivelDificultad < 1 || nivelDificultad > 3) {
            System.out.print("Selecciona la dificultad (1 = Fácil, 2 = Medio, 3 = Difícil): ");
            nivelDificultad = scanner.nextInt();
            if (nivelDificultad < 1 || nivelDificultad > 3) {
                System.out.println("Entrada no válida. Ingresa 1, 2 o 3.");
            }
        }

        // Determinar cantidad de enemigos según la dificultad
        int cantidadEnemigos;
        switch (nivelDificultad) {
            case 1:
                cantidadEnemigos = 5;
                break;
            case 2:
                cantidadEnemigos = 8;
                break;
            case 3:
                cantidadEnemigos = 12;
                break;
            default:
                cantidadEnemigos = 8;
        }

        // Configuración inicial de movimiento circular
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

        // Inicializar tableros
        Tablero.inicializarTablero(tableroJugador1);
        Tablero.inicializarTablero(tableroJugador2);

        // Agregar Enemigos
        Tablero.agregarEnemigos(tableroJugador1, cantidadEnemigos);
        Tablero.agregarEnemigos(tableroJugador2, cantidadEnemigos);

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

        boolean movimientoValido = false;
        int nuevaFila = filaActual;
        int nuevaColumna = columnaActual;

        while (!movimientoValido) {
            // Solicitar movimiento al usuario
            System.out.print("Ingresa tu movimiento (número de casillas dirección, máximo 3 casillas): ");
            int cantidadCasillas = scanner.nextInt();
            String direccionStr = scanner.next().toUpperCase();
            char direccion = direccionStr.charAt(0);

            // Verificar que la cantidad de casillas sea válida
            if (cantidadCasillas < 1 || cantidadCasillas > 3) {
                System.out.println(colors.colored_text("Cantidad de casillas no válida. Ingresa un número entre 1 y 3.", 1));
                continue; // Volver a solicitar el movimiento
            }

            nuevaFila = filaActual;
            nuevaColumna = columnaActual;

            // Movimiento según la dirección especificada
            switch (direccion) {
                case 'D':
                    nuevaColumna = columnaActual + cantidadCasillas;
                    if (movimientoCircular) {
                        nuevaColumna %= tablero[0].length;
                    } else if (nuevaColumna >= tablero[0].length) {
                        nuevaColumna = tablero[0].length - 1;
                    }
                    movimientoValido = true;
                    break;
                case 'A':
                    nuevaColumna = columnaActual - cantidadCasillas;
                    if (movimientoCircular) {
                        nuevaColumna = (nuevaColumna + tablero[0].length) % tablero[0].length;
                    } else if (nuevaColumna < 0) {
                        nuevaColumna = 0;
                    }
                    movimientoValido = true;
                    break;
                case 'W':
                    nuevaFila = filaActual - cantidadCasillas;
                    if (movimientoCircular) {
                        nuevaFila = (nuevaFila + tablero.length) % tablero.length;
                    } else if (nuevaFila < 0) {
                        nuevaFila = 0;
                    }
                    movimientoValido = true;
                    break;
                case 'S':
                    nuevaFila = filaActual + cantidadCasillas;
                    if (movimientoCircular) {
                        nuevaFila %= tablero.length;
                    } else if (nuevaFila >= tablero.length) {
                        nuevaFila = tablero.length - 1;
                    }
                    movimientoValido = true;
                    break;
                default:
                    System.out.println(colors.colored_text("Dirección no válida. Ingresa 'D', 'A', 'W' o 'S'.", 1));
                    break;
            }
        }

        // Reproducir sonido de movimiento
        sounds.play_sound(5); // Assuming index 5 is for movement sound

        // Chequear si hay un enemigo en la nueva posición
        if (tablero[nuevaFila][nuevaColumna] == 'E') {
            if (jugador == 'A') {
                vidasJugador1--;
                System.out.println(colors.colored_text("¡Jugador A ha encontrado un enemigo y ha perdido una vida! Vidas restantes: " + vidasJugador1, 1));
            } else {
                vidasJugador2--;
                System.out.println(colors.colored_text("¡Jugador B ha encontrado un enemigo y ha perdido una vida! Vidas restantes: " + vidasJugador2, 1));
            }
            // Reproducir sonido de pérdida de vida
            sounds.play_sound(6); // Assuming index 6 is for losing a life
        }

        // Chequear si hay una vida extra en la nueva posición
        if (tablero[nuevaFila][nuevaColumna] == 'V') {
            if (jugador == 'A') {
                vidasJugador1++;
                System.out.println(colors.colored_text("¡Jugador A ha encontrado una vida extra! Vidas actuales: " + vidasJugador1, 2));
            } else {
                vidasJugador2++;
                System.out.println(colors.colored_text("¡Jugador B ha encontrado una vida extra! Vidas actuales: " + vidasJugador2, 2));
            }
            // Reproducir sonido de recoger ítem
            sounds.play_sound(4); // Assuming index 4 is for picking up a heart
        }

        // Verificar si el jugador llegó a la salida
        if (tablero[nuevaFila][nuevaColumna] == 'S') {
            if (jugador == 'A') {
                System.out.println(colors.colored_text("¡Jugador A ha llegado a la salida! Fin del juego", 3));
                // Reproducir sonido de fin de partida
                sounds.play_sound(7); // Assuming index 7 is for winning
                return true;
            } else {
                System.out.println(colors.colored_text("¡Jugador B ha llegado a la salida! Fin del juego", 3));
                // Reproducir sonido de fin de partida
                sounds.play_sound(7); // Assuming index 7 is for winning
                return true;
            }
        }

        // Mover jugador y dejar la casilla libre
        tablero[filaActual][columnaActual] = 'L';
        tablero[nuevaFila][nuevaColumna] = jugador;

        if ((jugador == 'A' && vidasJugador1 <= 0) || (jugador == 'B' && vidasJugador2 <= 0)) {
            System.out.println(colors.colored_text("¡El jugador " + jugador + " ha perdido todas sus vidas! Fin del juego.", 1));
            // Reproducir sonido de fin de partida
            sounds.play_sound(1);
            return true;
        }

        // Mostrar tablero tras el movimiento
        System.out.println("Estado de tu tablero tras el movimiento:");
        Tablero.mostrarTablero(tablero, false);

        return false;
    }
}
