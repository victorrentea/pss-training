package victor.training.concurrency;

import static victor.training.concurrency.ConcurrencyUtil.log;

import java.io.FileReader;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadLocalDeLaZero {

	public static void main(String[] args) throws InterruptedException {
//		Thread thread = new Thread(ThreadLocalDeLaZero::altaMetoda);
//		thread.start();
		
//		new ThreadPoolExecutor()
		ExecutorService pool = Executors.newFixedThreadPool(3);
		
		pool.submit(ThreadLocalDeLaZero::altaMetoda);
		pool.submit(ThreadLocalDeLaZero::altaMetoda);
		pool.submit(ThreadLocalDeLaZero::altaMetoda);
//		pool.submit(ThreadLocalDeLaZero::altaMetoda);
		
		pool.shutdown(); // inchide usa in spatele clientilor : nu mai acceptam noi taskur
		pool.awaitTermination(1, TimeUnit.SECONDS);
		pool.shutdownNow(); // inchide usa in nasul clientilor.
		log("gata, plec acasa");
	}
	
	static ThreadLocal<String> nume = new ThreadLocal<String>();
	
	public static void altaMetoda() {
		//cod
		log("Uaaa!!!");
		nume.set(UUID.randomUUID().toString());
		log("Te botez " + nume.get());
		log("Traiesc un pic...");
		ConcurrencyUtil.sleepSomeTime();
		log("Oare cum ma chema?" + nume.get());
		
		
//		String file  = "a.dt";
//		FileReader reader = new FileReader(file);
//		
//		reader.read();
//		Thread.currentThread().isInterrupted()
	}
}
