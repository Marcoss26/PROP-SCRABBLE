import javax.swing.Box;

public class Board {

    public Set<Box> getAnchorSquares() {
        Set<Box> anchorSquares = new HashSet<>();
    
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (hasLetter(i, j)) {
                    // Revisar las casillas adyacentes
                    addAnchorSquareIfValid(anchorSquares, i - 1, j); // Arriba
                    addAnchorSquareIfValid(anchorSquares, i + 1, j); // Abajo
                    addAnchorSquareIfValid(anchorSquares, i, j - 1); // Izquierda
                    addAnchorSquareIfValid(anchorSquares, i, j + 1); // Derecha
                }
            }
        }
    
        return anchorSquares;
    }
    
    private void addAnchorSquareIfValid(Set<Box> anchorSquares, int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size && !hasLetter(x, y)) {
            anchorSquares.add(board[x][y]);
        }
    }

    public boolean hasLetter(int x, int y) {
        if (x >= 0 && x < size && y >= 0 && y < size) {
            return this.board[x][y].getSymbol() != null;
        } else {
            throw new IllegalArgumentException("Coordenadas fuera de los límites del tablero.");
        }
    }

    public char getLetter(int x, int y) {
        return board[x][y].getSymbol().charAt(0); // Asumiendo que el símbolo es un carácter
    }

    public Box getLeftNeighbor(Box anchor) {
        if (anchor.getX() > 0) {
            return board[anchor.getX() - 1][anchor.getY()]; // Asumiendo que `board` es accesible aquí
        }
        return null;
    }

    public Box getTopNeighbor(Box anchor) {
        if (anchor.getY() > 0) {
            return board[anchor.getX()][anchor.getY() - 1]; // Asumiendo que `board` es accesible aquí
        }
        return null;
    }
}
