public class Spieler {

    private String name;

    private Level level;

    private double gesamtFortschritt;

    private int punktekonto;


    public Spieler (String name) {
        setName(name);
        setPunktekonto(0);
        setGesamtFortschritt();
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
        if (gesamtFortschritt <= 0.20) {
            this.level = Level.ANFÄNGER;
        } else if (gesamtFortschritt <= 0.40) {
            this.level = Level.BRONZE;
        } else if (gesamtFortschritt <= 0.60) {
            this.level = Level.SILBER;
        } else if (gesamtFortschritt <= 0.80) {
            this.level = Level.GOLD;
        } else {
            this.level = Level.MASTER;
        }
    }

    public double getGesamtFortschritt() {
        return gesamtFortschritt;
    }

    public void setGesamtFortschritt() {
        this.gesamtFortschritt = (double) getPunktekonto() / getMaxPunkte();
    }

    public int getPunktekonto() {
        return punktekonto;
    }

    public void setPunktekonto(int punktekonto) {
        this.punktekonto = punktekonto;
    }

    public int getMaxPunkte() {
        return 200;
    }
}
