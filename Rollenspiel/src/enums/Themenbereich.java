package enums;

public enum Themenbereich {
    SQL("SQL"),
    UML("UML-Diagramme"),
    DATENBANK("Datenbankmodellierung"),
    PSEUDOCODE("Pseudocode"),
    DESIGN_PATTERN("Design Pattern"),
    ;
    private final String name;

    Themenbereich(String name) {
        this.name = name;
    }
}
