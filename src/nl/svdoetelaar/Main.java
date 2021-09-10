package nl.svdoetelaar;

public class Main {

    public static void main(String[] args) {
        String[] words = {"good", "bad"};
        NaiveBayes nb = new NaiveBayes(words);
        nb.addSample("1 good bad bad bad casino");
        nb.addSample("0 bad good good pizza");
        nb.addSample("0 bad good tapas");
        System.out.println(nb.classify("good"));
        System.out.println(nb.classify("bad"));
        System.out.println(nb.classify("good bad bad"));
        System.out.println(nb.classify("pizza"));
        System.out.println(nb.classify("casino"));

    }
}
