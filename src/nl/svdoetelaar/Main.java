package nl.svdoetelaar;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        String[] words = {"good", "bad"};
        NaiveBayes nb = new NaiveBayes(words);
        nb.trainClassifier(new File("traindata.txt"));
        nb.classifyFile(new File("newdata.txt"), new File("classifications.txt"));
    }
}
