public class Vector {
    private double up;
    private double right;
    private double down;
    private double left;

    public Vector(float up, float right, float down,float left) {
        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;
    }
    
    public Moves getMove() {
        double maxValue = this.up;
        Moves move = Moves.UP;

        if (right > maxValue) {
            maxValue = right;
            move = Moves.RIGHT;
        }

        if (down > maxValue) {
            maxValue = down;
            move = Moves.DOWN;
        }

        if (left > maxValue) {
            move = Moves.LEFT;
        }

        return move;
    }
}
