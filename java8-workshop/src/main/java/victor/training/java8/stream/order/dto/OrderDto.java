package victor.training.java8.stream.order.dto;

import victor.training.java8.stream.order.entity.Order;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderDto {

	public BigDecimal totalPrice;
	public LocalDate creationDate;

	public OrderDto(Order order) {
		this.totalPrice = order.getTotalPrice();
		this.creationDate = order.getCreationDate();
	}

	public OrderDto() {
	}
}
