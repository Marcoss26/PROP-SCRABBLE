public class Rack {
    public List<Character> getLetters() {
        List<Character> lettersList = new ArrayList<>();
        for (Letter letter : letters) {
            lettersList.add(letter.getSymbol().charAt(0)); // Asumiendo que el símbolo es un carácter
        }
        return lettersList;
    }

    public List<Letter> getLetter() {

        return letters;
    }

    public void removeLetter(Letter letter) {
        letters.remove(letter);
    }

    @Override
    public Rack clone() {
    Rack clonedRack = new Rack();
    for (Letter letter : this.letters) {
        clonedRack.addLetter(new Letter(letter.getSymbol(), letter.getValue())); // Asumiendo que Letter tiene un constructor de copia
    }
    return clonedRack;
}
}
