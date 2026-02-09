public class Spieler {

    private String name;

    private Level level;

    private double gesamtFortschritt;


    public Spieler (String name) {
        setName(name);
        setGesamtFortschritt(0.0);
        setLevel();
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

    public void setLevel() {
        if (gesamtFortschritt <= 20) {
            this.level = Level.ANFÄNGER;
        } else if (gesamtFortschritt <= 40) {
            this.level = Level.BRONZE;
        } else if (gesamtFortschritt <= 60) {
            this.level = Level.SILBER;
        } else if (gesamtFortschritt <= 80) {
            this.level = Level.GOLD;
        } else {
            this.level = Level.MASTER;
        }
    }

    public double getGesamtFortschritt() {
        return gesamtFortschritt;
    }

    public void setGesamtFortschritt(double gesamtFortschritt) {
        this.gesamtFortschritt = gesamtFortschritt;
    }
}
