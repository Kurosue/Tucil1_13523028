public class Board {
    public char[][] board;

    public Board(int N, int M)
    {
        board = new char[N][M];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                board[i][j] = '.';
            }
        }
    }

    public void printBoard()
    {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void placeBlocks(int i, int j, Block bl)
    {
        for (int k = 0; k < bl.block.length; k++) {
            for (int l = 0; l < bl.block[0].length; l++) {
                if (bl.block[k][l] != '.') {
                    board[i + k][j + l] = bl.block[k][l];
                }
            }
        }
    }

}