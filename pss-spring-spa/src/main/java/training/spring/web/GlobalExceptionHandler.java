package training.spring.web;

import java.text.ParseException;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	private final static AtomicInteger ERROR_ID = new AtomicInteger();
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String buba(Exception e) {
		int errorId = ERROR_ID.incrementAndGet();
		log.error("ERROR #" +errorId, e.getMessage(),e);
		return "Internal Server Error (#"+errorId+"). Please check the logs :p";
	}
}
