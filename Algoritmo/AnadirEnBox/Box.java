public class Box {
    
    public Box getLeftNeighbor() {
        if (x > 0) {
            return board[x - 1][y]; // Asumiendo que `board` es accesible aquí
        }
        return null;
    }

    public Box getTopNeighbor() {
        if (y > 0) {
            return board[x][y - 1]; // Asumiendo que `board` es accesible aquí
        }
        return null;
    }
}
