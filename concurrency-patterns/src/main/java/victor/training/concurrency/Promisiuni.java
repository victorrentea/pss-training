package victor.training.concurrency;

import static victor.training.concurrency.ConcurrencyUtil.log;
import static victor.training.concurrency.ConcurrencyUtil.sleep2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Promisiuni {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		// IN sPRING: 	<task:executor id="asyncExecutor" pool-size="1" queue-capacity="100" />

		log("Start!");
		ExecutorService pool = Executors.newFixedThreadPool(4);
//		BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(6);
//		ThreadPoolExecutor pool = new ThreadPoolExecutor(2, 4, 1, TimeUnit.SECONDS, workQueue, 
//				new ThreadPoolExecutor.DiscardOldestPolicy());
		
		List<Future<String>> promisiuni = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			Future<String> viitorRezultat = pool.submit(Promisiuni::treabaRodnica);
			promisiuni.add(viitorRezultat);
		}
		log("Am submis toata treaba");
		
		List<String> rezultate = new ArrayList<>();
		log("Astept rezultatele");
		for (Future<String> promisiune : promisiuni) {
			String rezultat = promisiune.get();
			rezultate.add(rezultat);
		}
		log("Au venit rezultatele");
		log("Gata!: " + rezultate);
		
		Future<?> email1 = pool.submit(Promisiuni::fireAndForget);
		Future<?> email2 = pool.submit(Promisiuni::fireAndForget);
		
		email1.get();
		email2.get();
		pool.shutdown();
	}
	
	public static String treabaRodnica() {
		log("Start");
		sleep2(1000);
		log("End");
		return "Rez" + new Random().nextInt(100);
	}
	
	public static void fireAndForget() {
		log("Sending email");
		//sending
		if (true) throw new IllegalArgumentException("La asta chiar nu ma asteptam din partea ta");
		log("sent");
	}
	
}
