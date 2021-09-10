package nl.svdoetelaar;

import java.util.regex.Pattern;

public class WordCounter extends DocumentDecoder {
    private int totalWordsNotSpam = 0;
    private int totalWordsSpam = 0;
    private int focusWordsNotSpam = 0;
    private int focusWordsInSpam = 0;

    private final String FOCUS_WORD;

    public WordCounter(String focusWord) {
        this.FOCUS_WORD = focusWord;
    }

    public void addSample(String document) {
        String[] wordsInDocument = splitWordsInDocument(document);

        if (isDocumentSpam(document)) {
            // no spam
            totalWordsSpam += wordsInDocument.length - DOCUMENT_PREFIX_OFFSET;
            focusWordsInSpam += getFocusWordsInDocument(wordsInDocument);
        } else {
            // spam
            totalWordsNotSpam += wordsInDocument.length - DOCUMENT_PREFIX_OFFSET;
            focusWordsNotSpam += getFocusWordsInDocument(wordsInDocument);
        }

    }

    private int getFocusWordsInDocument(String[] wordsInDocument) {
        Pattern pattern = Pattern.compile(FOCUS_WORD, Pattern.CASE_INSENSITIVE);
        int numberOfFocusWords = 0;

        for (String word : wordsInDocument) {
            if (pattern.matcher(word).find()) {
                numberOfFocusWords++;
            }
        }

        return numberOfFocusWords;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
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
        return FOCUS_WORD;
    }

    @Override
    public String toString() {
        return String.format("Focus Word: %s\n" +
                "\tTotal Words Not Spam: %d\n" +
                "\tTotal Words Spam: %d\n" +
                "\tFocus Words Not Spam: %d\n" +
                "\tFocus Words In Spam: %d\n" +
                "\tConditional Spam: %.2f\n" +
                "\tConditional No Spam: %.2f\n", FOCUS_WORD, totalWordsNotSpam, totalWordsSpam, focusWordsNotSpam, focusWordsInSpam, getConditionalSpam(), getConditionalNoSpam());

    }
}
