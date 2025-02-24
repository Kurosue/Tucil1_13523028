import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Main {
    private static int N, M, P;
    private static String mode;
    private static Block[] blocks;
    private static char[] usedBlock;
    private static Board board;

    public static void main(String[] args) {
        readInput();


        Solver solver = new Solver();

        long startTime = System.nanoTime();
        boolean solved = Solver.solveBruteForce(blocks, board,0, 0);
        long endTime = System.nanoTime();

        if(!solved)
        {
            System.out.println("\nTidak Ada solusi yang ditemukan");
        }

        double duration = (endTime - startTime) / 1_000_000_000.0; // Ubah ke detik
        System.out.println(String.format("\nWaktu Eksekusi = %.5f s\n", duration));

        // Pilihan untuk menyimpan hasil atau tidak
        if(solved)
        {
            System.out.print("Apakah anda ingin menyimpan hasil ? (Y/N) : ");
            Scanner input = new Scanner(System.in);
            String save = input.next();
            if(save.equals("Y") || save.equals("y"))
            {
                System.out.print("\nApakah hasil ingin disimpan dalam bentku gambar ? (Y/N) : ");
                String saveMode = input.next();
                if(saveMode.equals("Y") || saveMode.equals("y"))
                {
                    System.out.print("Masukkan nama file : ");
                    String fileName = "test/" + input.next();
                    board.saveBoardInImage(fileName);
                }
                else
                {
                    System.out.print("Masukkan nama file : ");
                    String fileName = "test/" + input.next();
                    board.saveBoard(fileName);
                }
            }
        }
    }

    private static void readInput() {
        try {
            // Mengambil input dari cli langsung dari user
            System.out.print("Insert File Name( Make sure its located in test directory) : ");
            Scanner inputNameFile = new Scanner(System.in);
            Scanner fileparser = new Scanner(new File("./test/" + inputNameFile.next()));

            // Mengambil nilai N, M, P dari file dengan split menjadi list
            String[] tokens = fileparser.nextLine().split(" ");
            if(tokens.length != 3)
            {
                System.out.println("Input Tidak Valid");
                System.exit(1);
            }
            N = Integer.parseInt(tokens[0]);
            M = Integer.parseInt(tokens[1]);
            P = Integer.parseInt(tokens[2]);

            // Mengambil "mode" pada line selanjutnya
            mode = fileparser.nextLine().trim();
            String[] mapArray = null;
            if (mode.equals("CUSTOM")) {
                ArrayList<String> mapRow = new ArrayList<>();
                for (int i = 0; i < N; i++) {
                    mapRow.add(fileparser.nextLine().trim());
                }
                mapArray = mapRow.toArray(new String[0]);
            }

            board = new Board(N, M, mode, mapArray);
            System.out.println("[+] Inisialisasi Papan berhasil !");
            
            // Mengambil blok sebanyak P
            blocks = new Block[P];
            usedBlock = new char[P];
            int i = 0;
            String firstLine = fileparser.nextLine();

            while (i < P) {
                ArrayList<String> blockLines = new ArrayList<>();

                blockLines.add(firstLine);
                char charBlok = firstLine.trim().charAt(0);
                // mencegah adanya 2 blok dengan karakter yang sama, jika ada yang sama, program langsung keluar
                if(checkUsedBlock(charBlok))
                {
                    System.out.println("Blok " + charBlok + " sudah digunakan");
                    System.exit(1);
                }
                usedBlock[i] = charBlok;

                while (fileparser.hasNextLine()) {
                    String nextLine = fileparser.nextLine();
                    if (nextLine.isEmpty() || nextLine.trim().charAt(0) != charBlok) {
                        if(nextLine.trim().charAt(0) != charBlok) {
                            // Jika huruf berubah == blok baru maka line perlu disimpan untuk iterasi berikutnya
                            firstLine = nextLine;
                        }
                        break;
                    }
                    blockLines.add(nextLine);
                }
                
                // Memasukkan array of string menjadi blok lalu memasukkan blok kedalam array of block
                String[] blockArray = blockLines.toArray(new String[0]);
                blocks[i] = new Block(blockArray);
                i++;
            }
            System.out.println("[+] Membuat semua kemungkinan blok berhasil !");

            fileparser.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error : " + e.getMessage());
            System.exit(0);
        }
    }

    private static boolean checkUsedBlock(char c)
    {
        for (int i = 0; i < usedBlock.length; i++) {
            if(usedBlock[i] == c)
            {
                return true;
            }
        }
        return false;
    }
}
