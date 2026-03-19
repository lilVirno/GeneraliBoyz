package backend;

/**
 * Repräsentiert eine Antwort in einem Quiz oder Fragesystem.
 * Enthält den Antworttext sowie die Information, ob die Antwort richtig ist.
 */
public class Antwort {

    /**
     * Gibt an, ob die Antwort korrekt ist.
     */
    private boolean richtig;

    /**
     * Der Text der Antwort.
     */
    private String antwort;

    /**
     * Erstellt eine neue Antwort mit Angabe der Richtigkeit und des Antworttexts.
     *
     * @param richtig true, wenn die Antwort korrekt ist; false sonst
     * @param antwort der Text der Antwort
     */
    public Antwort(boolean richtig, String antwort) {
        this.richtig = richtig;
        this.antwort = antwort;
    }

    /**
     * Gibt zurück, ob die Antwort korrekt ist.
     *
     * @return true, wenn die Antwort korrekt ist; false sonst
     */
    public boolean isRichtig() {
        return richtig;
    }

    /**
     * Setzt, ob die Antwort korrekt ist.
     *
     * @param richtig true, wenn die Antwort korrekt sein soll; false sonst
     */
    public void setRichtig(boolean richtig) {
        this.richtig = richtig;
    }

    /**
     * Gibt den Text der Antwort zurück.
     *
     * @return der Antworttext
     */
    public String getAntwort() {
        return antwort;
    }

    /**
     * Setzt den Antworttext.
     *
     * @param antwort der neue Antworttext
     */
    public void setAntwort(String antwort) {
        this.antwort = antwort;
    }



}
