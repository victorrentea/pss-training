package victor.training.concurrency;

import java.io.Reader;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StoppingThreads {
    static boolean isAlive = true;
    static Lock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {

        Runnable r = () -> {
            while (isAlive) {
                try {
                    ConcurrencyUtil.log("Sleep again");
                    Thread.sleep(10);
                    lock.lock();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                Reader r;
//                r.read();
//                Thread.currentThread().isInterrupted()
            }

        };

        Thread thread = new Thread(r);
        thread.start();
        lock.lock();
        ConcurrencyUtil.sleep2(10);
        ConcurrencyUtil.log("Kill it !");
        isAlive = false;
        thread.interrupt();
        thread.join();
        ConcurrencyUtil.log("Teminator: DONE");

    }


}
