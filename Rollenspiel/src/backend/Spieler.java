package backend;

import enums.Level;
import enums.Themenbereich;

import java.util.ArrayList;
import java.util.List;

/**
 * Repräsentiert einen Spieler im Lernspiel. Der Spieler besitzt Name, Level,
 * Fortschritt je Themenbereich, ein Punktekonto sowie eine Liste freigeschalteter Medaillen.
 */
public class Spieler {

    /**
     * Name des Spielers.
     */
    private String name;

    /**
     * Aktuelles Level des Spielers, abhängig vom Gesamtfortschritt.
     */
    private Level level;

    /**
     * Gesamtfortschritt des Spielers basierend auf erzielten Punkten.
     * Wert liegt zwischen 0.0 und 1.0.
     */
    private double gesamtFortschritt;

    /**
     * Punktestand des Spielers.
     */
    private int punktekonto;

    /**
     * Fortschritt für das Thema SQL.
     */
    private double fortschrittSQL;

    /**
     * Fortschritt für das Thema UML.
     */
    private double fortschrittUML;

    /**
     * Fortschritt für das Thema Datenbank.
     */
    private double fortschrittDATENBANK;

    /**
     * Fortschritt für das Thema Pseudocode.
     */
    private double fortschrittPSEUDOCODE;

    /**
     * Fortschritt für das Thema Recht.
     */
    private double fortschrittRECHT;

    /**
     * Fortschritt für das Thema Maschinelles Lernen.
     */
    private double fortschrittMASCHINELLES_LEARNING;

    /**
     * Fortschritt für das Thema Wirtschaft.
     */
    private double fortschrittWIRTSCHAFT;

    /**
     * Liste der Medaillen, die der Spieler freigeschaltet hat.
     */
    private List<String> medallien;

    /**
     * Konstruktor für einen neuen Spieler.
     *
     * @param name Name des Spielers
     */
    public Spieler (String name) {
        setName(name);
        setPunktekonto(0);
        setGesamtFortschritt();
        setLevel();
        setFortschrittThemenbereiche();
        medallien = new ArrayList<>();
    }

    /**
     * Setzt alle themenspezifischen Fortschritte auf 0 zurück.
     */
    public void setFortschrittThemenbereiche () {
        this.fortschrittSQL = 0.0;
        this.fortschrittUML = 0.0;
        this.fortschrittDATENBANK = 0.0;
        this.fortschrittPSEUDOCODE = 0.0;
        this.fortschrittRECHT = 0.0;
        this.fortschrittWIRTSCHAFT = 0.0;
        this.fortschrittMASCHINELLES_LEARNING = 0.0;
    }

    /** @return Fortschritt im Bereich SQL */
    public double getFortschrittSQL() {
        return fortschrittSQL;
    }

    /** @param fortschrittSQL neuer Fortschrittswert SQL */
    public void setFortschrittSQL(double fortschrittSQL) {
        this.fortschrittSQL = fortschrittSQL;
    }

    /** @return Fortschritt im Thema UML */
    public double getFortschrittUML() {
        return fortschrittUML;
    }

    /** @param fortschrittUML neuer Fortschrittswert UML */
    public void setFortschrittUML(double fortschrittUML) {
        this.fortschrittUML = fortschrittUML;
    }

    /** @return Fortschritt im Thema Datenbank */
    public double getFortschrittDATENBANK() {
        return fortschrittDATENBANK;
    }

    /** @param fortschrittDATENBANK neuer Fortschrittswert Datenbank */
    public void setFortschrittDATENBANK(double fortschrittDATENBANK) {
        this.fortschrittDATENBANK = fortschrittDATENBANK;
    }

    /** @return Fortschritt im Thema Pseudocode */
    public double getFortschrittPSEUDOCODE() {
        return fortschrittPSEUDOCODE;
    }

    /** @param fortschrittPSEUDOCODE neuer Fortschrittswert Pseudocode */
    public void setFortschrittPSEUDOCODE(double fortschrittPSEUDOCODE) {
        this.fortschrittPSEUDOCODE = fortschrittPSEUDOCODE;
    }

    /** @return Fortschritt im Thema Recht */
    public double getFortschrittRECHT() {
        return fortschrittRECHT;
    }

    /** @param fortschrittRECHT neuer Fortschrittswert Recht */
    public void setFortschrittRECHT(double fortschrittRECHT) {
        this.fortschrittRECHT = fortschrittRECHT;
    }

