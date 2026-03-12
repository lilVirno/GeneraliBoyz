package enums;

public enum Fragenkategorie {
    MULTIPLE_CHOICE("multiple choice"),
    WAHR_FALSCH("wahr/falsch"),
    LUECKENTEXT("Lückentext")
    ;

    private final String kategorie;

    Fragenkategorie(String kategorie) {
        this.kategorie = kategorie;
    }
}
