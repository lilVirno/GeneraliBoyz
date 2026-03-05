package gui;

import backend.Antwort;
import backend.Frage;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceGUI extends BorderPane {

    private final Startbildschirm main;
    private final List<CheckBox> checkBoxes = new ArrayList<>();
    private final Frage aktuelleFrage;

    public MultipleChoiceGUI(Frage frage, Startbildschirm main) {

        this.main = main;
        this.aktuelleFrage = frage;

        // Frage oben
        Label frageLabel = new Label(frage.getFrage());
        frageLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-alignment: center;");
        frageLabel.setWrapText(true);
        setTop(frageLabel);
        BorderPane.setAlignment(frageLabel, Pos.CENTER);
        BorderPane.setMargin(frageLabel, new Insets(20));

        // Antwort-Container
        VBox antwortBox = new VBox(15);
        antwortBox.setAlignment(Pos.CENTER_LEFT);
        antwortBox.setPadding(new Insets(20, 50, 20, 50));

        // Checkboxen für jede Antwort erstellen
        for (Antwort antwort : frage.getAntworten()) {
            CheckBox cb = new CheckBox(antwort.getAntwort());
            cb.setStyle("-fx-font-siye: 32px;");
            cb.setUserData(antwort);
            checkBoxes.add(cb);
            antwortBox.getChildren().add(cb);
        }
        setCenter(antwortBox);

        // Bestätigen Button unten
        Button submitBtn = new Button("Prüfen");
        submitBtn.setStyle("-fx-font-size: 20px; -fx-background-color: #Ffffff; -fx-text-fill: black; -fx-padding: 10px 44px; -fx-background-radius: 10;");
        submitBtn.setOnAction(e -> pruefeAntwort());

        HBox bottomBox = new HBox(submitBtn);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setPadding(new Insets(20));
        setBottom(bottomBox);
    }

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

    private void zeigeErgebnis(boolean erfolg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        if (erfolg) {
            alert.setContentText("Richtig! Alle korrekten Antworten wurden ausgewählt.");
        } else {
            alert.setContentText("Falsch! Die Komination der Antworten stimmt nicht.");
        }

        // Timer zum automatischen Schließen des Pop-ups und Weiterleitung
        PauseTransition delay = new PauseTransition(Duration.seconds(3));

        delay.setOnFinished(event -> {
            if (alert.isShowing()) {
                alert.close();
            }
        });

        // Weiterleitung bei manuellen Schließen des Pop-ups
        alert.setOnHidden(event -> {
            // verhinert doppeltes Auführen
            delay.stop();
            main.oeffneNaechsteFrageOderBeenden();
        });

        alert.show();
        delay.play();
    }
}
