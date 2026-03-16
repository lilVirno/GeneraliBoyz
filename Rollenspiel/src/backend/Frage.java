package backend;

import enums.Fragenkategorie;
import enums.Themenbereich;

import java.util.List;

public class Frage {
    private int dbID;
    private Themenbereich themenbereich;
    private Fragenkategorie fragenkategorie;
    private String frage;
    private List<Antwort> antworten;
    private boolean geloest = false;
    private int punkte;
    private List<GapField> gapFields;



    public Frage(int dbID, Themenbereich themenbereich, Fragenkategorie fragenkategorie, String frage, List<Antwort> antworten, int punkte) {
        this.dbID = dbID;
        this.themenbereich = themenbereich;
        this.fragenkategorie = fragenkategorie;
        this.frage = frage;
        this.antworten = antworten;
        this.punkte = punkte;
    }

    public Themenbereich getThemenbereich() {
        return themenbereich;
    }

    public Fragenkategorie getFragenkategorie() {
        return fragenkategorie;
    }

    public String getFrage() {
        return frage;
    }

    public void setFrage(String frage) {
        this.frage = frage;
    }

    public List<Antwort> getAntworten() {
        return antworten;
    }

    public boolean isGeloest() {
        return geloest;
    }

    public void setGeloest() {
        this.geloest = true;
    }

    public int getPunkte() {
        return punkte;
    }

    public void setPunkte(int punkte) {
        this.punkte = punkte;
    }




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
