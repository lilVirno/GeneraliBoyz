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

public class LueckentextGUI extends BorderPane {

    private final Startbildschirm main;
    private final Frage aktuelleFrage;
    private final Spieler aktuellerSpieler;

    public LueckentextGUI(Frage frage, Startbildschirm main, Spieler spieler) {

        this.main = main;
        this.aktuelleFrage = frage;
        this.aktuellerSpieler = spieler;

        // Kopf: Frage
        Label frageLabel = new Label(frage.getFrage());
        frageLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
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
            tf.setStyle(
                    "-fx-font-size: 18px;" +
                            "-fx-background-color: white;" +
                            "-fx-background-radius: 6;" +
                            "-fx-text-fill: -fx-text-base-color;" +
                            "-fx-padding: 6 10 6 10;" +
                            "-fx-border-color: -fx-box-border;" +
                            "-fx-border-radius: 6;"
            );
            tf.setPrefColumnCount(16);
            tf.setMaxWidth(300);

            textfelder.add(tf);
            eingabenBox.getChildren().add(tf);
        }

        setCenter(eingabenBox);

        // Prüfen-Button
        Button pruefen = new Button("Prüfen");
        pruefen.setDefaultButton(true);
        pruefen.setStyle(
                "-fx-font-size: 18px;" +
                        "-fx-background-color: #ffffff;" +
                        "-fx-text-fill: black;" +
                        "-fx-padding: 10px 28px;" +
                        "-fx-background-radius: 10;"
        );

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

            alert.setOnHidden(event -> {
                delay.stop();
                this.main.oeffneNaechsteFrageOderBeenden();
            });
        });

        setBottom(pruefen);
        BorderPane.setAlignment(pruefen, Pos.CENTER);
        BorderPane.setMargin(pruefen, new Insets(20));
    }


}
