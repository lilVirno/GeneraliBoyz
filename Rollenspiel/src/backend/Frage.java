package backend;

import enums.Fragenkategorie;
import enums.Themenbereich;

import java.util.List;

/**
 * Repräsentiert eine Frage mit Kategorie, Themenbereich, möglichen Antworten,
 * Punktwert und einem Status, ob die Frage bereits gelöst wurde.
 */
public class Frage {
    private int dbID;

    /**
     * Der Themenbereich, dem die Frage zugeordnet ist.
     */
    private Themenbereich themenbereich;

    /**
     * Die Kategorie der Frage (z. B. Multiple Choice, Wissen, etc.).
     */
    private Fragenkategorie fragenkategorie;

    /**
     * Der eigentliche Fragetext.
     */
    private String frage;

    /**
     * Liste der möglichen Antworten zur Frage.
     */
    private List<Antwort> antworten;

    /**
     * Gibt an, ob die Frage bereits gelöst wurde.
     */
    private boolean geloest = false;

    /**
     * Punktzahl, die für das richtige Beantworten vergeben wird.
     */
    private int punkte;



    /**
     * Konstruktor zum Erstellen einer vollständigen Frage.
     *
     * @param dbID            ID der Frage in der Datenbank
     * @param themenbereich   Themenbereich zu der Frage
     * @param fragenkategorie Kategorie der Frage
     * @param frage           Text der Frage
     * @param antworten       Liste der Antwortmöglichkeiten
     * @param punkte          Punktewert für korrekte Lösung
     */

    public Frage(int dbID, Themenbereich themenbereich, Fragenkategorie fragenkategorie,
                 String frage, List<Antwort> antworten, int punkte) {
        this.dbID = dbID;
        this.themenbereich = themenbereich;
        this.fragenkategorie = fragenkategorie;
        this.frage = frage;
        this.antworten = antworten;
        this.punkte = punkte;
    }
    public Frage(){ }

    /**
     * @return die eindeutige Datenbank-ID der Frage
     */
    public int getDbID() {
        return dbID;
    }

    /**
     * @return der Themenbereich der Frage
     */
    public Themenbereich getThemenbereich() {
        return themenbereich;
    }

    /**
     * Setzt den Themenbereich der Frage.
     *
     * @param themenbereich der neue Themenbereich
     */
    public void setThemenbereich(Themenbereich themenbereich) {
        this.themenbereich = themenbereich;
    }

    /**
     * @return die Kategorie der Frage
     */
    public Fragenkategorie getFragenkategorie() {
        return fragenkategorie;
    }

    /**
     * Setzt die Kategorie der Frage.
     *
     * @param fragenkategorie die neue Fragenkategorie
     */
    public void setFragenkategorie(Fragenkategorie fragenkategorie) {
        this.fragenkategorie = fragenkategorie;
    }

    /**
     * @return der Text der Frage
     */
    public String getFrage() {
        return frage;
    }

    /**
     * Setzt den Fragetext.
     *
     * @param frage neuer Fragetext
     */
    public void setFrage(String frage) {
        this.frage = frage;
    }

    /**
     * @return die Liste aller Antwortmöglichkeiten
     */
    public List<Antwort> getAntworten() {
        return antworten;
    }

    /**
     * Setzt die Liste der Antwortmöglichkeiten.
     *
     * @param antworten neue Antwortliste
     */
    public void setAntworten(List<Antwort> antworten) {
        this.antworten = antworten;
    }

    /**
     * @return true, wenn die Frage bereits korrekt gelöst wurde; sonst false
     */
    public boolean isGeloest() {
        return geloest;
    }

    /**
     * Markiert die Frage als gelöst.
     */
    public void setGeloest() {
        this.geloest = true;
    }

    /**
     * @return der Punktwert der Frage
     */
    public int getPunkte() {
        return punkte;
    }

    /**
     * Setzt den Punktwert der Frage.
     *
     * @param punkte neuer Punktwert
     */
    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }

    /**
     * Liefert den Text der korrekten Antwort zurück.
     *
     * @return die richtige Antwort, oder ein Hinweistext falls keine gefunden wurde
     */
//    public String getKorrekteAntwort() {}

    public List<String> getKorrekteAntworten() {
        return antworten.stream()
                .filter(Antwort::isRichtig)
                .map(Antwort::getAntwort)
                .toList();
    }

    // Hilfsmethode: zähle Vorkommen eines Substrings
    public static int countOccurrences(String text, String token) {
        int count = 0, idx = 0;
        while ((idx = text.indexOf(token, idx)) != -1) {
            count++;
            idx += token.length();
        }
        return count;
    }
}
