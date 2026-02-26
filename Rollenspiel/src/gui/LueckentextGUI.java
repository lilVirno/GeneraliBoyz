package gui;

import backend.Frage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class LueckentextGUI extends BorderPane {

    private final Startbildschirm main;

    public LueckentextGUI(Frage frage, Startbildschirm main) {

        this.main = main;

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
            } else {
                alert.setContentText("Falsch!");
            }

            alert.showAndWait();

            main.oeffneNaechsteFrageOderBeenden();
        });

        setBottom(pruefen);
        BorderPane.setAlignment(pruefen, Pos.CENTER);
        BorderPane.setMargin(pruefen, new Insets(20));
    }
}
