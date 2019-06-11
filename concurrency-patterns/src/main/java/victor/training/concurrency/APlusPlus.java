package victor.training.concurrency;

public class APlusPlus {
	private static int populatie;
	private static Object monitor = new Object(); 

	public static class ThreadA extends Thread {
		@Override
		public void run() {
			for (int i=0;i<10000;i++) {
					// critical code
					populatie ++;
			}
		}
	}
	
	public static class ThreadB extends Thread {
		@Override
		public void run() {
			for (int i=0;i<10000;i++) {
					// critical code
					populatie ++;
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		ThreadA threadA = new ThreadA();
		ThreadB threadB = new ThreadB();
		threadA.start();
		threadB.start();
		
		threadA.join();
		threadB.join();
		System.out.println(populatie);
	}
}
