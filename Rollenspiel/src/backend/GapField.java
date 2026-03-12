package backend;

import java.util.List;

public class GapField {
    public String Frage;
    public String InputType;
    public List<String> gapAnswer;
    public List<String> correctGapAnswer;


    public String getFrage() {
        return Frage;
    }

    public void setFrage(String frage) {
        Frage = frage;
    }

    public String getInputType() {
        return InputType;
    }

    public void setInputType(String inputType) {
        InputType = inputType;
    }

    public List<String> getCorrectGapAnswer() {
        return correctGapAnswer;
    }

    public void setCorrectGapAnswer(List<String> correctGapAnswer) {
        this.correctGapAnswer = correctGapAnswer;
    }

    public List<String> getGapAnswer() {
        return gapAnswer;
    }

    public void setGapAnswer(List<String> gapAnswer) {
        this.gapAnswer = gapAnswer;
    }

    public GapField(String frage, String inputType,List<String> gapAnswer, List<String> correctGapAnswer) {
        setFrage(frage);
        setInputType(inputType);
        setGapAnswer(gapAnswer);
        setCorrectGapAnswer(correctGapAnswer);
    }
    public GapField(){}
}
