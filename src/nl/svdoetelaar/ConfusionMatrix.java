package nl.svdoetelaar;

public class ConfusionMatrix {
    private int trueNegatives = 0;
    private int truePositives = 0;
    private int falseNegatives = 0;
    private int falsePositives = 0;

    public ConfusionMatrix() {
    }

    public void addTrueNegative() {
        this.trueNegatives++;
    }

    public void addTruePositive() {
        this.truePositives++;
    }

    public void addFalseNegative() {
        this.falseNegatives++;
    }

    public void addFalsePositive() {
        this.falsePositives++;
    }

    public int getTrueNegatives() {
        return trueNegatives;
    }

    public int getTruePositives() {
        return truePositives;
    }

    public int getFalseNegatives() {
        return falseNegatives;
    }

    public int getFalsePositives() {
        return falsePositives;
    }
}
