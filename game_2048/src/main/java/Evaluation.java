import java.util.Arrays;

import static java.util.Arrays.asList;
import static java.util.Collections.max;

public class Evaluation {
    private final GameInterface game = new Game();
    private int score = 0;
    private double maxVal = 0.0;

    Evaluation(RNNEvolutionStrategy agent, int tries, int maxMoves) {
        for(int j = 0; j < tries; j++) {
            game.newGame();
            agent.resetLayers();

            int i = 0;

            while(!game.gameOver() && i < maxMoves) {
                agent.activate(game.getState());
                game.move(agent.getOutputLayer());

                if(Arrays.stream(game.getState()).max().getAsDouble() > maxVal) {
                    maxVal = Arrays.stream(game.getState()).max().getAsDouble();
                }

                i++;
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
