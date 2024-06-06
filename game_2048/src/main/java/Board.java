import java.util.Random;

public class Board
{
    public Tile[][] board;

    private final Random RANDOM;

    int grids = 4;

    int border = 0;

    public int score = 0;


   // 4x4 matrix
    public Board(int seed)
    {
        RANDOM = new Random(seed);
        board = new Tile[4][4];
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                board[i][j] = new Tile();
            }
        }
    }

    public double[] flattenBoard() {
        double[] flatBoard = new double[16];
        int index = 0;

        for (Tile[] tiles : board) {
            for (Tile tile : tiles) {
                //flatBoard[index++] = tile.getValue();
                if(tile.getValue() > 0) {
                    flatBoard[index++] = Math.log(tile.getValue())/Math.log(2);
                } else {
                    flatBoard[index++] = 0.0;
                }
            }
        }

        return flatBoard;
    }

    public int getScore()
    {
        return score;
    }


    // return the highest value on the board
    public int getHighTile()
    {
        int high = board[0][0].getValue();
        for (Tile[] tiles : board) {
            for (Tile tile : tiles) {
                if (tile.getValue() > high) {
                    high = tile.getValue();
                }
            }
        }
        return high;
    }


    // return the board as the string
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        for (Tile[] tiles : board) {
            for (Tile tile : tiles) {
                s.append(tile.toString()).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }


    // spawn "2" or "4"
    public void spawn()
    {
        boolean empty = true;
        while ( empty )
        {
            int r = RANDOM.nextInt(16);
            int row = r/4;
            int col = r%4;
            double x = RANDOM.nextDouble();
            if ( board[row][col].getValue() == 0 )
            {
                if ( x < 0.2 )
                {
                    board[row][col] = new Tile( 4 );
                    empty = false;
                }
                else
                {
                    board[row][col] = new Tile( 2 );
                    empty = false;
                }
            }

        }

    }


    /**
     * Checks to see if the board is completely blacked out and if it is, it
     * will give a suggestion to the player - nudging them to restart
     */
    public boolean blackOut()
    {
        int count = 0;
        for (Tile[] tiles : board) {
            for (Tile tile : tiles) {
                if (tile.getValue() > 0) {
                    count++;
                }
            }
        }
        return count == 16;
    }


    /**
     * Checks to see if the game is over - that is, checks if any tile (that
     * isn't a 0) is able to combine with the tiles next to it - If not, the
     * game is over
     */
    public boolean gameOver()
    {
        int count = 0;
        for ( int i = 0; i < board.length; i++ )
        {
            for ( int j = 0; j < board[i].length; j++ )
            {
                if ( board[i][j].getValue() > 0 )
                {
                    if ( i == 0 && j == 0 )
                    {
                        if ( board[i][j].getValue() != board[i + 1][j].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( i == 0 && j == 3 )
                    {
                        if ( board[i][j].getValue() != board[i + 1][j].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( i == 3 && j == 3 )
                    {
                        if ( board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( i == 3 && j == 0 )
                    {
                        if ( board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( i == 0 && ( j == 1 || j == 2 ) )
                    {
                        if ( board[i][j].getValue() != board[i + 1][j].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( i == 3 && ( j == 1 || j == 2 ) )
                    {
                        if ( board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue()
                                && board[i][j].getValue() != board[i][j - 1].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( j == 0 && ( i == 1 || i == 2 ) )
                    {
                        if ( board[i][j].getValue() != board[i][j + 1].getValue()
                                && board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i + 1][j].getValue() )
                        {
                            count++;
                        }
                    }
                    else if ( j == 3 && ( i == 1 || i == 2 ) )
                    {
                        if ( board[i][j].getValue() != board[i][j - 1].getValue()
                                && board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i + 1][j].getValue() )
                        {
                            count++;
                        }
                    }
                    else
                    {
                        if ( board[i][j].getValue() != board[i][j - 1].getValue()
                                && board[i][j].getValue() != board[i][j + 1].getValue()
                                && board[i][j].getValue() != board[i - 1][j].getValue()
                                && board[i][j].getValue() != board[i + 1][j].getValue() )
                        {
                            count++;
                        }
                    }
                }
            }
        }
        return count == 16;
    }


    /**
     *
     * This method is called when a 'w' or up arrow is pressed - goes through
     * the entire board and calls verticalMove with an "up" parameter for each
     * tile
     */
    public boolean up()
    {
        boolean hasMoved = false;

        for ( int i = 0; i < grids; i++ )
        {
            border = 0;
            for ( int j = 0; j < grids; j++ )
            {
                if ( board[j][i].getValue() != 0 )
                {
                    if ( border <= j )
                    {
                        if(verticalMove( j, i, Moves.UP ))
                            hasMoved = true;
                    }
                }
            }
        }

        return hasMoved;
    }


    /**
     *
     * This method is called when a 's' or down arrow is pressed - goes through
     * the entire board and calls verticalMove with a "down" parameter for each
     * tile
     */
    public boolean down()
    {
        boolean hasMoved = false;
        for ( int i = 0; i < grids; i++ )
        {
            border = ( grids - 1 );
            for ( int j = grids - 1; j >= 0; j-- )
            {
                if ( board[j][i].getValue() != 0 )
                {
                    if ( border >= j )
                    {
                        if(verticalMove( j, i, Moves.DOWN ))
                            hasMoved = true;
                    }
                }
            }
        }

        return hasMoved;
    }


    /*
     * Compares two tile's values together and if they are the same or if one is
     * equal to 0 (plain tile) - their values are added (provided that the tiles
     * we are comparing are two different tiles, and they are moving towards the
     * appropriate direction) - Uses recursion to go through the entire column
     *
     * Returns true if at least one tile moved
     */
    private boolean verticalMove( int row, int col, Moves direction )
    {
        Tile initial = board[border][col];
        Tile compare = board[row][col];
        if ( initial.getValue() == 0 || initial.getValue() == compare.getValue() )
        {
            if ( row > border || (direction.equals(Moves.DOWN) && (row < border)) )
            {
                int addScore = initial.getValue() + compare.getValue();
                if ( initial.getValue() != 0 )
                {
                    score += addScore;
                }
                initial.setValue( addScore );
                compare.setValue( 0 );
                return true;
            }
            return false;
        }
        else
        {
            if ( direction.equals(Moves.DOWN) )
            {
                border--;
            }
            else
            {
                border++;
            }
            return verticalMove( row, col, direction );
        }
    }


    /**
     *
     * This method is called when an 'a' or left arrow is pressed - goes through
     * the entire board and calls horizontalMove with a "left" parameter for
     * each tile
     */
    public boolean left()
    {
        boolean hasMoved = false;
        for ( int i = 0; i < grids; i++ )
        {
            border = 0;
            for ( int j = 0; j < grids; j++ )
            {
                if ( board[i][j].getValue() != 0 )
                {
                    if ( border <= j )
                    {
                        if(horizontalMove( i, j, Moves.LEFT ))
                            hasMoved = true;
                    }
                }
            }
        }

        return hasMoved;
    }


    /**
     *
     * This method is called when a 'd' or right arrow is pressed - goes through
     * the entire board and calls horizontalMove with a "right" parameter for
     * each tile
     */
    public boolean right()
    {
        boolean hasMoved = false;
        for ( int i = 0; i < grids; i++ )
        {
            border = ( grids - 1 );
            for ( int j = ( grids - 1 ); j >= 0; j-- )
            {
                if ( board[i][j].getValue() != 0 )
                {
                    if ( border >= j )
                    {
                        if(horizontalMove( i, j, Moves.RIGHT ))
                            hasMoved = true;
                    }
                }
            }
        }

        return hasMoved;
    }

    private boolean horizontalMove( int row, int col, Moves direction )
    {
        Tile initial = board[row][border];
        Tile compare = board[row][col];
        if ( initial.getValue() == 0 || initial.getValue() == compare.getValue() )
        {
            if ( col > border || ( direction.equals(Moves.RIGHT) && ( col < border ) ) )
            {
                int addScore = initial.getValue() + compare.getValue();
                if ( initial.getValue() != 0 )
                {
                    score += addScore;
                }
                initial.setValue( addScore );
                compare.setValue( 0 );
                return true;
            }
            return false;
        }
        else
        {
            if ( direction.equals(Moves.RIGHT) )
            {
                border--;
            }
            else
            {
                border++;
            }
            return horizontalMove( row, col, direction );
        }
    }

}