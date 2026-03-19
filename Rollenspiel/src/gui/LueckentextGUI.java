package gui;

import backend.Frage;
import backend.GapField;
import backend.Spieler;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static backend.Frage.countOccurrences;


/**
 * GUI-Komponente für die Darstellung und Bearbeitung einer Lückentextfrage.
 * Diese Ansicht zeigt eine Frage, ein Eingabefeld sowie einen Prüfbutton
 * und wertet die Antwort des Spielers aus. Bei korrekter Lösung werden
 * Punkte vergeben und der Spielerfortschritt aktualisiert.
 */

public class LueckentextGUI extends BorderPane {

    /**
     * Referenz auf den Startbildschirm, um zur nächsten Frage navigieren zu können.
     */
    private final Startbildschirm main;

    /**
     * Die aktuell angezeigte Frage.
     */
    private final Frage aktuelleFrage;

    /**
     * Spieler, der diese Frage beantwortet.
     */
    private final Spieler aktuellerSpieler;

    /**
     * Erstellt eine GUI für eine Lückentextfrage.
     *
     * @param frage   die anzuzeigende Frage
     * @param main    Referenz auf den Startbildschirm für Navigation
     * @param spieler der aktuell spielende Spieler
     */
    public LueckentextGUI(Frage frage, Startbildschirm main, Spieler spieler) {

        this.main = main;
        this.aktuelleFrage = frage;
        this.aktuellerSpieler = spieler;

        Label frageLabel = new Label(frage.getFrage());
        frageLabel.setStyle(UIStyles.FRAGE_LABEL);
        setTop(frageLabel);
        BorderPane.setAlignment(frageLabel, Pos.CENTER);
        BorderPane.setMargin(frageLabel, new Insets(20, 0, 20, 0));

        // >>> Anzahl korrekter Antworten = Anzahl Lücken
        // Falls du die Methode noch nicht hast, siehe unten "Frage-Erweiterung"
        List<String> korrekteAntworten = frage.getKorrekteAntworten();

        // Optional: Prüfen, ob Anzahl "____" im Text zur Anzahl Antworten passt
        int lueckenImText = countOccurrences(frage.getFrage() ,"____");
        if (lueckenImText > 0 && lueckenImText != korrekteAntworten.size()) {
            System.err.println("Warnung: Anzahl Lücken im Text (" + lueckenImText +
                    ") stimmt nicht mit Anzahl korrekter Antworten (" + korrekteAntworten.size() + ") überein.");
        }

        // Container für mehrere Textfelder (Eingabefelder!)
        VBox eingabenBox = new VBox(12);
        eingabenBox.setPadding(new Insets(20));
        eingabenBox.setFillWidth(false);
        eingabenBox.setAlignment(Pos.TOP_CENTER);

        // Liste der Textfelder für spätere Auswertung
        List<TextField> textfelder = new ArrayList<>(korrekteAntworten.size());

//        for (int i = 0; i < korrekteAntworten.size(); i++) {
//            TextField tf = new TextField();
//            tf.setPromptText("Antwort " + (i + 1) + " eingeben...");
//            // WICHTIG: neutrales Styling, damit es nicht wie Buttons aussieht
//            tf.setStyle(
//                    "-fx-font-size: 18px;" +
//                            "-fx-background-color: white;" +
//                            "-fx-background-radius: 6;" +
//                            "-fx-text-fill: -fx-text-base-color;" +
//                            "-fx-padding: 6 10 6 10;" +
//                            "-fx-border-color: -fx-box-border;" +
//                            "-fx-border-radius: 6;"
//            );
//            tf.setPrefColumnCount(16);
//            tf.setMaxWidth(300);
//
//            textfelder.add(tf);
//            eingabenBox.getChildren().add(tf);
//        }

        for (int i = 0; i < frage.getAntworten().size(); i++) {
            TextField tf = new TextField();
            tf.setPromptText("Antwort " + (i + 1) + " eingeben...");
            // WICHTIG: neutrales Styling, damit es nicht wie Buttons aussieht
            tf.setStyle(UIStyles.LÜCKENTEXT_FELD);
            tf.setPrefColumnCount(16);
            tf.setMaxWidth(300);

            textfelder.add(tf);
            eingabenBox.getChildren().add(tf);
        }

        setCenter(eingabenBox);

        // Prüfen-Button
        Button pruefen = new Button("Prüfen");
        pruefen.setDefaultButton(true);
        pruefen.setStyle(UIStyles.ANTWORT_BUTTON);


        /**
         * Eventhandler für den Prüfbutton.
         * Vergleicht die Benutzereingabe mit der korrekten Antwort,
         * zeigt ein Info-Popup und aktualisiert ggf. den Fortschritt des Spielers.
         */
        pruefen.setOnAction(e -> {
            boolean alleRichtig = true;

            for (int i = 0; i < korrekteAntworten.size(); i++) {
                String eingabe = textfelder.get(i).getText().trim();
                String korrekt = korrekteAntworten.get(i).trim();

                if (!eingabe.equalsIgnoreCase(korrekt)) {
                    alleRichtig = false;
                    break;
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);

            if (alleRichtig) {
                alert.setContentText("Alle Antworten korrekt!");
                this.aktuelleFrage.setGeloest();
                aktuellerSpieler.addPunkte(aktuelleFrage);
            } else {
                alert.setContentText("Mindestens eine Antwort ist falsch!");
            }

            alert.show();

            PauseTransition delay = new PauseTransition(Duration.seconds(3));
            delay.setOnFinished(event -> {
                alert.close();
                main.oeffneNaechsteFrageOderBeenden();
            });
            delay.play();

            // Weiterleitung bei manuellem Schließen des Pop-ups
            alert.setOnHidden(event -> {
                // verhindert doppeltes Ausführen
                delay.stop();
                this.main.oeffneNaechsteFrageOderBeenden();
            });
        });

        setBottom(pruefen);
        BorderPane.setAlignment(pruefen, Pos.CENTER);
        BorderPane.setMargin(pruefen, new Insets(20));
    }


}
