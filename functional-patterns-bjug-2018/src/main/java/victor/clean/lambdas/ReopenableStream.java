package victor.clean.lambdas;

import java.util.Iterator;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ReopenableStream {

    void processOnce(Iterator<String> linesIterator) {
        // you only get one shot at the data
    }

    void sweepAsManyTimesYouWant(Iterable<String> linesIterator) {
        for (String line : linesIterator) {
        }
        for (String line : linesIterator) {
        }
        // you only get one shot at the data
    }

    void processOnce8(Stream<String> linesIterator) {
        // you only get one shot at the data
    }

    void sweepAsManyTimesYouWant8(Supplier<Stream<String>> linesIterator) {
        try (Stream<String> lineStream = linesIterator.get()) {

        }
        try (Stream<String> lineStream = linesIterator.get()) {

        }
        // yo


    }
}