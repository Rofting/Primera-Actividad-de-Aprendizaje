import java.util.Random;
public class Tablero {
    public static void main(String[] args) {
        char[][] tableroJugador1 = new char[6][6];
        char[][] tableroJugador2 = new char[6][6];

        // Inicializar tableros
        inicializarTablero(tableroJugador1);
        inicializarTablero(tableroJugador2);

        // Agregar Enemigos
        agregarEnemigos(tableroJugador1, 8);
        agregarEnemigos(tableroJugador2, 8);

        //Mostrar Usuario
        agregarUsuario(tableroJugador1, 'A');
        agregarUsuario(tableroJugador2, 'B');

        // Mostrar tableros
        System.out.println("Tablero del Jugador 1:");
        mostrarTablero(tableroJugador1, false);

        System.out.println("\nTablero del Jugador 2:");
        mostrarTablero(tableroJugador2, false);
    }

    public static void inicializarTablero(char[][] tablero) {
        Random rand = new Random();
        int filaSalida = rand.nextInt(tablero.length);
        int columnaSalida = rand.nextInt(tablero[0].length);

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                tablero[i][j] = 'L'; // 'L' representa una casilla libre
            }
        }
        tablero[filaSalida][columnaSalida] = 'S';
    }

    public static void mostrarTablero(char[][] tablero, boolean mostrarEnemigos) {
        System.out.println("  1 2 3 4 5 6");
        for (int i = 0; i < tablero.length; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] == 'E' && !mostrarEnemigos) {
                    System.out.print("L ");
                } else {
                    System.out.print(tablero[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public static void agregarEnemigos(char [][] tablero, int cantidad){
        Random rand = new Random();
        int enemigosAgregados = 0;

        while (enemigosAgregados < cantidad){
            int fila = rand.nextInt(tablero.length);
            int columna = rand.nextInt(tablero[0].length);

            if (tablero[fila][columna] == 'L') {
                tablero[fila][columna] = 'E';
                enemigosAgregados++;
            }
        }
    }
    public static void agregarUsuario(char [][] tablero, char usuario){
        Random rand  = new Random();
        boolean colocado = false;

        while (!colocado){
            int fila = rand.nextInt(tablero.length);
            int columna = rand.nextInt(tablero[0].length);

            if (tablero[fila][columna] == 'L'){
                tablero[fila][columna] = usuario;
                colocado = true;
            }
        }
    }
}

