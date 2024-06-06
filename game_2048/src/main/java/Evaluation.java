import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;
import static java.util.Arrays.asList;
import static java.util.Collections.max;

public class Evaluation {
    private final Game game;
    private int score = 0;
    private double maxVal = 0.0;

    Evaluation(RecurrentNeuralNetwork agent, int tries, int maxMoves, int seed) {
        //Game.setUpGUI();
        game = new Game();
        game.setUpGUI();
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            ;
        }
        game.newGame(seed);
        for(int j = 0; j < tries; j++) {
            game.newGame(seed);
            agent.resetLayers();

            int i = 0;

            while(!game.gameOver() && i < maxMoves) {
                agent.activate(game.getState());
                game.move(agent.getOutputLayer());

                System.out.println(Arrays.toString(game.getState()));
                //game.paint();

                if(Arrays.stream(game.getState()).max().getAsDouble() > maxVal) {
                    maxVal = Arrays.stream(game.getState()).max().getAsDouble();
                }

                i++;

                try {
                    sleep(100);
                } catch(InterruptedException e) {
                    ;
                }
            }

            score += game.getScore();
        }
        score /= tries;
    }

    public double getScore() {
        return score;
    }

    public double getMaxVal() {
        return maxVal;
    }
}
