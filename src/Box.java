import java.util.*;

enum Color {
    //Color end string, color reset
    RESET("\033[0m"),

    // Regular Colors. Normal color, no bold, background color etc.
    BLACK("\033[0;30m"),    // BLACK
    RED("\033[0;31m"),      // RED
    GREEN("\033[0;32m"),    // GREEN
    YELLOW("\033[0;33m"),   // YELLOW
    BLUE("\033[0;34m"),     // BLUE
    MAGENTA("\033[0;35m"),  // MAGENTA
    CYAN("\033[0;36m"),     // CYAN
    WHITE("\033[0;37m"),    // WHITE

    private final String code;

    Color(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
And now we will make a small example:
class RunApp {
    public static void main(String[] args) {

        System.out.print(Color.BLACK_BOLD);
        System.out.println("Black_Bold");
        System.out.print(Color.RESET);

        System.out.print(Color.YELLOW);
        System.out.print(Color.BLUE_BACKGROUND);
        System.out.println("YELLOW & BLUE");
        System.out.print(Color.RESET);

        System.out.print(Color.YELLOW);
        System.out.println("YELLOW");
        System.out.print(Color.RESET);
    }
}

public class Box
{
    protected int x;
    protected int y;
    private String symbol;
    private int value;
    public Box(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.symbol = null;
        this.value = 0;
    }

    public void setLetter(String lletra, int valor)
    {
        this.symbol = lletra;
        this.value = valor;
    }
    public String getSymbol()
    {
        return symbol;
    }
    public int getValue()
    {
        return value;
    }

    @Override
    public String toString() {
        if (getSymbol() != null) {
            return Color.GREEN + "(" + getSymbol() + ", " + getValue() + ")" + Color.RESET;
        }
        return Color.GREEN + "()" + Color.RESET;
    }

    public void applyMultiplicator();

    public class DoubleLetter extends Box
    {
        private int multiplicator = 2;

        @Override
        public void applyMultiplicator()
        {
            // Implementación del multiplicador de letra doble
        }

        @Override
        public String toString() {
            if (getSymbol() != null) {
                return Color.CYAN + "(" + getSymbol() + ", " + getValue() + ")" + Color.RESET;
            }
            return Color.CYAN + "()" + Color.RESET;
        }
    }
    public class TripleLetter extends Box
    {
        private int multiplicator = 3;

        @Override
        public void applyMultiplicator()
        {
            // Implementación del multiplicador de letra triple
        }

        @Override
        public String toString() {
            if (getSymbol() != null) {
                return Color.BLUE + "(" + getSymbol() + ", " + getValue() + ")" + Color.RESET;
            }
            return Color.BLUE + "()" + Color.RESET;
        }
    }
    public class DoubleWord extends Box
    {
        private int multiplicator = 2;

        @Override
        public void applyMultiplicator()
        {
            // Implementación del multiplicador de palabra doble
        }

        @Override
        public String toString() {
            if (getSymbol() != null) {
                return Color.MAGENTA + "(" + getSymbol() + ", " + getValue() + ")" + Color.RESET;
            }
            return Color.MAGENTA + "()" + Color.RESET;
        }
    }
    public class TripleWord extends Box
    {
        private int multiplicator = 3;

        @Override
        public void applyMultiplicator()
        {
            // Implementación del multiplicador de palabra triple
        }

        @Override
        public String toString() {
            if (getSymbol() != null) {
                return Color.RED + "(" + getSymbol() + ", " + getValue() + ")" + Color.RESET;
            }
            return Color.RED + "()" + Color.RESET;
        }
    }
}