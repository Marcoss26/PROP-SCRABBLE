public class Rack {
    public List<Character> getLetters() {
        List<Character> lettersList = new ArrayList<>();
        for (Letter letter : letters) {
            lettersList.add(letter.getSymbol().charAt(0)); // Asumiendo que el símbolo es un carácter
        }
        return lettersList;
    }
}
