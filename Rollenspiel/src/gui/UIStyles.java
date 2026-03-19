package gui;

public class UIStyles {

    // -------------------------------------------
    // Farben
    // -------------------------------------------

    // Hauptfarbe der App (Türkis)
    public static final String MAIN_COLOR = "#2EA3A3";

    // Akzentfarbe (Blau) für Buttons, Highlights
    public static final String ACCENT_COLOR = "#3498db";

    // Hoverfarbe für Buttons/Elemente
    public static final String HOVER_COLOR = "#2980b9";


    // -------------------------------------------
    // Hintergründe
    // -------------------------------------------

    // Haupt-Hintergrundfarbe des Fensters
    public static final String MAIN_BACKGROUND = "-fx-background-color: " + MAIN_COLOR + ";";

    // Dunkler, halbtransparenter Blur-Overlay (z.B. für Popups)
    public static final String OVERLAY_BLUR = "-fx-background-color: rgba(0,0,0,0.7);";

    // Popup-Container mit Radius und weißem Rand
    public static final String POPUP_CONTAINER =
            "-fx-background-color: " + MAIN_COLOR + "; -fx-background-radius: 20; -fx-border-color: white; -fx-border-width: 2;";

    // Einfach schwarzer Hintergrund
    public static final String BLACK = "-fx-background-color: black;";

    // Transparenter, abgerundeter Overlay-Hintergrund
    public static final String OVERLAY_BACKGROUND =
            "-fx-background-color: rgba(0,0,0,0.28); -fx-background-radius: 12;";


    // -------------------------------------------
    // Buttons
    // -------------------------------------------

    // Hauptbutton-Stil (Akzentfarbe, groß, abgerundet)
    public static final String BUTTON_PRIMARY =
            "-fx-font-size: 20px; -fx-background-color: " + ACCENT_COLOR + "; -fx-text-fill: white; -fx-padding: 10 44; -fx-background-radius: 10; -fx-cursor: hand;";

    // Navigation-Buttons (halbtransparent, weißer Text)
    public static final String NAV_BUTTON =
            "-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;";


    // -------------------------------------------
    // Themen-Kacheln (Glassmorphism)
    // -------------------------------------------

    // Grundstil der Themenkacheln (Glasoptik)
    public static final String THEME_TILE_BASE =
            "-fx-background-color: rgba(255, 255, 255, 0.15); -fx-background-radius: 20; -fx-border-color: rgba(255, 255, 255, 0.3); " +
                    "-fx-border-radius: 20; -fx-border-width: 2; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-cursor: hand;";

    // Hovereffekt für Themenkacheln (heller + Vergrößerung)
    public static final String THEME_TILE_HOVER =
            THEME_TILE_BASE + "-fx-background-color: rgba(255, 255, 255, 0.25); -fx-scale-x: 1.05; -fx-scale-y: 1.05;";


    // -------------------------------------------
    // Texte & Eingabefelder
    // -------------------------------------------

    // Großer Titeltext
    public static final String LABEL_TITLE = "-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: white;";

    // Untertiteltext
    public static final String LABEL_SUBTITLE = "-fx-font-size: 16px; -fx-text-fill: rgba(255,255,255,0.8);";

    // Textfelder (Eingabe)
    public static final String TEXT_FIELD = "-fx-font-size: 18px; -fx-background-radius: 10; -fx-padding: 10;";

    // Frageanzeige
    public static final String FRAGE = "-fx-font-size: 22px; -fx-text-fill: white; -fx-font-weight: bold;";

    // Themenüberschrift
    public static final String THEMEN_LABEL = "-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;";

    // Transparente Scrollpane
    public static final String SCROLL_PANE = "-fx-background: transparent; -fx-background-color: transparent;";

    // Durchsichtiges Pane
    public static final String PANEL_DURCHSICHTIG = "-fx-background-color: transparent;";


    // -------------------------------------------
    // Allgemeine Kacheln
    // -------------------------------------------

