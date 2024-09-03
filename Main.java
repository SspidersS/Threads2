import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static final int N = 15;
    private static final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private static final AtomicInteger currentNumber = new AtomicInteger(1);

    public static void main(String[] args) {
        Thread worker = new Thread(() -> {
            for (int i = 1; i <= N; i++) {
                String result = "";
                if (i % 15 == 0) {
                    result = "fizzbuzz";
                } else if (i % 3 == 0) {
                    result = "fizz";
                } else if (i % 5 == 0) {
                    result = "buzz";
                } else {
                    result = Integer.toString(i);
                }
                try {
                    queue.put(result);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        Thread printer = new Thread(() -> {
            for (int i = 1; i <= N; i++) {
                try {
                    String result = queue.take();
                    System.out.print(result + " ");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        worker.start();
        printer.start();

        try {
            worker.join();
            printer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
