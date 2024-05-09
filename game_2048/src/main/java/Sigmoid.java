import java.util.function.Function;

import static java.lang.Math.exp;

public class Sigmoid implements Function<Double,Double> {
    @Override
    public Double apply(Double x) {
        return 1/(1+exp(-x));
    }
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
