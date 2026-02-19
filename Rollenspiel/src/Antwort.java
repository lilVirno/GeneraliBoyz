public class Antwort {
    private boolean richtig;
    private String antwort;

    public Antwort(boolean richtig, String antwort) {
        this.richtig = richtig;
        this.antwort = antwort;
    }

    public boolean isRichtig() {
        return richtig;
    }

    public void setRichtig(boolean richtig) {
        this.richtig = richtig;
    }

    public String getAntwort() {
        return antwort;
    }

    public void setAntwort(String antwort) {
        this.antwort = antwort;
    }
}
