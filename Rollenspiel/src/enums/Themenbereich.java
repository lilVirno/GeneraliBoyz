package enums;

public enum Themenbereich {
    SQL("SQL"),
    UML("UML-Diagramme"),
    DATENBANK("Datenbank - Modellierung"),
    PSEUDOCODE("Pseudocode"),
    RECHT("Recht"),
    WIRTSCHAFT("Wirtschaft"),
    MASCHINELLESLEARNING("Maschinelles Lernen"),
    KEINTHEMA("Für Tests")
    ;
    private final String name;

    Themenbereich(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
