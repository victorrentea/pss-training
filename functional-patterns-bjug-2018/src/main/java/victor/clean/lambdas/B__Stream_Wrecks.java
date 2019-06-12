package victor.clean.lambdas;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import lombok.Data;

// get the products frequently ordered during the past year


class ProductService {
	private ProductRepo productRepo;

	public List<Product> getFrequentOrderedProducts(List<Order> orders) {
		Set<Long> hiddenProductIds = new HashSet<>(productRepo.getHiddenProductIds());
		List<Product> frequentProducts = getProductCounts(orders).entrySet()
				.stream()
				.filter(e -> e.getValue() >= 10)
				.map(Entry::getKey)
				.collect(toList());
		Predicate<Product> isNotHidden = p -> !hiddenProductIds.contains(p.getId());
		return frequentProducts.stream()
//				.filter(((Predicate<Product>) Product::isNotDeleted).negate())
				.filter(Product::isNotDeleted)
				.filter(isNotHidden)
				.collect(toList());
	}

	private Map<Product, Integer> getProductCounts(List<Order> orders) {
		return orders.stream()
				.filter(this::placedInThePreviousYear)
				.flatMap(o -> o.getOrderLines().stream())
				.collect(groupingBy(OrderLine::getProduct, summingInt(OrderLine::getItemCount)));
	}

	private boolean placedInThePreviousYear(Order o) {
		return o.getCreationDate().isAfter(LocalDate.now().minusYears(1));
	}
}





//VVVVVVVVV ==== supporting (dummy) code ==== VVVVVVVVV
@Data
class Product {
	private Long id;
	private boolean deleted;

	public boolean isNotDeleted() {
		return !deleted;
	}
}

@Data
class Order {
	private Long id;
	private List<OrderLine> orderLines;
	private LocalDate creationDate;
}

@Data
class OrderLine {
	private Product product;
	private int itemCount;
}

interface ProductRepo {
	List<Long> getHiddenProductIds();
}
