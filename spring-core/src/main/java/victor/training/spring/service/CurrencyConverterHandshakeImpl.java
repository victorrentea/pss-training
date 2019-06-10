package victor.training.spring.service;

import javax.annotation.PostConstruct;

//TODO what about profiles ?
public class CurrencyConverterHandshakeImpl implements CurrencyConverter {

	@PostConstruct
	public void initialize() {
		/* Time-consuming initialization that must be called before first usage */
		System.out.println("Currency Converter - HandshakeImpl initialized");
	}

	@Override
	public Double convert(Double amount, String currencyFrom, String currencyTo) {
		throw new UnsupportedOperationException();
	}

}
