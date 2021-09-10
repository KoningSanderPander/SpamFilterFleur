package nl.svdoetelaar;

public class Main {

    public static void main(String[] args) {
        WordCounter wc = new WordCounter("good");
        System.out.println(wc.getFocusWord());
        wc.addSample("1 good bad bad bad");
        wc.addSample("0 bad good good");
        wc.addSample("0 bad good");
        System.out.println(wc);
    }
}
