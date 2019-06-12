package victor.clean.lambdas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.function.Consumer;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

// export all orders to a file

interface OrderRepo extends JpaRepository<Order, Long> { // Spring Data FanClub
	Stream<Order> findByActiveTrue(); // 1 Mln orders ;)
}
@Service
class OrderExporter {
	private final static Logger log = LoggerFactory.getLogger(OrderExporter.class);

	public File exportFile(String fileName, Consumer<Writer> contentWriter) throws IOException {
		File file = new File("export/" + fileName);
		try (Writer writer = new FileWriter(file)) {
			contentWriter.accept(writer);
			return file;
		} catch (Exception e) {
			// TODO send email notification
//			log.debug("Gotcha!", e); // TERROR-Driven Development
			throw e;
		}
	}

	public static void main(String[] args) throws IOException {
		OrderExporter exporter = new OrderExporter();
		OrderContent content = null;//new OrderContent();

		exporter.exportFile("users.csv", content::writeUsersContent);
	}

}
@Service
@RequiredArgsConstructor
class OrderContent {
	private final OrderRepo orderRepo;
	@SneakyThrows
	public void writeOrdersContent(Writer writer) {
		writer.write("OrderID;Date\n");
		orderRepo.findByActiveTrue()
				.map(o -> o.getId() + ";" + o.getCreationDate())
				.forEach(Unchecked.consumer(writer::write));
	}

	private final UserRepo userRepo;
	@SneakyThrows
	public void writeUsersContent(Writer writer) {
		writer.write("Userid;lastName\n");
		userRepo.findAll().stream()
				.map(u -> u.getUsername() + ";" + u.getLastName())
				.forEach(Unchecked.consumer(writer::write));
	}

}

