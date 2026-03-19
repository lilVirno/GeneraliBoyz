package gui;

import backend.Antwort;
import backend.Frage;
import backend.Spieler;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * GUI-Klasse zur Darstellung und Bearbeitung von Multiple-Choice-Fragen.
 * Sie zeigt die Frage, die verfügbaren Antwortoptionen als CheckBoxen,
 * prüft die Antwortkombination und aktualisiert den Spielerfortschritt.
 */
public class MultipleChoiceGUI extends BorderPane {

    /**
     * Referenz auf den Startbildschirm zur Navigation zur nächsten Frage.
     */
    private final Startbildschirm main;

    /**
     * Liste der CheckBoxen, die die Antwortoptionen repräsentieren.
     */
    private final List<CheckBox> checkBoxes = new ArrayList<>();

    /**
     * Die aktuelle Multiple-Choice-Frage.
     */
    private final Frage aktuelleFrage;

    /**
     * Der Spieler, der diese Frage beantwortet.
     */
    private final Spieler aktuellerSpieler;

    /**
     * Konstruktor für das Multiple-Choice-GUI.
     *
     * @param frage   die anzuzeigende Frage
     * @param main    die Hauptoberfläche zur Navigation
     * @param spieler der aktuelle Spieler
     */
    public MultipleChoiceGUI(Frage frage, Startbildschirm main, Spieler spieler) {

        this.main = main;
        this.aktuelleFrage = frage;
        this.aktuellerSpieler = spieler;

        // Frage oben
        Label frageLabel = new Label(frage.getFrage());
        frageLabel.setStyle(UIStyles.FRAGE_LABEL);
        frageLabel.setWrapText(true);
        setTop(frageLabel);
        BorderPane.setAlignment(frageLabel, Pos.CENTER);
        BorderPane.setMargin(frageLabel, new Insets(20));

        // Antwort-Container
        VBox antwortBox = new VBox(15);
        antwortBox.setAlignment(Pos.CENTER_LEFT);
        antwortBox.setPadding(new Insets(20, 50, 20, 50));

        /**
         * Erstellen der CheckBoxen für jede Antwortoption.
         * Jede CheckBox hält im UserData-Feld das zugehörige Antwort-Objekt.
         */
        for (Antwort antwort : frage.getAntworten()) {
            CheckBox cb = new CheckBox(antwort.getAntwort());
            cb.setFocusTraversable(false);
            cb.setUserData(antwort);

            // 1. Basis-Styling für Text und Abstände
            String baseStyle = UIStyles.ANTWORT_MC;

            cb.setStyle(baseStyle);

            // 2. Styling für das Quadrat (die "Box") selbst:
            // Wir nutzen Lookups erst nach dem Rendern oder setzen ein allgemeines Stylesheet.
            // Da wir hier im Loop sind, ist die sauberste Methode für "abgerundet & weiß"
            // das Anwenden auf die Sub-Struktur via CSS-String:
            cb.lookupAll(".box").forEach(node -> node.setStyle(UIStyles.CHECKBOX_MC));

            // Falls das lookup oben noch nichts findet (weil noch nicht angezeigt),
            // fügen wir das Styling für die Box als "festen" Bestandteil hinzu:
            cb.setStyle(cb.getStyle() + UIStyles.CHECKBOX_MC_STYLE);



            // Hover-Effekt (sanftes Grau für den Hintergrund des ganzen Elements)
            cb.setOnMouseEntered(e -> cb.setStyle(cb.getStyle() + UIStyles.CHECKBOX_MC_HOVER));
            cb.setOnMouseExited(e -> cb.setStyle(cb.getStyle().replace(UIStyles.CHECKBOX_MC_HOVER_EXIT, "")));

            checkBoxes.add(cb);
            antwortBox.getChildren().add(cb);
        }

// Abstand zwischen den Checkboxen in der VBox (antwortBox) einstellen
        antwortBox.setSpacing(15);
        setCenter(antwortBox);

        // Button zum Prüfen der Antwort
        Button submitBtn = new Button("Prüfen");
        submitBtn.setDefaultButton(true);
        submitBtn.setStyle(UIStyles.ANTWORT_BUTTON);
        submitBtn.setOnAction(e -> pruefeAntwort());

        HBox bottomBox = new HBox(submitBtn);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));
        setBottom(bottomBox);
    }

    /**
     * Prüft, ob die vom Spieler ausgewählten Checkboxen der korrekten
     * Kombination der Frage entsprechen.
     */
    private void pruefeAntwort() {
        boolean allesRichtig = true;

        for (CheckBox cb : checkBoxes) {
            Antwort antwort = (Antwort) cb.getUserData();

            if (antwort.isRichtig() != cb.isSelected()) {
                allesRichtig = false;
                break;
            }
        }

        zeigeErgebnis(allesRichtig);
    }

    /**
     * Zeigt eine Rückmeldung (Popup) für richtig oder falsch an,
     * vergibt bei Erfolg Punkte und navigiert nach kurzer Wartezeit weiter.
     *
     * @param erfolg true, wenn alle korrekten Antworten gewählt wurden
     */
    private void zeigeErgebnis(boolean erfolg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        if (erfolg) {
            alert.setContentText("Richtig! Alle korrekten Antworten wurden ausgewählt.");
            this.aktuelleFrage.setGeloest();
            aktuellerSpieler.addPunkte(aktuelleFrage);
        } else {
            alert.setContentText("Falsch! Die Komination der Antworten stimmt nicht.");
        }

        // Timer zum automatischen Schließen des Popups
        PauseTransition delay = new PauseTransition(Duration.seconds(3));

        delay.setOnFinished(event -> {
            if (alert.isShowing()) {
                alert.close();
            }
        });

        // Weiterleitung nach Schließen des Popups
        alert.setOnHidden(event -> {
            delay.stop();
            this.main.oeffneNaechsteFrageOderBeenden();
        });

        alert.show();
        delay.play();
    }
}