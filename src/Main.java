import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ThreadExample {

    private static Lock lock = new ReentrantLock();
    private static int[] sum = new int[5];
    private static void increment(ThreadExample threadExample,int a) {

        lock.lock();


        try {

            int[][] matrix = {
                    {1, 2, 3, 4, 5},
                    {6, 7, 8, 9, 10},
                    {11, 12, 13, 14, 15},
                    {16, 17, 18, 19, 20},
                    {21, 22, 23, 24, 25}
            };

            int i = a;
                for (int j = 0; j < 5; j++)
                   sum[i] += matrix[i][j];
            System.out.println(Thread.currentThread().getName() + " " + sum[i]);

        } finally {
            lock.unlock();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        ThreadExample threadExample = new ThreadExample();
        Thread thread1 = new Thread(() -> {
            increment(threadExample,0);
        });

        Thread thread2 = new Thread(() -> {
            increment(threadExample,1);
        });

        Thread thread3 = new Thread(() -> {
            increment(threadExample,2);
        });

        Thread thread4 = new Thread(() -> {
            increment(threadExample,3);
        });

        Thread thread5 = new Thread(() -> {
            increment(threadExample,4);
        });

        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
        thread3.start();
        thread3.join();
        thread4.start();
        thread4.join();
        thread5.start();
        thread5.join();

        int total = 0;
        for(int i = 0; i<5; i++){
            total += sum[i];
        }
        System.out.println("Total Sum: " + total);
    }
}
