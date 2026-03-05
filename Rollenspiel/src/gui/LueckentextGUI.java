package gui;

import backend.Frage;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

public class LueckentextGUI extends BorderPane {

    private final Startbildschirm main;
    private final Frage aktuelleFrage;

    public LueckentextGUI(Frage frage, Startbildschirm main) {

        this.main = main;
        this.aktuelleFrage = frage;

        Label frageLabel = new Label(frage.getFrage());
        frageLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        setTop(frageLabel);
        BorderPane.setAlignment(frageLabel, Pos.CENTER);
        BorderPane.setMargin(frageLabel, new Insets(20, 0, 20, 0));

        TextField eingabe = new TextField();
        eingabe.setPromptText("Antwort eingeben...");
        eingabe.setStyle("-fx-font-size: 18px;");
        setCenter(eingabe);
        BorderPane.setMargin(eingabe, new Insets(20));

        Button pruefen = new Button("Prüfen");
        pruefen.setStyle("-fx-font-size: 20px;"
                        + "-fx-background-color: #Ffffff;"
                        + "-fx-text-fill: black;"
                        + "-fx-padding: 10px 44px;"
                        + "-fx-background-radius: 10;");
        pruefen.setOnAction(e -> {
            String korrekt = frage.getKorrekteAntwort();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);

            if (eingabe.getText().trim().equalsIgnoreCase(korrekt)) {
                alert.setContentText("Richtig!");
                this.aktuelleFrage.setGeloest();

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
        });

        setBottom(pruefen);
        BorderPane.setAlignment(pruefen, Pos.CENTER);
        BorderPane.setMargin(pruefen, new Insets(20));
    }
}
