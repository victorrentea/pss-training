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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@LoggedClass
public class ExpensiveOps {
	private final static Logger log = LoggerFactory.getLogger(ExpensiveOps.class);
	
	private static final BigDecimal TWO = new BigDecimal("2");
	
	@LoggedMethod
	@Cacheable("primes")
	// daca faci metodala finala sau privata Aspectele nu pot interveni.
	// pentru ca nu merge @Override
	public Boolean isPrime(int n) {
		log.debug("Computing isPrime({})", n);
		
		new RuntimeException().printStackTrace(System.out);
		
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
	
//	@Transactional(REQUIRES_NEW)
//	private void salveazaBatch(List<String> listaDe500) {
//		
//	}
//	
	
	@Autowired
	private ExpensiveOps euInOglinda;
	
	@Cacheable("folders")
	public String hashAllFiles(File folder) {
		log.debug("Computing hashAllFiles({})", folder);
		
		log.debug("Got: " + euInOglinda.isPrime(10_000_169) + "\n");
		
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

	@CacheEvict("folders")
	public void aruncaCacheulPentruFolderul(File folder) {
		 // Let the magic happen
		System.out.println("Degeaba");
	}
}
