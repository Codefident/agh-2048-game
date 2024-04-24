import org.la4j.Vector;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements KeyListener, GameInterface
{
    Board game = new Board();

    static Game startNewGame = new Game();

    static JFrame frame = new JFrame( "2048" );

    static Color green;

    String gameBoard = game.toString();


    // set up GUI, add key listeners
    public static void setUpGUI()
    {
        frame.addKeyListener( startNewGame );
        frame.getContentPane().add( startNewGame );
        frame.setSize( 600, 400 );
        frame.setVisible( true );
        frame.setResizable( false );
    }

    // WASD
    @Override
    public void keyPressed( KeyEvent e )
    {
        if ( e.getKeyChar() == 'w' || e.getKeyCode() == KeyEvent.VK_UP )
        {
            if(!game.up()) return;
            game.spawn();
            gameBoard = game.toString();
            frame.repaint();
        }
        else if ( e.getKeyChar() == 's' || e.getKeyCode() == KeyEvent.VK_DOWN )
        {
            if(!game.down()) return;
            game.spawn();
            gameBoard = game.toString();
            frame.repaint();
        }
        else if ( e.getKeyChar() == 'a' || e.getKeyCode() == KeyEvent.VK_LEFT )
        {
            if(!game.left()) return;
            game.spawn();
            gameBoard = game.toString();
            frame.repaint();
        }
        else if ( e.getKeyChar() == 'd' || e.getKeyCode() == KeyEvent.VK_RIGHT )
        {
            if(!game.right()) return;
            game.spawn();
            gameBoard = game.toString();
            frame.repaint();
        }
        else if ( e.getKeyCode() == KeyEvent.VK_ENTER )
        {
            game = new Board();
            game.spawn();
            game.spawn();
            frame.repaint();
        }
    }


    @Override
    public void keyReleased( KeyEvent e )
    {
        // Not Used
    }


    @Override
    public void keyTyped( KeyEvent e )
    {
        // Not Used
    }


    // painting`
    public void paint( Graphics g )
    {
        super.paint( g );
        Graphics2D g2 = (Graphics2D)g;
        g2.drawString( "2048", 250, 20 );
        g2.drawString( "Wynik: " + game.getScore(),
                200 - 4 * String.valueOf( game.getScore() ).length(),
                40 );
        g2.drawString( "Najwieksze pole: " + game.getHighTile(),
                280 - 4 * String.valueOf( game.getHighTile() ).length(),
                40 );
        g2.drawString( "Nacisnij 'Enter' aby zaczac", 210, 315 );
        g2.drawString( "Poruszanie sie - WSAD lub strzalki", 180, 335 );
        if ( game.blackOut() )
        {
            g2.drawString( "Nacisnij 'Enter' zeby zrestartowac", 200, 355 );
        }
        g2.setColor( Color.gray );
        g2.fillRect( 140, 50, 250, 250 );
        for ( int i = 0; i < 4; i++ )
        {
            for ( int j = 0; j < 4; j++ )
            {
                drawTiles( g, game.board[i][j], j * 60 + 150, i * 60 + 60 );
            }
        }
        if ( game.gameOver() )
        {
            //this.gameOver();

            g2.setColor( Color.gray );
            g2.fillRect( 140, 50, 250, 250 );
            for ( int i = 0; i < 4; i++ )
            {
                for ( int j = 0; j < 4; j++ )
                {
                    g2.setColor( Color.RED );
                    g2.fillRoundRect( j * 60 + 150, i * 60 + 60, 50, 50, 5, 5 );
                    g2.setColor( Color.black );
                    g.drawString( "GAME", j * 60 + 160, i * 60 + 75 );
                    g.drawString( "OVER", j * 60 + 160, i * 60 + 95 );
                }
            }
        }
    }


    // draws a tile
    public void drawTiles( Graphics g, Tile tile, int x, int y )
    {
        int tileValue = tile.getValue();
        int length = String.valueOf( tileValue ).length();
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor( Color.lightGray );
        g2.fillRoundRect( x, y, 50, 50, 5, 5 );
        g2.setColor( Color.black );
        if ( tileValue > 0 )
        {
            g2.setColor( tile.getColor() );
            g2.fillRoundRect( x, y, 50, 50, 5, 5 );
            g2.setColor( Color.black );
            g.drawString( "" + tileValue, x + 25 - 3 * length, y + 25 );
        }
    }

    // GameInterface
    @Override
    public void newGame() {
        game = new Board();
        game.spawn();
        game.spawn();
        frame.repaint();
    }

    @Override
    public void move(Vector vector) {
        Moves move = Moves.UP;
        double maxValue = 0;

        // vector: [up, right, down, left]
        for (int i = 0; i < vector.length(); i++) {
            double value = vector.get(i);
            if (value > maxValue) {
                maxValue = value;
                switch (i) {
                    case 0 -> move = Moves.UP;
                    case 1 -> move = Moves.RIGHT;
                    case 2 -> move = Moves.DOWN;
                    case 3 -> move = Moves.LEFT;
                }
            }
        }

        switch (move) {
            case UP -> {
                if(!game.up()) return;
                game.spawn();
                gameBoard = game.toString();
                frame.repaint();
            }

            case RIGHT -> {
                if(!game.right()) return;
                game.spawn();
                gameBoard = game.toString();
                frame.repaint();
            }

            case DOWN -> {
                if(!game.down()) return;
                game.spawn();
                gameBoard = game.toString();
                frame.repaint();
            }

            case LEFT -> {
                if(!game.left()) return;
                game.spawn();
                gameBoard = game.toString();
                frame.repaint();
            }
        }
    }

    @Override
    public int getScore() {
        //System.out.println("game over");
        return game.getScore();
    }

    @Override
    public boolean gameOver() {
        return game.gameOver();
    }

    @Override
    public double[] getState() {
        return game.flattenBoard();
    }
}