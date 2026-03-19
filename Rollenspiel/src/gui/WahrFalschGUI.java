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

/**
 * GUI-Komponente zur Darstellung einer Wahr/Falsch-Frage.
 * Zeigt die Frage als Text und darunter zwei Buttons (Antwortmöglichkeiten),
 * wertet die Auswahl aus und aktualisiert den Spielerfortschritt.
 */
public class WahrFalschGUI extends BorderPane {

    /**
     * Referenz auf den Startbildschirm, um später zur nächsten Frage oder
     * zur Themenübersicht navigieren zu können.
     */
    private final Startbildschirm main;

    /**
     * Die aktuell dargestellte Frage.
     */
    private final Frage aktuelleFrage;

    /**
     * Spieler, der diese Frage beantwortet.
     */
    private final Spieler aktuellerSpieler;

    /**
     * Konstruktor für die Wahr/Falsch-Oberfläche.
     *
     * @param frage   die anzuzeigende Frage
     * @param main    Referenz zur Hauptnavigation
     * @param spieler der aktuelle Spieler
     */
    public WahrFalschGUI(Frage frage, Startbildschirm main, Spieler spieler) {

        this.main = main;
        this.aktuelleFrage = frage;
        this.aktuellerSpieler = spieler;

        Label frageLabel = new Label(frage.getFrage());
        frageLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");
        setTop(frageLabel);
        BorderPane.setAlignment(frageLabel, Pos.CENTER);
        BorderPane.setMargin(frageLabel, new Insets(20, 0, 20, 0));

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));

        /**
         * Für jede Antwortoption (Wahr/Falsch) wird ein Button erzeugt.
         * Das Antwortobjekt wird in der Aktion direkt übergeben.
         */
        for (Antwort antwort : frage.getAntworten()) {
            Button btn = new Button(antwort.getAntwort());
            btn.setStyle("-fx-font-size: 20px; -fx-background-color: #Ffffff; -fx-text-fill: black; -fx-padding: 10px 44px; -fx-background-radius: 10;");
            btn.setOnAction(e -> pruefeAntwort(antwort));
            buttonBox.getChildren().add(btn);
            btn.setFocusTraversable(false);
        }

        setCenter(buttonBox);
    }

    /**
     * Prüft, ob die ausgewählte Antwort korrekt ist.
     * Zeigt dazu ein Info-Popup an, aktualisiert bei Erfolg den Spieler,
     * und leitet anschließend (automatisch oder manuell) zur nächsten Frage weiter.
     *
     * @param antwort die vom Spieler ausgewählte Antwort
     */
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

        // Weiterleitung bei manuellem Schließen des Pop-ups
        alert.setOnHidden(event -> {
            // verhindert doppeltes Ausführen
            delay.stop();
            main.oeffneNaechsteFrageOderBeenden();
        });
    }
}
