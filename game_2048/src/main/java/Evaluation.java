import java.util.Arrays;

public class Evaluation {
    private double error = 0;
    private final GameInterface game = new Game();
    private int score = 0;

    Evaluation(RecurrentNeuralNetwork agent) {
        int tries = 5;
        for(int j = 0; j < tries; j++) {
            game.newGame();
            agent.resetLayers();

            int i = 0;

            while(!game.gameOver() && i < 100) {
                agent.activate(game.getState());
                game.move(agent.getOutputLayer());

                i++;
            }

            score += game.getScore();
        }
        score /= tries;
    }

    public double getScore() {
        return score;
    }

}
