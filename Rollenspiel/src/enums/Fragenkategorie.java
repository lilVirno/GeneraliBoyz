package enums;

/**
 * Definiert die verschiedenen Kategorien von Fragen,
 * die im Lernspiel verwendet werden können.
 */
public enum Fragenkategorie {

    /**
     * Multiple Choice – mehrere Antwortoptionen,
     * von denen eine oder mehrere korrekt sein können.
     */
    MULTIPLE_CHOICE("multiple choice"),

    /**
     * Wahr/Falsch – einfache Entscheidungsfrage
     * mit zwei Antwortmöglichkeiten.
     */
    WAHR_FALSCH("wahr/falsch"),

    /**
     * Lückentext – Antwort muss als Text ergänzt werden.
     */
    LUECKENTEXT("Lückentext")
    ;

    /**
     * Textdarstellung der Kategorie.
     */
    private final String kategorie;

    /**
     * Konstruktor für eine Fragenkategorie.
     *
     * @param kategorie Textbeschreibung der Kategorie
     */
    Fragenkategorie(String kategorie) {
        this.kategorie = kategorie;
    }
}