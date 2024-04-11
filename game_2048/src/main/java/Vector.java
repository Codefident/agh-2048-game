public class Vector {
    private float up = 0;
    private float right = 0;
    private float down = 0;
    private float left = 0;

    public Vector(float up, float right, float down,float left) {
        this.up = up;
        this.right = right;
        this.down = down;
        this.left = left;
    }
    
    public Moves getMove() {
        float maxValue = this.up;
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
            maxValue = left;
            move = Moves.LEFT;
        }

        return move;
    }
}
