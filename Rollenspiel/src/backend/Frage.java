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

    public Frage(int dbID, Themenbereich themenbereich, Fragenkategorie fragenkategorie, String frage, List<Antwort> antworten, int punkte) {
        this.dbID = dbID;
        this.themenbereich = themenbereich;
        this.fragenkategorie = fragenkategorie;
        this.frage = frage;
        this.antworten = antworten;
        this.punkte = punkte;
    }

    public int getDbID() {
        return dbID;
    }

    public Themenbereich getThemenbereich() {
        return themenbereich;
    }

    public void setThemenbereich(Themenbereich themenbereich) {
        this.themenbereich = themenbereich;
    }

    public Fragenkategorie getFragenkategorie() {
        return fragenkategorie;
    }

    public void setFragenkategorie(Fragenkategorie fragenkategorie) {
        this.fragenkategorie = fragenkategorie;
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

    public void setAntworten(List<Antwort> antworten) {
        this.antworten = antworten;
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


    public String getKorrekteAntwort() {
        return antworten.stream()
                .filter(Antwort::isRichtig)
                .map(Antwort::getAntwort)
                .findFirst()
                .orElse("Keine korrekte backend.Antwort hinterlegt.");
    }
}
