package victor.training.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockingOurselves {
    public static void main(String[] args) {

        A a = new A();
        a.addIfNotPresent("a");

        new Thread(() -> a.addIfNotPresent("b")).start();
        new Thread(() -> a.addIfNotPresent("b")).start();

        ConcurrencyUtil.sleep2(1000);
        System.out.println(a.getList());
    }


}


class A {
    private Lock lock = new ReentrantLock();
    private List<String> list = new ArrayList<>();

    public void addIfNotPresent(String s) {
        lock.lock();
        try {
            if (!list.contains(s)) {
                ConcurrencyUtil.sleep2(100);
                list.add(s);
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean contains(String s) {
        lock.lock();
        try {
            return list.contains(s);
        } finally {
            lock.unlock();
        }
    }

    public List<String> getList() {
        return list;
    }
}