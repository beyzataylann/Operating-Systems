import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class filereader {
    public static void main(String[] args) {
        try (BufferedReader reader1 = new BufferedReader(new FileReader("C:/github/Operating-Systems/project1/matrix1.txt"));
             BufferedReader reader2 = new BufferedReader(new FileReader("C:/github/Operating-Systems/project1/matrix2.txt"))) {

            int[][] matrix1 = readMatrix(reader1);
            int[][] matrix2 = readMatrix(reader2);


            if (matrix1.length != matrix2[0].length) {
                System.out.println("1. matrisin sutun sayısı ile 2.matrisin satır sayısı eşit olmalı.");
                return;
            }


            int satirsayisi = matrix1.length;
            int sutunsayisi = matrix2[0].length;
            int[][] sonuc = new int[satirsayisi][sutunsayisi];


            Thread[][] threads = new Thread[satirsayisi][sutunsayisi];
            for (int i = 0; i < satirsayisi; i++) {
                for (int j = 0; j < sutunsayisi; j++) {
                    threads[i][j] = new Thread(new MultiMatrix(matrix1, matrix2, sonuc, i, j));
                    threads[i][j].start();
                }
            }


            for (int i = 0; i < satirsayisi; i++) {
                for (int j = 0; j < sutunsayisi; j++) {
                    try {
                        threads[i][j].join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }


            System.out.println("Matris çarpımı sonucu:");
            Matrixprinter(sonuc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[][] readMatrix(BufferedReader reader) throws IOException {
        String line;
        int satir = 0;
        int sutun = 0;


        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            satir++;
            String[] elements = line.split(" ");
            sutun = elements.length;
        }


        reader.close();
        reader = new BufferedReader(new FileReader("C:/github/Operating-Systems/project1/matrix1.txt"));

        int[][] matrix3 = new int[satir][sutun];


        for (int i = 0; i < satir; i++) {
            line = reader.readLine();
            if (line == null) {
                break;
            }
            String[] elements = line.split(" ");
            for (int j = 0; j < sutun; j++) {
                matrix3[i][j] = Integer.parseInt(elements[j]);
            }
        }

        return matrix3;
    }


    private static class MultiMatrix implements Runnable {
        private final int[][] A;
        private final int[][] B;
        private final int[][] C;
        private final int row;
        private final int col;

        public MultiMatrix(int[][] A, int[][] B, int[][] C, int row, int col) {
            this.A = A;
            this.B = B;
            this.C = C;
            this.row = row;
            this.col = col;
        }

        @Override
        public void run() {
            long startTime = System.nanoTime();
            C[row][col] = 0;
            for (int k = 0; k < A[0].length; k++) {
                C[row][col] += A[row][k] * B[k][col];
            }
            long endTime = System.nanoTime();
            long duration  = endTime - startTime;
            System.out.println("Thread " + Thread.currentThread().getId()  +" Çalışma süresi: " + duration + " nanosaniye");
        }
    }

    private static void Matrixprinter(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}
