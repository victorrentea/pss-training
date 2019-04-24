package training.spring.web;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncPlaygroundController {
	private final static Logger log = LoggerFactory.getLogger(AsyncPlaygroundController.class);
	
	public static final ExecutorService putz = Executors.newFixedThreadPool(2);
	
	@GetMapping("parallel")
	public void fa2chestiiOdata() throws InterruptedException, ExecutionException {
		log.info("Incep");
		Future<Void> viitorNimic = putz.submit(() -> task1());
		Future<Integer> viitorNumar = putz.submit(() -> task2());
		
		viitorNimic.get();
		log.info("Numarul norocos este : " +viitorNumar.get());
		log.info("Termin");
	}
	
	public Void task1() {
		log.info("start1");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
//		if (true) throw new IllegalArgumentException(); // pune-o la loc ca sa vezi de ce intorc Void !
		log.info("end1");
		return null;
	}
	public int task2() {
		log.info("start2");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		log.info("end2");

		return 6;
	}
}
