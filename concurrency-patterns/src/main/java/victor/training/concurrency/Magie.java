package victor.training.concurrency;

import static victor.training.concurrency.ConcurrencyUtil.log;

public class Magie {
	private static ThreadLocal<String> userulDePeThread = new ThreadLocal<>();
	public static void main(String[] args) {
		new Thread(new Runnable() {
			public void run() {
				String username = "joe";
				userulDePeThread.set(username);
				try {
					metodaMaiInalta();
				} finally {
					// dezleaga ghiuleaua
					userulDePeThread.remove();
				}
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				String username = "jane";
				userulDePeThread.set(username);
				try {
					metodaMaiInalta();
				} finally {
					// dezleaga ghiuleaua
					userulDePeThread.remove();
				}
			}
		}).start();
	}
	private static void metodaMaiInalta() {
		altaMetoda();
	}
	private static void altaMetoda() {
		//beci aka Repository
		log("User="+userulDePeThread.get());
		//completam coloana LAST_MODIFIED_BY
	}
}
