import javafx.util.Pair;

import java.util.*;
import java.util.function.Function;

import static java.util.Collections.max;

public class Main {
    public static void main(String[] args) {
        Random random = new Random(2137); // love

        List<Integer> layers = List.of(16,32,64,64,16,4);
        int populationSize = 80; // rozmiar populacji
        int bestSize = 10; // ile osobników przeżywa i tworzy nowe osobniki
        double randomProbability = 0.001; // prawdopodobieństwo powstania nowego losowego osobnika
        int generations = 1000; // liczba pokoleń
        double mutationProbability = 0.001; // prawdopodobieństwo mutacji genu
        int evaluationTries = 5; // na ilu grach ewaluujemy
        int maxMoves = 1000; // maksymalna liczba ruchów w grze
        Function<Double,Double> activationFunction = new Sigmoid(); // funkcja aktywacji





        RecurrentNeuralNetwork best = null;

        List<RecurrentNeuralNetwork> population = new ArrayList<>();

        // test - RNN from files
        String fileName_weightsW = "bestNetwork_weightsW.csv";
        String fileName_weightsV = "bestNetwork_weightsV.csv";
        String fileName_weightsB = "bestNetwork_weightsB.csv";
        //
        RecurrentNeuralNetwork rnn = new RecurrentNeuralNetwork(fileName_weightsW,fileName_weightsV,fileName_weightsB,layers);

        Evaluation eval = new Evaluation(rnn,evaluationTries,maxMoves);
        System.out.println(eval.getScore());
        /*for(int i = 0; i < populationSize; i++) {
            //population.add(new RecurrentNeuralNetwork(new ArrayList<>(layers),activationFunction));
            population.add(new RecurrentNeuralNetwork(fileName_weightsW, fileName_weightsV,fileName_weightsB,layers));
        }

        for(int j = 0; j < generations; j++) {
            List<Double> maxVals = new ArrayList<>();
            List<Pair<Double,Integer>> rating = new ArrayList<>();
            double sum = 0;

            for(int i = 0; i < populationSize; i++) {
                Evaluation eval = new Evaluation(population.get(i),evaluationTries,maxMoves);
                maxVals.add(eval.getMaxVal());
                rating.add(new Pair<>(-eval.getScore(),i));
                sum += eval.getScore();
            }

            rating.sort(Comparator.comparing(Pair::getKey));

            //System.out.println(rating);

            List<Integer> topRNNs = new ArrayList<>();
            for(int i = 0; i < bestSize; i++) {
                //System.out.println(rating.get(i));
                topRNNs.add(rating.get(i).getValue());
            }

            best = population.get(rating.get(0).getValue());
            //best.save();

            System.out.print(j);
            System.out.print('\t');
            System.out.print(-rating.get(0).getKey());
            System.out.print('\t');
            System.out.print(sum/populationSize);
            System.out.print('\t');
            System.out.println(Math.pow(2,max(maxVals)));
            

            for(int i = 0; i < populationSize; i++) {
                if(!topRNNs.contains(i)) {
                    if(random.nextDouble() < randomProbability) {
                        population.set(i,new RecurrentNeuralNetwork(new ArrayList<>(layers),new Sigmoid()));
                    } else {
                        population.set(i,new RecurrentNeuralNetwork(population.get(topRNNs.get(random.nextInt(bestSize))),population.get(topRNNs.get(random.nextInt(bestSize))),mutationProbability));
                    }
                }
            }
        }

        best.save();*/
    }
}