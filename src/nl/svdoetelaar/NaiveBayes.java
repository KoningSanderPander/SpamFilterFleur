package nl.svdoetelaar;

import java.io.*;
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
        String[] unclassifiedWords = splitWordsInDocument(unclassifiedDocument);

        double spamScore = (double) documentsSpam / totalDocuments;
        double noSpamScore = (double) documentsNoSpam / totalDocuments;

        for (String unclassifiedWord : unclassifiedWords) {
            WordCounter wc = wordCounterMap.getOrDefault(unclassifiedWord, null);

            if (wc != null) {
                spamScore *= wc.getConditionalSpam();
                noSpamScore *= wc.getConditionalNoSpam();
            }
        }

        return noSpamScore < spamScore;

    }

    public void trainClassifier(File trainingFile) throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(trainingFile));
            bufferedReader.lines().forEach(this::addSample);
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public void classifyFile(File input, File output) throws IOException {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(input));
            FileWriter fileWriter = new FileWriter(output);
            bufferedReader.lines().map(this::classify).forEach(aBoolean -> {
                try {
                    fileWriter.write(aBoolean ? "1\n" : "0\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bufferedReader.close();
            fileWriter.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public ConfusionMatrix computeAccuracy(File testdata) throws IOException {
        ConfusionMatrix confusionMatrix = new ConfusionMatrix();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(testdata));
            bufferedReader.lines().forEach(s -> {
                if (isDocumentSpam(s)) {
                    if (classify(s)) {
                        confusionMatrix.addTruePositive();
                    } else {
                        confusionMatrix.addFalseNegative();
                    }
                } else {
                    if (classify(s)) {
                        confusionMatrix.addFalsePositive();
                    } else {
                        confusionMatrix.addTrueNegative();
                    }
                }
            });
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (IOException e) {
            throw new IOException(e);
        }

        return confusionMatrix;
    }


}
