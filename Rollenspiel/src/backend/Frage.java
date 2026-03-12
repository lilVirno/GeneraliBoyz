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
    private List<GapField> gapFields;



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

    public List<GapField> gapFields() {
        return gapFields;
    }

    public void setGapField(List<GapField> gapFields) {
        this.gapFields = gapFields;
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

//    private Fragenkategorie ermittleKategorie() {
//
//
//        if (gapFields != null && !gapFields.isEmpty()) {
//            return Fragenkategorie.GAPFIELD;
//        }
//
//
//        return switch (antworten.size()) {
//            case 1 -> Fragenkategorie.LUECKENTEXT;
//            case 2 -> Fragenkategorie.WAHR_FALSCH;
//            case 4 -> Fragenkategorie.MULTIPLE_CHOICE;
//            default -> null;
//        };
//    }


    private Fragenkategorie ermittleKategorie() {

        // 1. Wenn GapFields genutzt werden → eigene Kategorie
        if (gapFields != null && !gapFields.isEmpty()) {
            return Fragenkategorie.GAPFIELD;
        }

        // 2. Wenn Text Lücken "____" enthält → Lückentext
        int luecken = countOccurrences(frage, "____");
        if (luecken > 0) {
            return Fragenkategorie.LUECKENTEXT;
        }

        // 3. Normale Fragen (MC / Wahr-Falsch)
        return switch (antworten.size()) {
            case 2 -> Fragenkategorie.WAHR_FALSCH;
            case 4 -> Fragenkategorie.MULTIPLE_CHOICE;
            default -> null;
        };
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
