import org.la4j.Vector;

public interface GameInterface {
    /*
    Funkcja do rozpoczynania nowej rozgrywki (resetuje pola, wynik itp)
    */
    void newGame(int seed);

    /*
    Przyjmuje la4j.Vector, w tym wypadku czteroelementowy,
    którego kolejne pola to wartości 0-1 - wybiera pole z
    najwyższą wartością i wykonuje ruch o tym numerze.
    Kolejność ruchów możesz ustalić dowolną, to bez znaczenia,
    np. że jeśli pole [0] ma najwyższą wartość to ruch to góra, itd.
     */
    void move(Vector vector);

    /*
    Po zakończeniu gry będzie trzeba pobrać jej wynik
     */
    int getScore();

    /*
    *   Game over
    */
    boolean gameOver();

    /*
    Ta funkcja "spłaszcza" pole gry do jednowymiarowej tablicy doubli, też kolejność w jakiej spłaszczysz jest obojętna
     */
    double[] getState();
}
