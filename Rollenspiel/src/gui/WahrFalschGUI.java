package gui;

import backend.Antwort;
import backend.Frage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class WahrFalschGUI extends BorderPane {

    public WahrFalschGUI(Frage frage) {

        Label frageLabel = new Label(frage.getFrage());
        frageLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        setTop(frageLabel);
        BorderPane.setAlignment(frageLabel, Pos.CENTER);
        BorderPane.setMargin(frageLabel, new Insets(20, 0, 20, 0));

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));

        for (Antwort antwort : frage.getAntworten()) {
            Button btn = new Button(antwort.getAntwort());
            btn.setStyle("-fx-font-size: 18px; -fx-padding: 10px 20px;");
            btn.setOnAction(e -> pruefeAntwort(antwort));
            buttonBox.getChildren().add(btn);
        }

        setCenter(buttonBox);
    }

    private void pruefeAntwort(Antwort antwort) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        if (antwort.isRichtig()) {
            alert.setContentText("Richtig!");
        } else {
            alert.setContentText("Falsch!");
        }

        alert.showAndWait();
    }
}
