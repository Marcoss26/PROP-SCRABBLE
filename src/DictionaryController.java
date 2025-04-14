import java.io.*;
import java.util.*;

public class DictionaryController {
    private Dictionary dictionary;

    public DictionaryController(String language) {
        this.dictionary = new Dictionary(language);
    }
}