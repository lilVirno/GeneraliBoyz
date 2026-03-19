package backend;

import java.util.List;

/**
 * Verwaltet eine Liste von Fragen und ermöglicht das Navigieren
 * zwischen einzelnen Fragen. Hält intern einen Index, der die
 * aktuelle Frage repräsentiert.
 */
public class FragenController {

    /**
     * Liste aller Fragen, die der Controller verwaltet.
     */
    private final List<Frage> fragen;

    /**
     * Aktueller Index innerhalb der Fragenliste.
     */
    private int index = 0;

    /**
     * Erstellt einen neuen FragenController mit einer Liste von Fragen.
     *
     * @param fragen die zu verwaltenden Fragen
     */
    public FragenController(List<Frage> fragen) {
        this.fragen = fragen;
    }

    /**
     * Liefert die Frage, die aktuell durch den Index ausgewählt ist.
     *
     * @return die aktuelle Frage
     */
    public Frage getAktuelleFrage() {
        return fragen.get(index);
    }

    /**
     * Prüft, ob nach der aktuellen Frage noch weitere Fragen existieren.
     *
     * @return true, wenn eine nächste Frage verfügbar ist
     */
    public boolean hatNaechsteFrage() {
        return index < fragen.size() - 1;
    }

    /**
     * Geht zur nächsten Frage über, falls vorhanden.
     * Wird einfach der Index erhöht.
     */
    public void naechsteFrage() {
        index++;
        getAktuelleFrage();
    }
}