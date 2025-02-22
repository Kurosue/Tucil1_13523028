import java.util.HashMap;
import java.util.Map;

public class Board {
    public String[][] board;
    private static final String RESET = "\033[0m";
    private static final String[] COLORS = {
        "\033[31m", // Red
        "\033[32m", // Green
        "\033[33m", // Yellow
        "\033[34m", // Blue
        "\033[35m", // Magenta
        "\033[36m", // Cyan
        "\033[37m", // White
        "\033[91m", // Bright Red
        "\033[92m", // Bright Green
        "\033[93m", // Bright Yellow
        "\033[94m", // Bright Blue
        "\033[95m", // Bright Magenta
        "\033[96m", // Bright Cyan
        "\033[97m"  // Bright White
    };
    private Map<String, String> colorMap;

    public Board(int N, int M, String mode, String[] maplist) {
        board = new String[N][M];
        // Jika mode custom
        if(mode.equals("CUSTOM"))
        {  
            for (int i = 0; i < N; i++)
            {
                for (int j = 0; j < M; j++)
                {
                    // Jika character X(sesuai spek) maka sel bisa diisi sehingga diganti dengan karakter .
                    if(maplist[i].charAt(j) == 'X')
                    {
                        board[i][j] = ".";
                    }
                    else
                    {
                        board[i][j] = "*";
                    }
                }
            }
        }
        else
        {
            // Jika default
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    board[i][j] = ".";
                }
            }
        }
        colorMap = new HashMap<>();
    }

    public boolean checkValidBlock(int x, int y, String[][] bl) {
        // Cek apakak blok valid (dapat diletakkan) pada x dan y dalam board
        for (int i = 0; i < bl.length; i++) {
            for (int j = 0; j < bl[0].length; j++) {
                if (!bl[i][j].equals(".")) {
                    if (x + i >= board.length || y + j >= board[0].length) {
                        return false;
                    }
                    if (!board[x + i][y + j].equals(".")) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean checkFull() {
        // Cek kondisi menang atau jika board telah full (tidak ada character .)
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j].equals(".")) {
                    return false;
                }
            }
        }
        return true;
    }

    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                String cell = board[i][j];
                if (cell.equals(".")) {
                    System.out.print(cell + " ");
                } else {
                    // Mapping karakter dengan warena sesuai map diatas jika tidak ada assssign dengan warna putih kemabli
                    String color = colorMap.computeIfAbsent(cell, k -> COLORS[colorMap.size() % COLORS.length]);
                    System.out.print(color + cell + RESET + " ");
                }
            }
            System.out.println();
        }
    }

    public void placeBlocks(int i, int j, String[][] bl) {
        for (int x = 0; x < bl.length; x++) {
            for (int y = 0; y < bl[0].length; y++) {
                if (!bl[x][y].equals(".")) {
                    board[i + x][j + y] = bl[x][y];
                }
            }
        }
    }

    public void removeBlocks(int i, int j, String[][] bl) {
        for (int x = 0; x < bl.length; x++) {
            for (int y = 0; y < bl[0].length; y++) {
                if (!bl[x][y].equals(".")) {
                    board[i + x][j + y] = ".";
                }
            }
        }
    }
}