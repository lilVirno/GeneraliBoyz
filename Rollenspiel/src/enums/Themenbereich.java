package enums;

/**
 * Repräsentiert alle verfügbaren Themenbereiche des Lernspiels.
 */
public enum Themenbereich {

    // Die Strings in den Klammern entsprechen jetzt exakt den INSERTs deiner Datenbank
    SQL("Datenbank - SQL"),
    UML("UML"),
    DATENBANK("Datenbanken Modellierung"),
    PSEUDOCODE("Programmierung Pseudocode"),
    RECHT("Recht"),
    WIRTSCHAFT("Wirtschaft"),
    MASCHINELLESLEARNING("Maschinelles Lernen"),
    KEINTHEMA("Für Tests");

    private final String name;

    Themenbereich(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * Sucht den passenden Themenbereich basierend auf dem String aus der Datenbank.
     * Ersetzt die fehleranfällige valueOf() Methode.
     */
    public static Themenbereich fromDatabaseName(String dbName) {
        for (Themenbereich t : Themenbereich.values()) {
            if (t.getName().equalsIgnoreCase(dbName)) {
                return t;
            }
        }

        // Falls ein Thema in der DB steht, das wir im Enum vergessen haben
        System.err.println("Warnung: Kein Enum-Match für Thema gefunden: " + dbName);
        return KEINTHEMA;
    }
}