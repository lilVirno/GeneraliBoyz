package gui;

import backend.Antwort;
import backend.Frage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MultipleChoiceGUI extends BorderPane {

    public MultipleChoiceGUI(Frage frage) {

        // Frage oben
        Label frageLabel = new Label(frage.getFrage());
        frageLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        setTop(frageLabel);
        BorderPane.setAlignment(frageLabel, Pos.CENTER);
        BorderPane.setMargin(frageLabel, new Insets(20, 0, 20, 0));

        // Antwort-Buttons
        VBox antwortBox = new VBox(12);
        antwortBox.setAlignment(Pos.CENTER);
        antwortBox.setPadding(new Insets(20));

        for (Antwort antwort : frage.getAntworten()) {
            Button btn = new Button(antwort.getAntwort());
            btn.setStyle("-fx-font-size: 18px; -fx-padding: 10px 20px;");
            btn.setMaxWidth(Double.MAX_VALUE);

            btn.setOnAction(e -> pruefeAntwort(antwort));
            antwortBox.getChildren().add(btn);
        }

        setCenter(antwortBox);
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
