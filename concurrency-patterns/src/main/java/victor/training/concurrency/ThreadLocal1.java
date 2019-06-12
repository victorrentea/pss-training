package victor.training.concurrency;

import static victor.training.concurrency.ConcurrencyUtil.log;

public class ThreadLocal1 {
	// TODO declare a static thread local variable
	static ThreadLocal<String> currentUserName = new ThreadLocal<>();
	
	static class MyThread extends Thread {
		static void m1() {
			// no knowledge about the hidden thread local
			m2();
		}
		
		static void m2() {
			// hocus-pocus, here is the data

			 String threadLocalData = currentUserName.get(); // TODO get from thread local
			log("Get thread local: " + threadLocalData);
		}
		public void run() {
			String dataToSet = "Data of " + getName();
			currentUserName.set("johndoe" + Thread.currentThread().getName());
			// TODO set in thread local 
			log("Set thread local: " + dataToSet);
			ConcurrencyUtil.sleep2(300);
			m1();
		}
	}
	
	public static void main(String[] args) {
		new MyThread().start();
		new MyThread().start();
		new MyThread().start();
	}
}
