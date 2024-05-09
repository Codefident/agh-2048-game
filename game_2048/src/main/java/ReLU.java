import java.util.function.Function;

import static java.lang.Math.max;

public class ReLU implements Function<Double,Double> {
    @Override
    public Double apply(Double x) {
        return max(0,x);
    }
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