    /** @return Name des Spielers */
    public String getName() {
        return name;
    }

    /** @param name neuer Spielername */
    public void setName(String name) {
        this.name = name;
    }

    /** @return aktuelles Level des Spielers */
    public Level getLevel() {
        return level;
    }

    /**
     * Setzt das Level basierend auf dem Gesamtfortschritt.
     */
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

    /** @return Gesamtfortschritt des Spielers */
    public double getGesamtFortschritt() {
        return gesamtFortschritt;
    }

    /**
     * Berechnet den Gesamtfortschritt basierend auf Punkten.
     */
    public void setGesamtFortschritt() {
        // Durchschnitt aller Themen
        this.gesamtFortschritt = (fortschrittSQL + fortschrittUML + fortschrittDATENBANK +
                fortschrittPSEUDOCODE + fortschrittRECHT +
                fortschrittWIRTSCHAFT + fortschrittMASCHINELLES_LEARNING) / 7.0;
    }

    /** @return aktueller Punktestand */
    public int getPunktekonto() {
        return punktekonto;
    }

    /** @param punktekonto neuer Punktestand */
    public void setPunktekonto(int punktekonto) {
        this.punktekonto = punktekonto;
    }

    /**
     * @return die maximal möglichen Punkte im Spiel
     */
    public int getMaxPunkte() {
        return 200;
    }

    /** @return Fortschritt im Bereich Maschinelles Lernen */
    public double getFortschrittMASCHINELLES_LEARNING() {
        return fortschrittMASCHINELLES_LEARNING;
    }

    /** @param fortschrittMASCHINELLES_LEARNING neuer Fortschritt */
    public void setFortschrittMASCHINELLES_LEARNING(double fortschrittMASCHINELLES_LEARNING) {
        this.fortschrittMASCHINELLES_LEARNING = fortschrittMASCHINELLES_LEARNING;
    }

    /** @return Fortschritt im Thema Wirtschaft */
    public double getFortschrittWIRTSCHAFT() {
        return fortschrittWIRTSCHAFT;
    }

    /** @param fortschrittWIRTSCHAFT neuer Fortschritt */
    public void setFortschrittWIRTSCHAFT(double fortschrittWIRTSCHAFT) {
        this.fortschrittWIRTSCHAFT = fortschrittWIRTSCHAFT;
    }

    /**
     * Prüft für alle Bereiche, welche Medaillen freigeschaltet wurden,
     * und fügt deren Bildpfade der Medaillenliste hinzu.
     */
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

    /** @return Liste aller Medaillen */
    public List<String> getMedallien() {
        return medallien;
    }

    /** @param medallien neue Medaillenliste */
    public void setMedallien(List<String> medallien) {
        this.medallien = medallien;
    }

    /**
     * Fügt Punkte für eine beantwortete Frage hinzu, aktualisiert Level,
     * Fortschritte und Medaillen.
     *
     * @param geloesteFrage beantwortete Frage
     */
    public void addPunkte(Frage geloesteFrage) {
        if (geloesteFrage != null) {
            this.punktekonto += geloesteFrage.getPunkte();
        }

        // Fortschritte direkt aus der "Wahrheit" (Repository) ziehen
        this.fortschrittSQL = FragenRepository.berechneFortschrittFuerThema(Themenbereich.SQL);
        this.fortschrittUML = FragenRepository.berechneFortschrittFuerThema(Themenbereich.UML);
        this.fortschrittDATENBANK = FragenRepository.berechneFortschrittFuerThema(Themenbereich.DATENBANK);
        this.fortschrittPSEUDOCODE = FragenRepository.berechneFortschrittFuerThema(Themenbereich.PSEUDOCODE);
        this.fortschrittRECHT = FragenRepository.berechneFortschrittFuerThema(Themenbereich.RECHT);
        this.fortschrittWIRTSCHAFT = FragenRepository.berechneFortschrittFuerThema(Themenbereich.WIRTSCHAFT);
        this.fortschrittMASCHINELLES_LEARNING = FragenRepository.berechneFortschrittFuerThema(Themenbereich.MASCHINELLESLEARNING);

        setGesamtFortschritt();
        setLevel();
        setMedallienArray();
    }

    /**
     * Aktualisiert den Fortschritt eines bestimmten Themenbereichs,
     * basierend auf der Anzahl gelöster Fragen.
     *
     * @param bereich Themenbereich
     */
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