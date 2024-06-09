import java.util.Random;

public class Tablero {
    private static Colors colors = new Colors();

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
                char elemento = tablero[i][j];
                String elementoColorizado = "";

                switch (elemento) {
                    case 'E':
                        if (mostrarEnemigos) {
                            elementoColorizado = colors.colored_text("E", 1);
                        } else {
                            elementoColorizado = colors.colored_text("L", 0);
                        }
                        break;
                    case 'V':
                        elementoColorizado = colors.colored_text("V", 2);
                        break;
                    case 'A':
                    case 'B':
                        elementoColorizado = colors.colored_text(Character.toString(elemento), 1);
                        break;
                    case 'S':
                        elementoColorizado = colors.colored_text("S", 4);
                        break;
                    default:
                        elementoColorizado = Character.toString(elemento);
                        break;
                }

                System.out.print(elementoColorizado + " ");
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

    public static void agregarVidasExtra(char[][] tablero, int cantidad) {
        Random rand = new Random();
        int vidasAgregadas = 0;

        while (vidasAgregadas < cantidad) {
            int fila = rand.nextInt(tablero.length);
            int columna = rand.nextInt(tablero[0].length);

            if (tablero[fila][columna] == 'L') {
                tablero[fila][columna] = 'V';
                vidasAgregadas++;
            }
        }
    }
}
