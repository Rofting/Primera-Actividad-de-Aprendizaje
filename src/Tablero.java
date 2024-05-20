import java.util.Random;

public class Tablero {
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

    public static void agregarEnemigos(char[][] tablero, int cantidad) {
        Random rand = new Random();
        int enemigosAgregados = 0;

        while (enemigosAgregados < cantidad) {
            int fila = rand.nextInt(tablero.length);
            int columna = rand.nextInt(tablero[0].length);

            if (tablero[fila][columna] == 'L') {
                tablero[fila][columna] = 'E';
                enemigosAgregados++;
            }
        }
    }

    public static void agregarUsuario(char[][] tablero, char usuario) {
        Random rand = new Random();
        boolean colocado = false;

        while (!colocado) {
            int fila = rand.nextInt(tablero.length);
            int columna = rand.nextInt(tablero[0].length);

            if (tablero[fila][columna] == 'L') {
                tablero[fila][columna] = usuario;
                colocado = true;
            }
        }
    }

    public static int[] encontrarJugador(char[][] tablero, char jugador) {
        int[] posicion = new int[2];

        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] == jugador) {
                    posicion[0] = i; // fila
                    posicion[1] = j; // columna
                    return posicion;
                }
            }
        }
        posicion[0] = -1;
        posicion[1] = -1;
        return posicion;
    }
}

