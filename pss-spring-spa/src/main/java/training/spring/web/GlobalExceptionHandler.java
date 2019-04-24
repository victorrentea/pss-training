package training.spring.web;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private final static Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	private final static AtomicInteger ERROR_ID = new AtomicInteger();
	
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String buba(HttpServletRequest request, Exception e) {
		int errorId = ERROR_ID.incrementAndGet();
		
		String errorKey = e.getMessage();
		String eroareaPeLimbaLui = messageSource.getMessage(errorKey, null, "NOT_FOUND", request.getLocale());
		
		if ("NOT_FOUND".equals(eroareaPeLimbaLui)) {
			eroareaPeLimbaLui = messageSource.getMessage("error.internal", new Object[] {errorId}, request.getLocale());
		}
		
		log.error("ERROR #" +errorId, e.getMessage(),e);
		return eroareaPeLimbaLui;
	}
}
