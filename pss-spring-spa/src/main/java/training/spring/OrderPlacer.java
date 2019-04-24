package training.spring;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class OrderPlacer {

	public int logica() {
		//chestii complicate
		return new Random().nextInt();
	}
}
