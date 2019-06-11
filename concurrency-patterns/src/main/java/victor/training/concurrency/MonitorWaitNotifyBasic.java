package victor.training.concurrency;

import static victor.training.concurrency.ConcurrencyUtil.log;
import static victor.training.concurrency.ConcurrencyUtil.sleep2;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Holder;

public class MonitorWaitNotifyBasic {
	static Holder<String> masaTaskuri = new Holder<>();

	public static class Sef {
		public void announceComision(String results) {
				masaTaskuri.value = results;
			
				// TODO masaTaskuri.notifyAll();
			log("Announced task");
		}
	}
	
	public static class AngajatModel extends Thread {
		public void waitForTask() {
			log("Waiting for task..");
			
			 sleep2(1); // Force thread shift  
			// TODO masaTaskuri.wait();
			
			log("Got task to do: " + masaTaskuri.value);
		}
		
		public void run() {
			waitForTask();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		long t0 = System.currentTimeMillis();
		
		List<AngajatModel> angajati = new ArrayList<>();
		for (int i = 0 ; i< 100; i++) {
			AngajatModel s = new AngajatModel();
			s.start();
			angajati.add(s);
		}
		
		Sef sef = new Sef();
		sef.announceComision("Cafea");
		sef.announceComision("Tzigari");
		sef.announceComision("Palinca");
		
		for (AngajatModel s : angajati) {
			s.join();
		}
		
		
		// TODO measure difference for 10000 tasks after applying double checked locking pattern
		log("Execution took: " +(System.currentTimeMillis() - t0));
	}
}
