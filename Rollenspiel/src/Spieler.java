public class Spieler {

    private String name;

    private Level level;

    private double GesamtFortschritt;


    public Spieler (String name) {
        setName(name);
        setLevel(Level.ANFÄNGER);
        setGesamtFortschritt(0.0);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public double getGesamtFortschritt() {
        return GesamtFortschritt;
    }

    public void setGesamtFortschritt(double gesamtFortschritt) {
        GesamtFortschritt = gesamtFortschritt;
    }
}
