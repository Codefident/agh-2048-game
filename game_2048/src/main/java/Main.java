import java.util.*;
import java.util.function.Function;

import javafx.util.Pair;

import static java.util.Collections.max;

public class Main {
    public static void main(String[] args) {
        Random random = new Random(2137);




        List<Integer> layers = List.of(16,32,64,64,16,4);
        int populationSize = 80; // rozmiar populacji
        int bestSize = 10; // ile osobników przeżywa i tworzy nowe osobniki
        double randomProbability = 0.001; // prawdopodobieństwo powstania nowego losowego osobnika
        int generations = 10000; // liczba pokoleń
        double mutationProbability = 0.001; // prawdopodobieństwo mutacji genu
        int evaluationTries = 5; // na ilu grach ewaluujemy
        int maxMoves = 1000; // maksymalna liczba ruchów w grze
        Function<Double,Double> activationFunction = new ReLU(); // funkcja aktywacji





        RNNEvolutionStrategy best;

        List<RNNEvolutionStrategy> population = new ArrayList<>();

        for(int i = 0; i < populationSize; i++) {
            population.add(new RNNEvolutionStrategy(new ArrayList<>(layers),activationFunction));
        }

        for(int j = 0; j < generations; j++) {
            List<Double> maxVals = new ArrayList<>();
            List<Pair<Double,Integer>> rating = new ArrayList<>();
            double sum = 0;
            for(int i = 0; i < populationSize; i++) {
                Evaluation eval = new Evaluation(population.get(i),evaluationTries,maxMoves);
                maxVals.add(eval.getMaxVal());
                rating.add(new Pair<>(-eval.getScore(),i));
                sum += rating.get(i).getKey();
            }

            rating.sort(Comparator.comparing(Pair::getKey));

            List<Integer> topRNNs = new ArrayList<>();
            for(int i = 0; i < bestSize; i++) {
                topRNNs.add(rating.get(i).getValue());
            }

            best = population.get(rating.get(0).getValue());

            System.out.print(j);
            System.out.print('\t');
            System.out.print(-rating.get(0).getKey());
            System.out.print('\t');
            System.out.print(-sum/50);
            System.out.print('\t');
            System.out.println(Math.pow(2,max(maxVals)));

            for(int i = 0; i < populationSize; i++) {
                if(!topRNNs.contains(i)) {
                    if(random.nextDouble() < randomProbability) {
                        population.set(i,new RNNEvolutionStrategy(new ArrayList<>(layers),new Sigmoid()));
                    } else {
                        population.set(i,new RNNEvolutionStrategy(population.get(topRNNs.get(random.nextInt(bestSize))),population.get(topRNNs.get(random.nextInt(bestSize))),mutationProbability));
                    }
                }
            }
        }
    }
}