package spring.training.proxy;

import java.io.File;

public interface IExpensiveOps {

	Boolean isPrime(int n);

	String hashAllFiles(File folder);

}