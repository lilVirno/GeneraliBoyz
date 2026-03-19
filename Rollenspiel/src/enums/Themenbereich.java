package enums;

/**
 * Repräsentiert alle verfügbaren Themenbereiche des Lernspiels.
 * Jeder Themenbereich besitzt zusätzlich eine lesbare Textbeschreibung.
 */
public enum Themenbereich {

    /**
     * Themenbereich SQL – Abfragen, Tabellen, Datenmanipulation.
     */
    SQL("SQL"),

    /**
     * Themenbereich UML-Diagramme – Modellierung von Systemstrukturen.
     */
    UML("UML-Diagramme"),

    /**
     * Themenbereich Datenbankmodellierung – Entity-Relationship-Modelle usw.
     */
    DATENBANK("Datenbank - Modellierung"),

    /**
     * Themenbereich Pseudocode – logische Ablaufbeschreibung.
     */
    PSEUDOCODE("Pseudocode"),

    /**
     * Themenbereich Recht – Datenschutz, Urheberrecht, IT-relevante Gesetzesthemen.
     */
    RECHT("Recht"),

    /**
     * Themenbereich Wirtschaft – Grundlagen wirtschaftlicher Zusammenhänge.
     */
    WIRTSCHAFT("Wirtschaft"),

    /**
     * Themenbereich Maschinelles Lernen – grundlegende ML‑Konzepte.
     */
    MASCHINELLESLEARNING("Maschinelles Lernen"),
    ;

    /**
     * Lesbare Bezeichnung des Themenbereichs.
     */
    private final String name;

    /**
     * Konstruktor zur Zuordnung eines sichtbaren Namens.
     *
     * @param name Anzeigename des Themenbereichs
     */
    Themenbereich(String name) {
        this.name = name;
    }

    /**
     * Gibt den Anzeigenamen des Themenbereichs zurück.
     *
     * @return lesbare Themenbezeichnung
     */
    public String getName(){
        return name;
    }
}