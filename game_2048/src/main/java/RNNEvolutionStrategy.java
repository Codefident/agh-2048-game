import org.la4j.Matrix;
import org.la4j.Vector;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.vector.dense.BasicVector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

public class RNNEvolutionStrategy {
    private final List<Matrix> weightsW = new ArrayList<>();
    private final List<Matrix> weightsV = new ArrayList<>();
    private final List<Vector> weightsB = new ArrayList<>();
    private final List<double[][]> deviationW = new ArrayList<>();
    private final List<double[][]> deviationV = new ArrayList<>();
    private final List<double[]> deviationB = new ArrayList<>();
    private final List<Vector> layers = new ArrayList<>();
    private final Function<Double,Double> activation;
    private static final Random RANDOM = new Random(2137);

    RNNEvolutionStrategy(List<Integer> nodes, Function<Double,Double> function) {
        this.activation = function;

        for(int i = 0; i < nodes.size()-1; i++) {
            double[][] temp = new double[nodes.get(i+1)][nodes.get(i)];
            double[][] temp2 = new double[nodes.get(i+1)][nodes.get(i)];

            for(int x = 0; x < nodes.get(i+1); x++) {
                for(int y = 0; y < nodes.get(i); y++) {
                    temp[x][y] = RANDOM.nextGaussian();
                    temp2[x][y] = RANDOM.nextGaussian();
                }
            }

            weightsW.add(new Basic2DMatrix(temp));
            deviationW.add(temp2);
        }

        for(int i = 1; i < nodes.size(); i++) {
            double[][] temp = new double[nodes.get(i)][nodes.get(i)];
            double[][] temp2 = new double[nodes.get(i)][nodes.get(i)];

            for(int x = 0; x < nodes.get(i); x++) {
                for(int y = 0; y < nodes.get(i); y++) {
                    temp[x][y] = RANDOM.nextGaussian();
                    temp2[x][y] = RANDOM.nextGaussian();
                }
            }

            weightsV.add(new Basic2DMatrix(temp));
            deviationV.add(temp2);
        }

        for(int i = 1; i < nodes.size(); i++) {
            double[] temp = new double[nodes.get(i)];
            double[] temp2 = new double[nodes.get(i)];

            for(int x = 0; x < nodes.get(i); x++) {
                temp[x] = RANDOM.nextGaussian();
                temp2[x] = RANDOM.nextGaussian();
            }

            weightsB.add(new BasicVector(temp));
            deviationB.add(temp2);
        }

        for(Integer layer : nodes) {
            layers.add(new BasicVector(new double[layer]));
        }
    }

    RNNEvolutionStrategy(RNNEvolutionStrategy firstRNN, RNNEvolutionStrategy secondRNN, double probability) {
        this.activation = firstRNN.activation;

        for(int i = 0; i < firstRNN.weightsW.size(); i++) {
            double[][] temp = new double[firstRNN.weightsW.get(i).rows()][firstRNN.weightsW.get(i).columns()];
            double randGaussian = RANDOM.nextGaussian();

            for(int x = 0; x < firstRNN.weightsW.get(i).rows(); x++) {
                for(int y = 0; y < firstRNN.weightsW.get(i).columns(); y++) {
                    temp[x][y] = firstRNN.deviationW.get(i)[x][y]*0.5+
                                secondRNN.deviationW.get(i)[x][y]*0.5;

                    temp[x][y] *= Math.exp(randGaussian-RANDOM.nextGaussian());
                }
            }

            deviationW.add(temp);

            temp = new double[firstRNN.weightsW.get(i).rows()][firstRNN.weightsW.get(i).columns()];

            for(int x = 0; x < firstRNN.weightsW.get(i).rows(); x++) {
                for(int y = 0; y < firstRNN.weightsW.get(i).columns(); y++) {
                    if(RANDOM.nextInt(2) == 0) {
                        temp[x][y] = firstRNN.weightsW.get(i).get(x,y);
                    } else {
                        temp[x][y] = secondRNN.weightsW.get(i).get(x,y);
                    }

                    temp[x][y] += deviationW.get(i)[x][y]*RANDOM.nextGaussian();
                }
            }

            weightsW.add(new Basic2DMatrix(temp));
        }

        for(int i = 0; i < firstRNN.weightsV.size(); i++) {
            double[][] temp = new double[firstRNN.weightsV.get(i).rows()][firstRNN.weightsV.get(i).columns()];
            double randGaussian = RANDOM.nextGaussian();

            for(int x = 0; x < firstRNN.weightsV.get(i).rows(); x++) {
                for(int y = 0; y < firstRNN.weightsV.get(i).columns(); y++) {
                    temp[x][y] = firstRNN.deviationV.get(i)[x][y]*0.5+
                                secondRNN.deviationV.get(i)[x][y]*0.5;

                    temp[x][y] *= Math.exp(randGaussian-RANDOM.nextGaussian());
                }
            }

            deviationV.add(temp);

            temp = new double[firstRNN.weightsV.get(i).rows()][firstRNN.weightsV.get(i).columns()];

            for(int x = 0; x < firstRNN.weightsV.get(i).rows(); x++) {
                for(int y = 0; y < firstRNN.weightsV.get(i).columns(); y++) {
                    if(RANDOM.nextInt(2) == 0) {
                        temp[x][y] = firstRNN.weightsV.get(i).get(x,y);
                    } else {
                        temp[x][y] = secondRNN.weightsV.get(i).get(x,y);
                    }

                    temp[x][y] += deviationV.get(i)[x][y]*RANDOM.nextGaussian();
                }
            }

            weightsV.add(new Basic2DMatrix(temp));
        }

        for(int i = 0; i < firstRNN.weightsB.size(); i++) {
            double[] temp = new double[firstRNN.weightsB.get(i).length()];
            double randGaussian = RANDOM.nextGaussian();

            for(int x = 0; x < firstRNN.weightsB.get(i).length(); x++) {
                temp[x] = firstRNN.deviationB.get(i)[x]*0.5+
                         secondRNN.deviationB.get(i)[x]*0.5;

                temp[x] *= Math.exp(randGaussian-RANDOM.nextGaussian());
            }

            deviationB.add(temp);

            temp = new double[firstRNN.weightsB.get(i).length()];

            for(int x = 0; x < firstRNN.weightsB.get(i).length(); x++) {
                if(RANDOM.nextInt(2) == 0) {
                    temp[x] = firstRNN.weightsB.get(i).get(x);
                } else {
                    temp[x] = secondRNN.weightsB.get(i).get(x);
                }

                temp[x] += deviationB.get(i)[x]*RANDOM.nextGaussian();
            }

            weightsB.add(new BasicVector(temp));
        }

        layers.addAll(firstRNN.layers);
        resetLayers();
    }

    public void resetLayers() {
        for(int i = 0; i < weightsW.size()+1; i++) {
            layers.set(i,new BasicVector(new double[layers.get(i).length()]));
        }
    }

    public void activate(double[] inputLayer) {
        layers.set(0,new BasicVector(inputLayer));
        for(int i = 0; i < weightsW.size(); i++) {
            Vector temp = weightsW.get(i).multiply(layers.get(i)).add(weightsV.get(i).multiply(layers.get(i+1))).add(weightsB.get(i));

            for(int j = 0; j < temp.length(); j++) {
                temp.set(j,activation.apply(temp.get(j)));
            }

            layers.set(i+1,temp);
        }
    }

    public Vector getOutputLayer() {
        return layers.get(layers.size()-1);
    }

}
