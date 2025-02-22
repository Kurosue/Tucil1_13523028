import java.util.Arrays;

public class Block {
    public String[][] block;
    public String[][][] calculatedPossBlocks;
    
    public Block(String[] lines) {
        int rows = lines.length;
        int cols = Arrays.stream(lines).mapToInt(String::length).max().orElse(0);

        block = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(j < lines[i].length())
                {
                    if(lines[i].charAt(j) != ' ')
                    {
                        block[i][j] = String.valueOf(lines[i].charAt(j));
                    }
                    else
                    {
                        block[i][j] = ".";
                    }
                }
                else
                {
                    // Kalau ada Null(jika panjang suatu baris lebih pendek daripada baris terpanjang) dalam string maka diganti menjadi .
                    block[i][j] = ".";
                }
            }
        }
        generatePossibleBlocks();
    }
    
    // Buat semua kemungkinan rotasi dalam 2D
    private void generatePossibleBlocks() {
        calculatedPossBlocks = new String[16][3][3]; // satu axis kemungkinan 8 sehingga maka total kemungkinan adalah 16
        
        String[][] tempBlok;
        if(block.length != block[0].length)
        {
            tempBlok = centerBlock(block);
        }
        else // Jika blok sudah simetris atau nxn maka tidak perlu centering
        {
            tempBlok = block;
        }

        for (int i = 0; i < 8; i++) {
            calculatedPossBlocks[i] = tempBlok;
            tempBlok = rotate45(tempBlok);
        }
        
        for (int i = 0; i < 8; i++) {
            calculatedPossBlocks[i + 8] = flipBlock(calculatedPossBlocks[i]);
        }
    }
    
    // Centering blok ke matriks nxn dengan n adalah max(col, row)
    private String[][] centerBlock(String[][] blk) 
    {
        int rows = blk.length;
        int cols = blk[0].length;
        int maxDim = Math.max(rows, cols);
        String[][] centered = new String[maxDim][maxDim];

        for (int i = 0; i < maxDim; i++) {
            for (int j = 0; j < maxDim; j++) {
                centered[i][j] = ".";
            }
        }

        int rowOffset = (maxDim - rows) / 2;
        int colOffset = (maxDim - cols) / 2;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                centered[i + rowOffset][j + colOffset] = blk[i][j];
            }
        }

        return centered;
    }
    
    // Rotasi blok 45 derajat menggunakan teorema pada aljabar linear
    private String[][] rotate45(String[][] blk) {
        // blk sudah dipastikan berukuran nxn karena centering atau sudah nxn dari awal
        int n = blk.length;
        String[][] rotated = new String[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotated[i][j] = ".";
            }
        }

        // Perhitungan titik tengah dan besar rotasi dari titik tengah
        double centerX = (n - 1) / 2.0;
        double centerY = (n - 1) / 2.0;
        double angle = Math.PI / 4; // 45 derajat
        double cosA = Math.cos(angle);
        double sinA = Math.sin(angle);

        // Traverse untuk setiap elemen
        if (n == 2) {
            // Khusus metriks 2x2
            rotated[0][0] = blk[1][0];
            rotated[0][1] = blk[0][0];
            rotated[1][0] = blk[1][1];
            rotated[1][1] = blk[0][1];
        }
        else
        {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    // Translasi lalu rotasi blok
                    double x = i - centerX;
                    double y = j - centerY;
                    double newX = cosA * x - sinA * y;
                    double newY = sinA * x + cosA * y;
                    int resX = (int) Math.round(newX + centerX);
                    int resY = (int) Math.round(newY + centerY);

                    // Ensure the resX and resY are within bounds
                    if (resX >= 0 && resX < n && resY >= 0 && resY < n) {
                        rotated[resX][resY] = blk[i][j];
                    }
                }
            }
        }

        return rotated;
    }

    
    // Mirroring secara harizontal
    private String[][] flipBlock(String[][] blk) {
        String[][] flipped = new String[blk.length][blk[0].length];
        for (int i = 0; i < blk.length; i++) {
            for (int j = 0; j < blk[0].length; j++) {
                flipped[i][j] = blk[i][blk[0].length - j - 1];
            }
        }

        return flipped;
    }
    
    // Print block (debugging only)
    public void printBlock(String[][] blk) {
        for (String[] row : blk) {
            for (String cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    
    // Print semua kemungkinan blok (debugging only)
    public void printAllBlocks() {
        for (int i = 0; i < calculatedPossBlocks.length; i++) {
            System.out.println("Transformation " + (i + 1) + ":");
            printBlock(calculatedPossBlocks[i]);
        }
    }
}
