package spring.training.proxy;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.FileUtils;
import org.jooq.lambda.Unchecked;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExpensiveOps {
	private final static Logger log = LoggerFactory.getLogger(ExpensiveOps.class);
	
	private static final BigDecimal TWO = new BigDecimal("2");
	
	public Boolean isPrime(int n) {
		log.debug("Computing isPrime({})", n);
		
//		new RuntimeException().printStackTrace();
		
		BigDecimal number = new BigDecimal(n);
		if (number.compareTo(TWO) <= 0) {
			return true;
		}
		if (number.remainder(TWO).equals(BigDecimal.ZERO)) {
			return false;
		}
		for (BigDecimal divisor = new BigDecimal("3"); 
			divisor.compareTo(number.divide(TWO)) < 0;
			divisor = divisor.add(TWO)) {
			if (number.remainder(divisor).equals(BigDecimal.ZERO)) {
				return false;
			}
		}
		return true;
	}
	public String hashAllFiles(File folder) {
		log.debug("Computing hashAllFiles({})", folder);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			for (int i = 0; i < 2; i++) { // pretend there is much more work to do here
				Files.walk(folder.toPath())
					.map(Path::toFile)
					.filter(File::isFile)
					.map(Unchecked.function(FileUtils::readFileToString))
					.forEach(s -> md.update(s.getBytes()));
			}
			byte[] digest = md.digest();
		    return DatatypeConverter.printHexBinary(digest).toUpperCase();
		} catch (NoSuchAlgorithmException | IOException e) {
			throw new RuntimeException(e);
		}
	}
//	@CacheEvict("folders")
	public void invalidateCacheForFolder(File file) {
		 // Do not touch; Let the magic happen!!..
	}

}
