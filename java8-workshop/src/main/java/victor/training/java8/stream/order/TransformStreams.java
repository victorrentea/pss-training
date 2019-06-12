package victor.training.java8.stream.order;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.summingLong;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jooq.lambda.function.Function3;
import org.junit.Rule;
import victor.training.java8.stream.order.dto.OrderDto;
import victor.training.java8.stream.order.entity.Customer;
import victor.training.java8.stream.order.entity.Order;
import victor.training.java8.stream.order.entity.OrderLine;
import victor.training.java8.stream.order.entity.Product;
import victor.training.java8.stream.order.entity.Order.PaymentMethod;

public class TransformStreams {

	/**
	 * Transform all entities to DTOs.
	 * Discussion:.. Make it cleanest!
	 */
	public List<OrderDto> p01_toDtos(List<Order> orders) {
		//
		Function<Order, OrderDto> function = this::toDto;
		OrderDto dto = function.apply(new Order());

		// I don't pass a reference to an instance of TransformStreams when I pick up the method reference.
		// as such, I need to provide the instance of TransformStreams when I will call this BiFunction
		BiFunction<TransformStreams, Order, OrderDto> biFunction = TransformStreams::toDto;
		OrderDto dto2 = biFunction.apply(this, new Order());

		Function<Order, LocalDate> getCreationDate = Order::getCreationDate;
		Order order = new Order();
		//all these threeeeee interfaces have ONE method that takes no parameter
		// this works because of 'Target Typing" - synctactic sugar in Java8
		Supplier<LocalDate> getCreationDateWithInstance = order::getCreationDate;
		Callable<LocalDate> getCreationDateWithInstance$ = order::getCreationDate;
		Runnable getCreationDateWithInstance$$ = order::getCreationDate;

//		f():Date
		Supplier<Date> noArgsCtr = Date::new; // new Date()
		Function<Long, Date> ctrWithLong = Date::new;//new Date(long);

//		return orders.stream().map(this::toDto).collect(toList());
		return orders.stream().map(OrderDto::new).collect(toList());


	}

	static String manyArgs(int a, int b, int c) {
		return "";
	}

	{
//		TriFunction<Integer, Integer, Integer, String> // not in JDK
		Function3<Integer, Integer, Integer, String> f = TransformStreams::manyArgs;
		String apply = f.apply(1, 2, 3);
		passAFunction(f);

	}

	private void passAFunction(Function3<Integer, Integer, Integer, String> f) {
	}

	private OrderDto toDto(Order order) {
		OrderDto dto = new OrderDto();
		dto.totalPrice = order.getTotalPrice();
		dto.creationDate = order.getCreationDate();
		return dto;
	}

	/**
	 * Note: Order.getPaymentMethod()
	 */
	public Set<PaymentMethod> p02_getUsedPaymentMethods(Customer customer) {
		return customer.getOrders()
				.stream()
				.map(Order::getPaymentMethod)
				.collect(toSet());
	}
	
	/**
	 * When did the customer created orders ?
	 * Note: Order.getCreationDate()
	 */
	public SortedSet<LocalDate> p03_getOrderDatesAscending(Customer customer) {
		return customer.getOrders()
				.stream()
				.map(Order::getCreationDate)
				.collect(toCollection(TreeSet::new));
	}
	
	
	/**
	 * @return a map order.id -> order
	 */
	public Map<Long, Order> p04_mapOrdersById(Customer customer) {
		return customer.getOrders()
				.stream()
				.collect(toMap(Order::getId, o -> o));
	}
	
	/** 
	 * Orders grouped by Order.paymentMethod
	 */
	public Map<PaymentMethod, List<Order>> p05_getProductsByPaymentMethod(Customer customer) {
		return customer.getOrders()
				.stream()
				.collect(groupingBy(Order::getPaymentMethod));
	}
	
	// -------------- MOVIE BREAK :p --------------------
	
	/** 
	 * A hard one !
	 * Get total number of products bought by a customer, across all her orders.
	 * Customer --->* Order --->* OrderLines(.count .product)
	 * The sum of all counts for the same product.
	 * i.e. SELECT PROD_ID, SUM(COUNT) FROM PROD GROUPING BY PROD_ID
	 */
	public Map<Product, Long> p06_getProductCount(Customer customer) {
		return customer.getOrders()
				.stream()
				.flatMap(o -> o.getOrderLines().stream())
//				.map(Order::getOrderLines)
//				.flatMap(List::stream)
				.collect(groupingBy(OrderLine::getProduct, summingLong(OrderLine::getCount)));

	}
	
	/**
	 * All the unique products bought by the customer, 
	 * sorted by Product.name.
	 */
	public List<Product> p07_getAllOrderedProducts(Customer customer) {
		Stream<OrderLine> hehehe = customer.getOrders()
				.stream()
				.flatMap(o -> {
					System.out.println("BING");
					return o.getOrderLines().stream();
				});
		System.out.println("how many times did the lambda above ran until this line");
		List<Product> result = hehehe
				.map(OrderLine::getProduct)
				.distinct() // piles up all the elements in stream (consumes it all) before providing its first result
				.sorted(comparing(Product::getName)) // piles up all the elements in stream (consumes it all) before providing its first result
				.collect(toList());
		System.out.println("END");
		return result;
	}
	
	
	/**
	 * The names of all the products bought by Customer,
	 * sorted and then concatenated by ",".
	 * Example: "Armchair,Chair,Table".
	 * Hint: Reuse the previous function.
	 */
	public String p08_getProductsJoined(Customer customer) {
		return p07_getAllOrderedProducts(customer).stream()
				.map(Product::getName)
				.collect(joining(","));
	}
	
	/**
	 * Sum of all Order.getTotalPrice(), truncated to Long.
	 */
	public Long p09_getApproximateTotalOrdersPrice(Customer customer) {
		BinaryOperator<BigDecimal> sum = (bd1, bd2) -> bd1.add(bd2);
		BiFunction<BigDecimal, BigDecimal, BigDecimal> sum2 = (bd1, bd2) -> bd1.add(bd2);
		BiFunction<BigDecimal, BigDecimal, BigDecimal> sum3 = BigDecimal::add;

		return customer.getOrders()
				.stream()

				// form1
				.mapToLong(o -> o.getTotalPrice().longValue())
				.sum();

				// form2
//				.map(Order::getTotalPrice)
//				.reduce(BigDecimal.ZERO, BigDecimal::add)
//				.longValue();
	}
	
	// ----------- IO ---------------
	
	/**
	 * - Read lines from file as Strings. 
	 * - Where do you close the opened file?
	 * - Parse those to OrderLine using the function bellow
	 * - Validate the created OrderLine. Throw ? :S
	 */
	public List<OrderLine> p10_readOrderFromFile(File file) throws IOException {
		
		try (Stream<String> lines = Files.lines(file.toPath())) {
			return lines
					.map(line -> line.split(";")) // Stream<String[]>
					.filter(cell -> "LINE".equals(cell[0]))
					.map(this::parseOrderLine) // Stream<OrderLine>
					.peek(this::validateOrderLine)
					.collect(toList());
		}

	}
	
	private OrderLine parseOrderLine(String[] cells) {
		return new OrderLine(new Product(cells[1]), Integer.parseInt(cells[2]));
	}
	
	private void validateOrderLine(OrderLine orderLine) {
		if (orderLine.getCount() < 0) {
			throw new IllegalArgumentException("Negative items");			
		}
	}
	
	
	// TODO print cannonical paths of all files in current directory
	// use Unchecked... stuff
}
