package backend;

import enums.Fragenkategorie;
import enums.Themenbereich;

import java.util.List;

public class Frage {
    private Themenbereich themenbereich;
    private Fragenkategorie fragenkategorie;
    private String frage;
    private List<Antwort> antworten;
    private boolean geloest = false;
    private int punkte;

    public Frage(Themenbereich themenbereich, String frage, List<Antwort> antworten, int punkte) {
        this.themenbereich = themenbereich;
        this.frage = frage;
        this.antworten = antworten;
        this.fragenkategorie = ermittleKategorie();
        this.punkte = punkte;
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

    private Fragenkategorie ermittleKategorie() {
        return switch (antworten.size()) {
            case 1 -> Fragenkategorie.LUECKENTEXT;
            case 2 -> Fragenkategorie.WAHR_FALSCH;
            case 4 -> Fragenkategorie.MULTIPLE_CHOICE;
            default -> null;
        };
    }
    // um die Methode ermittleKategorie testen zu können
    public Fragenkategorie kategorie() {
        return ermittleKategorie();
    }


    public String getKorrekteAntwort() {
        return antworten.stream()
                .filter(Antwort::isRichtig)
                .map(Antwort::getAntwort)
                .findFirst()
                .orElse("Keine korrekte backend.Antwort hinterlegt.");
    }
}
