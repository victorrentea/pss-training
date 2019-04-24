package training.spring.web;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AsyncPlaygroundController {
	private final static Logger log = LoggerFactory.getLogger(AsyncPlaygroundController.class);
	
	@Autowired
	private Worker worker;
	
	@GetMapping("parallel")
	public void fa2chestiiOdata() throws InterruptedException, ExecutionException {
		log.info("Incep cu " + worker.getClass());
		Future<Void> viitorNimic = worker.task1();
		Future<Integer> viitorNumar = worker.task2();
		
		viitorNimic.get();
		log.info("Numarul norocos este : " +viitorNumar.get());
		log.info("Termin");
	}
	@Component
	public static class Worker {
	
		@Async
		public Future<Void> task1() {
			log.info("start1");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
	//		if (true) throw new IllegalArgumentException(); // pune-o la loc ca sa vezi de ce intorc Void !
			log.info("end1");
			return CompletableFuture.completedFuture(null);
		}
		@Async
		public Future<Integer> task2() {
			log.info("start2");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			log.info("end2");
	
			return CompletableFuture.completedFuture(6);
		}
	}
}
