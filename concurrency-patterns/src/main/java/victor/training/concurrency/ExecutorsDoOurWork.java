package victor.training.concurrency;

import java.util.concurrent.*;

import static victor.training.concurrency.ConcurrencyUtil.log;
import static victor.training.concurrency.ConcurrencyUtil.sleep2;

public class ExecutorsDoOurWork {
    static volatile int i= 1;
    public static void main(String[] args) throws InterruptedException {

//        TransferQueue

//        ExecutorService pool = Executors.newCachedThreadPool(); // unbounded threads
//        ExecutorService pool = Executors.newFixedThreadPool(2); // always 2 threads
        BlockingQueue<Runnable> q = new ArrayBlockingQueue<>(10);
        RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
        ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 2, 10,
                TimeUnit.SECONDS, q, handler);
        for (int i = 0; i < 10 /*10_000_000 - try this at home*/; i++) {
            pool.execute(ExecutorsDoOurWork::task);
//            sleep2(1);
        }
//        pool.submit(() -> System.exit(1)); // "Poison running through my veins"
        log("Submitted all tasks");

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
    }

    private static void task() {
        log("Before");
        sleep2(100);
        log("After "+ (i++));
        Thread.currentThread().interrupt();
    }
}
