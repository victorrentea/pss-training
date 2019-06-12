package victor.training.concurrency;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.*;

import static victor.training.concurrency.ConcurrencyUtil.log;
import static victor.training.concurrency.ConcurrencyUtil.sleep2;

public class ARequirement {
    static volatile int i= 1;
    public static void main(String[] args) throws InterruptedException {
        Runnable task = ARequirement::stuff;

//        TransferQueue

//        ExecutorService pool = Executors.newCachedThreadPool(); // unbounded threads
        ExecutorService pool = Executors.newFixedThreadPool(2); // always 2 threads
        Future<String> future = pool.submit(ARequirement::call);

        sleep2(1);
        future.cancel(true);

//        pool.submit(() -> System.exit(1)); // "Poison running through my veins"
        log("Submitted all tasks");

        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);
    }

    private static void stuff() {
        log("Before");
        sleep2(100);
        log("After "+ (i++));
        Thread.currentThread().interrupt();
    }

    static String call() {
        log("Before");
        try (FileInputStream fis = new FileInputStream(new File("C:\\Users\\VictorRentea\\Videos\\CleanCoders videos\\CleanCode MP3s.zip"))) {
            IOUtils.toByteArray(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            throw new RuntimeException();
//        }

        log("After");
        return "a";
    }
}