    public static final String KACHELN =
            "-fx-background-color: rgba(255, 255, 255, 0.15);"
                    + "-fx-background-radius: 20;"
                    + "fx-border-color: rgba(255, 255, 255, 0.3);"
                    + "-fx-border-radius: 20;"
                    + "-fx-border-width: 2;"
                    + "-fx-text-fill: white;"
                    + "-fx-font-size: 18px;"
                    + "-fx-font-weight: bold;"
                    + "-fx-cursor: hand;";

    // Hovereffekt für allgemeine Kacheln
    public static final String KACHELN_HOVER =
            KACHELN + "-fx-background-color: rgba(255, 255, 255, 0.25); -fx-scale-x: 1.05; -fx-scale-y: 1.05;";


    // -------------------------------------------
    // Profilansicht
    // -------------------------------------------

    // Titel im Profil (z.B. Benutzername)
    public static final String PROFIL_TITEL = "-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: white;";

    // Hintergrund für Statistikfelder
    public static final String PROFIL_STATS = "-fx-background-color: rgba(0,0,0,0.15); -fx-padding: 10; -fx-background-radius: 10;";

    // Level-Label im Profil
    public static final String PROFIL_LEVEL_LABEL = "-fx-text-fill: #f1c40f; -fx-font-weight: bold; -fx-font-size: 16px;";

    // Punkteanzeige im Profil
    public static final String PROFIL_PUNKTE_LABEL = "-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;";

    // Hintergrund des Fortschrittsbalkens
    public static final String PROFIL_BALKEN = "-fx-background-color: rgba(255,255,255,0.05); -fx-background-radius: 10;";

    // Text auf dem Gesamtbalken
    public static final String PROFIL_GESAMT_BALKEN = "-fx-text-fill: white; -fx-font-weight: bold;";

    // Titel für Medaillenbereich
    public static final String PROFIL_MED_TITEL = "-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;";

    // Anzeige falls keine Medaillen vorhanden sind
    public static final String PROFIL_NO_MED = "-fx-text-fill: rgba(255,255,255,0.4);";


    // -------------------------------------------
    // Quiz – Buttons & Checkboxen
    // -------------------------------------------

    // Haupt-Quizbutton für Antworten
    public static final String BUTTON_MAIN = "-fx-font-size: 20px;"
            + "-fx-background-color: #3498db;"
            + "-fx-text-fill: white;"
            + "-fx-padding: 10px 44px;"
            + "-fx-background-radius: 10;";

    // Frage-Label im Quiz
    public static final String FRAGE_LABEL = "-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-alignment: center;";

    // Antwortbutton (weiß)
    public static final String ANTWORT_BUTTON =
            "-fx-font-size: 20px; -fx-background-color: #FFFFFF; -fx-text-fill: black; -fx-padding: 10px 44px; -fx-background-radius: 10;";

    // Multiple-Choice‑Antworten
    public static final String ANTWORT_MC = "-fx-font-size: 24px; "
            + "-fx-text-fill: #333333; "
            + "-fx-padding: 10px; "
            + "-fx-cursor: hand; ";

    // Checkbox-Grundstil
    public static final String CHECKBOX_MC =
            "-fx-background-radius: 5; -fx-background-color: white; -fx-border-color: #cccccc; -fx-border-radius: 5;";

    // Checkbox-Innenstil
    public static final String CHECKBOX_MC_STYLE =
            "-fx-box-background: white; -fx-box-border: #cccccc;";

    // Hovereffekt Checkbox
    public static final String CHECKBOX_MC_HOVER =
            "-fx-background-color: #f9f9f9; -fx-background-radius: 8;";

    // Hover-Zustand verlassen
    public static final String CHECKBOX_MC_HOVER_EXIT =
            "-fx-background-color: #f9f9f9; -fx-background-radius: 8;";


    // Lückentext-Eingabefeld
    public static final String LÜCKENTEXT_FELD =
            "-fx-font-size: 18px; -fx-background-color: white; -fx-background-radius: 6; -fx-text-fill: -fx-text-base-color; "
                    + "-fx-padding: 6 10 6 10; -fx-border-color: -fx-box-border; -fx-border-radius: 6;";
}