package backend;

import enums.Level;
import enums.Themenbereich;

import java.util.ArrayList;
import java.util.List;

public class Spieler {

    private String name;

    private Level level;

    private double gesamtFortschritt;

    private int punktekonto;

    private double fortschrittSQL;

    private double fortschrittUML;

    private double fortschrittDATENBANK;

    private double fortschrittPSEUDOCODE;

    private double fortschrittRECHT;

    private double fortschrittMASCHINELLES_LEARNING;

    private double fortschrittWIRTSCHAFT;

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
        this.fortschrittSQL = 0.0;
        this.fortschrittUML = 0.0;
        this.fortschrittDATENBANK = 0.0;
        this.fortschrittPSEUDOCODE = 0.0;
        this.fortschrittRECHT = 0.0;
        this.fortschrittWIRTSCHAFT = 0.0;
        this.fortschrittMASCHINELLES_LEARNING = 0.0;
    }

    public double getFortschrittSQL() {
        return fortschrittSQL;
    }

    public void setFortschrittSQL(double fortschrittSQL) {
        this.fortschrittSQL = fortschrittSQL;
    }

    public double getFortschrittUML() {
        return fortschrittUML;
    }

    public void setFortschrittUML(double fortschrittUML) {
        this.fortschrittUML = fortschrittUML;
    }

    public double getFortschrittDATENBANK() {
        return fortschrittDATENBANK;
    }

    public void setFortschrittDATENBANK(double fortschrittDATENBANK) {
        this.fortschrittDATENBANK = fortschrittDATENBANK;
    }

    public double getFortschrittPSEUDOCODE() {
        return fortschrittPSEUDOCODE;
    }

    public void setFortschrittPSEUDOCODE(double fortschrittPSEUDOCODE) {
        this.fortschrittPSEUDOCODE = fortschrittPSEUDOCODE;
    }

    public double getFortschrittRECHT() {
        return fortschrittRECHT;
    }

    public void setFortschrittRECHT(double fortschrittRECHT) {
        this.fortschrittRECHT = fortschrittRECHT;
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

    public double getFortschrittMASCHINELLES_LEARNING() {
        return fortschrittMASCHINELLES_LEARNING;
    }

    public void setFortschrittMASCHINELLES_LEARNING(double fortschrittMASCHINELLES_LEARNING) {
        this.fortschrittMASCHINELLES_LEARNING = fortschrittMASCHINELLES_LEARNING;
    }

    public double getFortschrittWIRTSCHAFT() {
        return fortschrittWIRTSCHAFT;
    }

    public void setFortschrittWIRTSCHAFT(double fortschrittWIRTSCHAFT) {
        this.fortschrittWIRTSCHAFT = fortschrittWIRTSCHAFT;
    }

    public void setMedallienArray() {
        if (fortschrittSQL == 1) {
            medallien.add("Rollenspiel/src/resources/Medaille_SQL_T.png");
        }
        if (fortschrittUML == 1) {
            medallien.add("Rollenspiel/src/resources/Medaille_UML_T.png");
        }
        if (fortschrittDATENBANK == 1) {
            medallien.add("Rollenspiel/src/resources/Medaille_DATENBANKENMODELLIERUNG_T.png");
        }
        if (fortschrittPSEUDOCODE == 1) {
            medallien.add("Rollenspiel/src/resources/Medaille_PROGRAMMIERUNG_PSEUDOCODE_T.png");
        }
        if (fortschrittRECHT == 1){
            medallien.add("Rollenspiel/src/resources/Medaille_RECHT_T.png");
        }
        if (fortschrittMASCHINELLES_LEARNING == 1){
            medallien.add("Rollenspiel/src/resources/Medaille_MASCHINELLES_LERNEN_T.png");
        }
        if (fortschrittWIRTSCHAFT == 1){
            medallien.add("Rollenspiel/src/resources/Medaille_WIRTSCHAFT_T.png");
        }
        if (gesamtFortschritt == 1){
            medallien.add("Rollenspiel/src/resources/EndbossMedallie.jpg");
        }
    }

    public List<String> getMedallien() {
        return medallien;
    }

    public void setMedallien(List<String> medallien) {
        this.medallien = medallien;
    }

    public void addPunkte(Frage frage) {
        this.punktekonto += frage.getPunkte();

        // Gesamtfortschritt und Level neu berechnen
        setGesamtFortschritt();
        setLevel();

        // Themen-spezifischen Fortschritt berechnen
        aktualisiereThemenFortschritt(frage.getThemenbereich());

        // Prüfen, ob neue Medaillen dazugekommen sind
        setMedallienArray();
    }

    private void aktualisiereThemenFortschritt(Themenbereich bereich) {
        // Berechnung: (Beantwortete Fragen in diesem Bereich) / (Gesamtanzahl Fragen in diesem Bereich)
        double fortschritt = FragenRepository.berechneFortschrittFuerThema(bereich);

        switch (bereich) {
            case SQL -> setFortschrittSQL(fortschritt);
            case UML -> setFortschrittUML(fortschritt);
            case DATENBANK -> setFortschrittDATENBANK(fortschritt);
            case PSEUDOCODE -> setFortschrittPSEUDOCODE(fortschritt);
            case RECHT -> setFortschrittRECHT(fortschritt);
            case WIRTSCHAFT -> setFortschrittWIRTSCHAFT(fortschritt);
            case MASCHINELLESLEARNING -> setFortschrittMASCHINELLES_LEARNING(fortschritt);
        }
    }
}
