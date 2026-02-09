import enums.Fragenkategorie;
import enums.Themenbereich;

import java.util.List;

public class Frage {
    private Themenbereich themenbereich;
    private Fragenkategorie fragenkategorie;
    private String frage;
    private List<Antwort> antworten;

    public Frage(Themenbereich themenbereich, String frage, List<Antwort> antworten) {
        this.themenbereich = themenbereich;
        this.frage = frage;
        this.antworten = antworten;
        this.fragenkategorie = ermittleKategorie();
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

    private Fragenkategorie ermittleKategorie() {
        return switch (antworten.size()) {
            case 1 -> Fragenkategorie.LUECKENTEXT;
            case 2 -> Fragenkategorie.WAHR_FALSCH;
            case 4 -> Fragenkategorie.MULTIPLE_CHOICE;
            default -> null;
        };
    }
}
