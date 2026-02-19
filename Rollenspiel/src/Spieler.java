import enums.Level;

import java.util.ArrayList;
import java.util.List;

public class Spieler {

    private String name;

    private Level level;

    private double gesamtFortschritt;

    private int punktekonto;

    private double fortschrittThemenbereich1;

    private double fortschrittThemenbereich2;

    private double fortschrittThemenbereich3;

    private double fortschrittThemenbereich4;

    private double fortschrittThemenbereich5;

    private List<String> medallien;


    public Spieler (String name) {
        setName(name);
        setPunktekonto(0);
        setGesamtFortschritt();
        setLevel();
        setFortschrittThemenbereiche();
        medallien = new ArrayList<>();
    }

    public void setFortschrittThemenbereiche () {
        this.fortschrittThemenbereich1 = 0.0;
        this.fortschrittThemenbereich2 = 0.0;
        this.fortschrittThemenbereich3 = 0.0;
        this.fortschrittThemenbereich4 = 0.0;
        this.fortschrittThemenbereich5 = 0.0;
    }

    public double getFortschrittThemenbereich1() {
        return fortschrittThemenbereich1;
    }

    public void setFortschrittThemenbereich1(double fortschrittThemenbereich1) {
        this.fortschrittThemenbereich1 = fortschrittThemenbereich1;
    }

    public double getFortschrittThemenbereich2() {
        return fortschrittThemenbereich2;
    }

    public void setFortschrittThemenbereich2(double fortschrittThemenbereich2) {
        this.fortschrittThemenbereich2 = fortschrittThemenbereich2;
    }

    public double getFortschrittThemenbereich3() {
        return fortschrittThemenbereich3;
    }

    public void setFortschrittThemenbereich3(double fortschrittThemenbereich3) {
        this.fortschrittThemenbereich3 = fortschrittThemenbereich3;
    }

    public double getFortschrittThemenbereich4() {
        return fortschrittThemenbereich4;
    }

    public void setFortschrittThemenbereich4(double fortschrittThemenbereich4) {
        this.fortschrittThemenbereich4 = fortschrittThemenbereich4;
    }

    public double getFortschrittThemenbereich5() {
        return fortschrittThemenbereich5;
    }

    public void setFortschrittThemenbereich5(double fortschrittThemenbereich5) {
        this.fortschrittThemenbereich5 = fortschrittThemenbereich5;
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

    public void setMedallienArray() {
        if (fortschrittThemenbereich1 == 1) {
            medallien.add("U:\\Documents\\workspace\\3.Jahr\\java\\Projekt\\Rollenspiel\\src\\resources\\BlauMedallie.jpg");
        }
        if (fortschrittThemenbereich2 == 1) {
            medallien.add("U:\\Documents\\workspace\\3.Jahr\\java\\Projekt\\Rollenspiel\\src\\resources\\GrünMedallie.jpg");
        }
        if (fortschrittThemenbereich3 == 1) {
            medallien.add("U:\\Documents\\workspace\\3.Jahr\\java\\Projekt\\Rollenspiel\\src\\resources\\LilaMedallie.jpg");
        }
        if (fortschrittThemenbereich4 == 1) {
            medallien.add("U:\\Documents\\workspace\\3.Jahr\\java\\Projekt\\Rollenspiel\\src\\resources\\olivgrünMedallie.jpg");
        }
        if (fortschrittThemenbereich5 == 1){
            medallien.add("U:\\Documents\\workspace\\3.Jahr\\java\\Projekt\\Rollenspiel\\src\\resources\\RotMedallie.jpg");
        }
        if (gesamtFortschritt == 1){
            medallien.add("U:\\Documents\\workspace\\3.Jahr\\java\\Projekt\\Rollenspiel\\src\\resources\\EndbossMedallie.jpg");
        }
    }
}
