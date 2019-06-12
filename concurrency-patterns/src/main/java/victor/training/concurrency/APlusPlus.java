package victor.training.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

public class APlusPlus {
    public static int populatie = 0;
    //        public static AtomicInteger populatie = new AtomicInteger(0);
    public final static Object monitor = new Object();


    public static void main(String[] args) throws InterruptedException {
        ThreadA threadA = new ThreadA();
        ThreadB threadB = new ThreadB();
        long t0 = System.currentTimeMillis();
        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();
        long t1 = System.currentTimeMillis();
        System.out.println(populatie);
        System.out.println("Took " + (t1 - t0) + " ms");
    }
}


class ThreadA extends Thread {
    @Override
    public void run() {
        int localValue = 0;
        for (int i = 0; i < 10000000; i++) {
            // critical code
            localValue++;
//                APlusPlus.populatie.incrementAndGet();
        }
        synchronized (APlusPlus.monitor) {
            APlusPlus.populatie += localValue;
        }
    }
}

class ThreadB extends Thread {
    @Override
    public void run() {
        int localValue = 0;
        for (int i = 0; i < 10000000; i++) {
            // critical code
            localValue++;
//                APlusPlus.populatie.incrementAndGet();
//            }

        }
        synchronized (APlusPlus.monitor) {
            APlusPlus.populatie += localValue;
        }
    }
}