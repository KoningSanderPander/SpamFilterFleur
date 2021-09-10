package nl.svdoetelaar;

import java.util.HashMap;
import java.util.Map;

public class NaiveBayes extends DocumentDecoder {
    private Map<String, WordCounter> wordCounterMap = new HashMap<>();

    private int documentsNoSpam = 0;
    private int documentsSpam = 0;
    private int totalDocuments = 0;

    public NaiveBayes(String[] focusWords) {
        for (String focusWord : focusWords) {
            wordCounterMap.putIfAbsent(focusWord, new WordCounter(focusWord));
        }
    }

    public void addSample(String document) {
        if (isDocumentSpam(document)) {
            documentsSpam++;
        } else {
            documentsNoSpam++;
        }

        totalDocuments++;

        wordCounterMap.values().forEach(wordCounter ->
                wordCounter.addSample(document)
        );

    }

    public boolean classify(String unclassifiedDocument) {
        double spamScore = (double) documentsSpam / totalDocuments;
        double noSpamScore = (double) documentsNoSpam / totalDocuments;

        String[] unclassifiedWords = splitWordsInDocument(unclassifiedDocument);

        for (String unclassifiedWord : unclassifiedWords) {
            WordCounter wc = wordCounterMap.getOrDefault(unclassifiedWord, null);

            if (wc != null) {
                spamScore *= wc.getConditionalSpam();
                noSpamScore *= wc.getConditionalNoSpam();
            }
        }

        return noSpamScore < spamScore;

    }
}
