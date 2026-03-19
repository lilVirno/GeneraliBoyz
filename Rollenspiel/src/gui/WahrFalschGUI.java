package gui;

import backend.Antwort;
import backend.Frage;
import backend.Spieler;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class WahrFalschGUI extends BorderPane {

    private final Startbildschirm main;
    private final Frage aktuelleFrage;
    private final Spieler aktuellerSpieler;

    public WahrFalschGUI(Frage frage, Startbildschirm main, Spieler spieler) {

        this.main = main;
        this.aktuelleFrage = frage;
        this.aktuellerSpieler = spieler;

        Label frageLabel = new Label(frage.getFrage());
        frageLabel.setStyle(UIStyles.FRAGE_LABEL);
        setTop(frageLabel);
        BorderPane.setAlignment(frageLabel, Pos.CENTER);
        BorderPane.setMargin(frageLabel, new Insets(20, 0, 20, 0));

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));

        for (Antwort antwort : frage.getAntworten()) {
            Button btn = new Button(antwort.getAntwort());
            btn.setStyle(UIStyles.ANTWORT_BUTTON);
            btn.setOnAction(e -> pruefeAntwort(antwort));
            buttonBox.getChildren().add(btn);
            btn.setFocusTraversable(false);
        }

        setCenter(buttonBox);
    }

    private void pruefeAntwort(Antwort antwort) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        if (antwort.isRichtig()) {
            alert.setContentText("Richtig!");
            aktuelleFrage.setGeloest();
            aktuellerSpieler.addPunkte(aktuelleFrage);
        } else {
            alert.setContentText("Falsch!");
        }

        alert.show();

        // Timer zum automatischen Schließen des Pop-ups und Weiterleitung
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> {
            alert.close();
            main.oeffneNaechsteFrageOderBeenden();
        });
        delay.play();

        // Weiterleitung bei manuellen Schließen des Pop-ups
        alert.setOnHidden(event -> {
            // verhinert doppeltes Auführen
            delay.stop();
            main.oeffneNaechsteFrageOderBeenden();
        });
    }
}
