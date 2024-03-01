public class Tablero {
    public static void main(String[] args) {
        char[][] tableroJugador1 = new char[6][6];
        char[][] tableroJugador2 = new char[6][6];

        // Inicializar tableros
        inicializarTablero(tableroJugador1);
        inicializarTablero(tableroJugador2);

        // Mostrar tableros
        System.out.println("Tablero del Jugador 1:");
        mostrarTablero(tableroJugador1);

        System.out.println("\nTablero del Jugador 2:");
        mostrarTablero(tableroJugador2);
    }

    public static void inicializarTablero(char[][] tablero) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = 'L'; // 'L' representa una casilla libre
            }
        }
    }

    public static void mostrarTablero(char[][] tablero) {
        System.out.println("  1 2 3 4 5 6");
        for (int i = 0; i < tablero.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < tablero[i].length; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }
}
