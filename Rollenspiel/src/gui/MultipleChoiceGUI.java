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

public class MultipleChoiceGUI extends BorderPane {

    private final Startbildschirm main;
    private final List<CheckBox> checkBoxes = new ArrayList<>();
    private final Frage aktuelleFrage;
    private final Spieler aktuellerSpieler;

    public MultipleChoiceGUI(Frage frage, Startbildschirm main, Spieler spieler) {

        this.main = main;
        this.aktuelleFrage = frage;
        this.aktuellerSpieler = spieler;

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
            cb.setFocusTraversable(false);
            cb.setUserData(antwort);

            // 1. Basis-Styling für Text und Abstände
            String baseStyle = "-fx-font-size: 24px; "
                    + "-fx-text-fill: #333333; "
                    + "-fx-padding: 10px; "
                    + "-fx-cursor: hand; ";

            cb.setStyle(baseStyle);

            // 2. Styling für das Quadrat (die "Box") selbst:
            // Wir nutzen Lookups erst nach dem Rendern oder setzen ein allgemeines Stylesheet.
            // Da wir hier im Loop sind, ist die sauberste Methode für "abgerundet & weiß"
            // das Anwenden auf die Sub-Struktur via CSS-String:
            cb.lookupAll(".box").forEach(node -> node.setStyle("-fx-background-radius: 5; -fx-background-color: white; -fx-border-color: #cccccc; -fx-border-radius: 5;"));

            // Falls das lookup oben noch nichts findet (weil noch nicht angezeigt),
            // fügen wir das Styling für die Box als "festen" Bestandteil hinzu:
            cb.setStyle(cb.getStyle() + "-fx-box-background: white; -fx-box-border: #cccccc;");



            // Hover-Effekt (sanftes Grau für den Hintergrund des ganzen Elements)
            cb.setOnMouseEntered(e -> cb.setStyle(cb.getStyle() + "-fx-background-color: #f9f9f9; -fx-background-radius: 8;"));
            cb.setOnMouseExited(e -> cb.setStyle(cb.getStyle().replace("-fx-background-color: #f9f9f9; -fx-background-radius: 8;", "")));

            checkBoxes.add(cb);
            antwortBox.getChildren().add(cb);
        }

// Abstand zwischen den Checkboxen in der VBox (antwortBox) einstellen
        antwortBox.setSpacing(15);
        setCenter(antwortBox);

        // Bestätigen Button unten
        Button submitBtn = new Button("Prüfen");
        submitBtn.setDefaultButton(true);
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
            this.aktuelleFrage.setGeloest();
            aktuellerSpieler.addPunkte(aktuelleFrage);
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
            this.main.oeffneNaechsteFrageOderBeenden();
        });

        alert.show();
        delay.play();
    }
}
