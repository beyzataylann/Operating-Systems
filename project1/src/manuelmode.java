import java.util.Scanner;

class manuelmode {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("1. matrisin satır sayısını giriniz: ");
        int satirA = scanner.nextInt();
        System.out.print("1. matrisin sütun sayısını giriniz: ");
        int sutunA = scanner.nextInt();
        System.out.print("2. matrisin satır sayısını giriniz: ");
        int satirB = scanner.nextInt();
        System.out.print("2. matrisin sütun sayısını giriniz: ");
        int sutunB = scanner.nextInt();

        if (sutunA != satirB) {
            System.out.println("1. matrisin sutun sayısı ile 2.matrisin satır sayısı eşit olmalı.");
            return;
        }

        int[][] A = new int[satirA][sutunA];
        int[][] B = new int[satirB][sutunB];

        System.out.println("Lütfen 1.matrisi girin:");
        for (int i = 0; i < satirA; i++) {
            for (int j = 0; j < sutunA; j++) {
                A[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Lütfen 2.matrisi girin:");
        for (int i = 0; i < satirB; i++) {
            for (int j = 0; j < sutunB; j++) {
                B[i][j] = scanner.nextInt();
            }
        }

        int[][] C = new int[A.length][B[0].length];

        Thread[][] threads = new Thread[A.length][B[0].length];
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B[0].length; j++) {
                threads[i][j] = new Thread(new MultiMatrix(A, B, C, i, j));
                threads[i][j].start();
            }
        }

        try {
            for (int i = 0; i < A.length; i++) {
                for (int j = 0; j < B[0].length; j++) {
                    threads[i][j].join();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Matris çarpımı sonucu:");
        Matrixpr(C);
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
            long duration = endTime - startTime;

            System.out.println("Thread " + Thread.currentThread().getId() + " hesapladı: " + duration + " nanosaniye");
        }
    }

    private static void Matrixpr(int[][] matrix) {
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }
}
