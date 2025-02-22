public class Solver {
    // Bruteforcing menggunakan rekursi untuk setiap blok pada array dan setiap titik pada board
    public static boolean solveBruteForce(Block[] bl, Board br, int blockIndex, int counter){
        // If all blocks have been placed and the board is full
        if (blockIndex == bl.length) {
            if (br.checkFull()) {
                System.out.println();
                br.printBoard();
                System.out.println("\nBanyak kasus yang ditelusuri: " + counter);
                return true;
            }
            return false;
        }

        // Iterasi untuk setiap kolom, baris, dan block. lalu untuk memasang setiap kemungkinan
        for (int i = 0; i < br.board.length; i++) {
            for (int j = 0; j < br.board[0].length; j++) {
                for (int L = 0; L < bl[blockIndex].calculatedPossBlocks.length; L++) {
                    counter++;
                    if (br.checkValidBlock(i, j, bl[blockIndex].calculatedPossBlocks[L])) {
                        br.placeBlocks(i, j, bl[blockIndex].calculatedPossBlocks[L]);

                        // Recursively try to place the next block
                        if (solveBruteForce(bl, br, blockIndex + 1, counter)) {
                            return true;
                        }

                        // Backtrack by removing the block
                        br.removeBlocks(i, j, bl[blockIndex].calculatedPossBlocks[L]);
                    }
                }
            }
        }
        return false;
    }
}