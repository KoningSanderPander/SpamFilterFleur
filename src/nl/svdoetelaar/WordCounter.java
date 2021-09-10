package nl.svdoetelaar;

import java.util.regex.Pattern;

public class WordCounter {
    private int totalWordsNotSpam = 0;
    private int totalWordsSpam = 0;
    private int focusWordsNotSpam = 0;
    private int focusWordsInSpam = 0;

    private String focusWord;

    public WordCounter(String focusWord) {
        this.focusWord = focusWord;
    }

    public void addSample(String document) {
        String[] wordsInDocument = document.split(" ");

        System.out.println(document.charAt(0) == '0');
        if (document.charAt(0) == '0') {
            // no spam
            totalWordsNotSpam += wordsInDocument.length - 1;
            focusWordsNotSpam += getFocusWordsInDocument(wordsInDocument);
        } else {
            // spam
            totalWordsSpam += wordsInDocument.length - 1;
            focusWordsInSpam += getFocusWordsInDocument(wordsInDocument);
        }

    }

    private int getFocusWordsInDocument(String[] wordsInDocument) {
        Pattern pattern = Pattern.compile(focusWord, Pattern.CASE_INSENSITIVE);
        int numberOfFocusWords = 0;

        for (String word : wordsInDocument) {
            if (pattern.matcher(word).find()) {
                numberOfFocusWords++;
            }
        }

        return numberOfFocusWords;
    }

    public boolean isCounterTrained() {
        return focusWordsInSpam + focusWordsNotSpam > 0 && totalWordsNotSpam > 0 && totalWordsSpam > 0;
    }

    public double getConditionalNoSpam() {
        if (!isCounterTrained()) {
            throw new IllegalStateException();
        }
        return (double) focusWordsNotSpam / totalWordsNotSpam;
    }

    public double getConditionalSpam() {
        if (!isCounterTrained()) {
            throw new IllegalStateException();
        }
        return (double) focusWordsInSpam / totalWordsSpam;
    }


    public String getFocusWord() {
        return focusWord;
    }

    @Override
    public String toString() {
        return String.format("focusWord: %s\n" +
                "\ttotalWordsNotSpam: %d\n" +
                "\ttotalWordsSpam: %d\n" +
                "\tfocusWordsNotSpam: %d\n" +
                "\tfocusWordsInSpam: %d\n" +
                "\tconditionalSpam: %.2f\n" +
                "\tconditionalNoSpam: %.2f\n", focusWord, totalWordsNotSpam, totalWordsSpam, focusWordsNotSpam, focusWordsInSpam, getConditionalSpam(), getConditionalNoSpam());

    }
}
