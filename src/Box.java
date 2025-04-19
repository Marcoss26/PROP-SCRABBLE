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
    WHITE("\033[0;37m");    // WHITE

    private final String code;

    Color(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}

class RunApp {
    public static void main(String[] args) {
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


    public static class DoubleLetter extends Box
    {
        public DoubleLetter(int x, int y)
        {
            super(x, y);
        }

        @Override
        public String toString() {
            if (getSymbol() != null) {
                return Color.CYAN + "(" + getSymbol() + ", " + getValue() + ")" + Color.RESET;
            }
            return Color.CYAN + "()" + Color.RESET;
        }
    }
    public static class TripleLetter extends Box
    {
        public TripleLetter(int x, int y)
        {
            super(x, y);
        }

        @Override
        public String toString() {
            if (getSymbol() != null) {
                return Color.BLUE + "(" + getSymbol() + ", " + getValue() + ")" + Color.RESET;
            }
            return Color.BLUE + "()" + Color.RESET;
        }
    }
    public static class DoubleWord extends Box
    {
        public DoubleWord(int x, int y)
        {
            super(x, y);
        }

        @Override
        public String toString() {
            if (getSymbol() != null) {
                return Color.MAGENTA + "(" + getSymbol() + ", " + getValue() + ")" + Color.RESET;
            }
            return Color.MAGENTA + "()" + Color.RESET;
        }
    }
    public static class TripleWord extends Box
    {
        public TripleWord(int x, int y)
        {
            super(x, y);
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