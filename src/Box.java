import java.util.*;

public class Box
{
    private int x;
    private int y;
    private boolean isOccupied; //pendiente de borrar, con idFicha ya
                                //sabemos si está ocupada o no
    private String idFicha;

    public void applyMultiplicator();

    public class DoubleLetter extends Box
    {
        private int multiplicator = 2;

        @Override
        public void applyMultiplicator()
        {
            // Implementación del multiplicador de letra doble
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
    }
    public class DoubleWord extends Box
    {
        private int multiplicator = 2;

        @Override
        public void applyMultiplicator()
        {
            // Implementación del multiplicador de palabra doble
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
    }
}