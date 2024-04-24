import java.util.*;

import javafx.util.Pair;

public class Main {
    public static void main(String[] args) {

        Random random = new Random(2137);
        List<Integer> layers = List.of(16,16,16,8,4);
        int population_size = 50; // rozmiar populacji
        int best_size = 10; // ile osobników przeżywa i tworzy nowe osobniki
        double random_probability = 0.001; // prawdopodobieństwo powstania nowego losowego osobnika
        int generations = 10000; // liczba pokoleń

        RecurrentNeuralNetwork best;

        List<RecurrentNeuralNetwork> population = new ArrayList<>();
        List<Pair<Double,Integer>> rating = new ArrayList<>();

        for(int i = 0; i < population_size; i++) {
            population.add(new RecurrentNeuralNetwork(new ArrayList<>(layers),new Sigmoid()));
        }

        for(int j = 0; j < generations; j++) {
            double sum = 0;
            for(int i = 0; i < population_size; i++) {
                rating.add(new Pair<>(-new Evaluation(population.get(i)).getScore(),i));
                sum += rating.get(i).getKey();
            }

            rating.sort(Comparator.comparing(Pair::getKey));

            List<Integer> topRNNs = new ArrayList<>();
            for(int i = 0; i < best_size; i++) {
                topRNNs.add(rating.get(i).getValue());
            }

            best = population.get(rating.get(0).getValue());

            //if(j % (100*(1+j/1000)) == 0) {
                System.out.print(j);
                System.out.print('\t');
                System.out.print(-rating.get(0).getKey());
                System.out.print('\t');
                System.out.println(-sum/50);
            //}

            for(int i = 0; i < population_size; i++) {
                if(!topRNNs.contains(i)) {
                    if(random.nextDouble() < random_probability) {
                        population.set(i,new RecurrentNeuralNetwork(new ArrayList<>(layers),new Sigmoid()));
                    } else {
                        population.set(i,new RecurrentNeuralNetwork(population.get(topRNNs.get(random.nextInt(10))),population.get(topRNNs.get(random.nextInt(10))),0.005));
                    }
                }
            }
        }


        //System.out.println(new Evaluation(test1).getError());
    }
}