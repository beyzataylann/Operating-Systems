import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean x = true;
        while (x) {
            System.out.print("Matrixi dosya yolu ile vermek için 0'a ,klavyeden girmek için 1' e basın:");
            int secim = scanner.nextInt();

            if (secim == 0) {
                x = false;
                filereader.main(args);
            } else if (secim == 1) {
                x = false;
                manuelmode.main(args);
            } else {
                System.out.println("Tekrar Deneyiniz!");
            }
        }
    }
}
