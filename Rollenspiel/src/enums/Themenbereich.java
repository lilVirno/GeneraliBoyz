package enums;

public enum Themenbereich {
    SQL("SQL"),
    UML("UML-Diagramme"),
    DATENBANK("Datenbankmodellierung"),
    PSEUDOCODE("Pseudocode"),
    RECHT("Recht"),
    WIRTSCHAFT("Wirtschaft"),
    MASCHINELLESLEARNING("Maschinelles Lernen"),
    ;
    private final String name;

    Themenbereich(String name) {
        this.name = name;
    }
}
