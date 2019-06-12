package victor.clean.lambdas.parallel;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ToParallelOrNotToParallel {

    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        list.parallelStream()
                .filter(n -> {
                    ConcurrencyUtil.log("Filtering " + n);
                    return n % 2 == 1;
                })
                .map(n -> {
                    ConcurrencyUtil.log("Squaring " + n);
                    return n * n;
                })
                .forEach(x -> {
                    ConcurrencyUtil.log(x+"");
                });





//        fib();
    }

    private static void fib() {
        // 1 1 2 3 5 8
        Stream.iterate(new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ONE},
                old -> new BigDecimal[]{old[1],old[0].add(old[1])})
//                .map(Arrays::toString)
                .map(arr -> arr[1])
//                .limit(7)
                .forEach(System.out::println);
    }
}
