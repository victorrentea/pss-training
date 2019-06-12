package victor.clean.lambdas;

import org.springframework.stereotype.Service;

import java.util.function.BiFunction;

import static org.mockito.Mockito.mock;

class Movie {
	enum Type {
		REGULAR(MoviePriceService::regularPrice),
		NEW_RELEASE(MoviePriceService::newReleasePrice),
		CHILDREN(MoviePriceService::childrensPrice);
		public final BiFunction<MoviePriceService, Integer, Integer> priceAlgorithm;

		Type(BiFunction<MoviePriceService, Integer, Integer> priceAlgorithm) {
			this.priceAlgorithm = priceAlgorithm;
		}
	}

	private final Type type;

	public Movie(Type type) {
		this.type = type;
	}


}

@Service
class MoviePriceService {
//	@Autowired
//	private MovieFactorRepo repo;
	public int computePrice(Movie.Type movieType, int days) {
//		switch (movieType) {
//			case REGULAR: return regularPrice(days);
//			case NEW_RELEASE: return newReleasePrice(days); // here, call the repo
//			case CHILDREN: return childrensPrice(days);
//			default: throw new IllegalArgumentException(movieType.name());
//		}
		return movieType.priceAlgorithm.apply(this,days);
	}

	public int childrensPrice(int days) {
		return 5;
	}

	public int newReleasePrice(int days) {
		return days * 2;
	}

	public int regularPrice(int days) {
		return days + 1;
	}
}



public class E__TypeSpecific_Functionality {
	public static void main(String[] args) {
//		System.out.println(new Movie(Movie.Type.REGULAR).computePrice(2));
//		System.out.println(new Movie(Movie.Type.NEW_RELEASE).computePrice(2));
//		System.out.println(new Movie(Movie.Type.CHILDREN).computePrice(2));
		System.out.println("COMMIT now!");
	}
}
