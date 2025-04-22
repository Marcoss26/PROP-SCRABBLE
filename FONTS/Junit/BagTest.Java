package Junit;
import Bag.java;
import BagController.java;
import Letter.java;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;

public class BagTest {
    private Bag bag;
    private List<Letter> iniletters;

    @Before
    public void setAttributes(){ // Voy a crear una bolsa con unos 20 elementos dentro
        letters = new ArrayList<>();
        letters.add(new Letter("A", 1));
        letters.add(new Letter("A", 1));
        letters.add(new Letter("A", 1));
        letters.add(new Letter("A", 1));
        letters.add(new Letter("B", 3));
        letters.add(new Letter("C", 3));
        letters.add(new Letter("C", 3));
        letters.add(new Letter("D", 2));
        letters.add(new Letter("D", 2));
        letters.add(new Letter("D", 2));
        letters.add(new Letter("D", 2));
        letters.add(new Letter("E", 1));
        letters.add(new Letter("F", 4));
        letters.add(new Letter("G", 2));
        letters.add(new Letter("G", 2));
        letters.add(new Letter("RR", 8));
        letters.add(new Letter("X", 8));
        letters.add(new Letter("LL", 8));
        letters.add(new Letter("Ñ", 8));
        letters.add(new Letter("CH", 5));



        bag = new Bag(letters);
    }

    @After
    public void clearAttributes(){ //Se resetean los atributos para no afectarse entre test y test
        letters = null;
        bag = null;
    }

    @Test
    //Comprobamos que la bolsa se ha creado correctamente y tiene el conjunto inicial de fichas que le hemos pasado
    public void testBagInitialization() {
        assertEquals(20, bag.getNumLetters()); 
        assertEquals(inilLetters, bag.getLetters());
    }


}