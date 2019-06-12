package victor.clean.lambdas.parallel;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ToParallelOrNotToParallel {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        List<Integer> list = IntStream.range(1, 20).boxed().collect(Collectors.toList());
        long t0 = System.currentTimeMillis();
        IntStream stream = list.parallelStream()
                .filter(n -> {
                    ConcurrencyUtil.log("Filtering " + n);
                    return n % 2 == 1;
                })
                .mapToInt(n -> {
                    ConcurrencyUtil.log("Squaring " + n);
                    ConcurrencyUtil.sleep2(1000); // FAKE an IO
                    return n * n;
                });

        ForkJoinPool pool = new ForkJoinPool(7);

        ForkJoinTask<Integer> submit = pool.submit(() -> stream.sum());

        System.out.println("sum=" + submit.get());

        long t1 = System.currentTimeMillis();
        System.out.println("Delta = " + (t1 - t0));


    }

    private static void fib() {
        // 1 1 2 3 5 8
        Stream.iterate(new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ONE},
                old -> new BigDecimal[]{old[1], old[0].add(old[1])})
//                .map(Arrays::toString)
                .map(arr -> arr[1])
//                .limit(7)
                .forEach(System.out::println);
    }
}
