package gui;

import backend.Frage;
import backend.Spieler;
import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.util.Duration;

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
        pruefen.setDefaultButton(true);
        pruefen.setStyle("-fx-font-size: 20px;"
                + "-fx-background-color: #Ffffff;"
                + "-fx-text-fill: black;"
                + "-fx-padding: 10px 44px;"
                + "-fx-background-radius: 10;");

        /**
         * Eventhandler für den Prüfbutton.
         * Vergleicht die Benutzereingabe mit der korrekten Antwort,
         * zeigt ein Info-Popup und aktualisiert ggf. den Fortschritt des Spielers.
         */
        pruefen.setOnAction(e -> {
            String korrekt = frage.getKorrekteAntwort();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);

            if (eingabe.getText().trim().equalsIgnoreCase(korrekt)) {
                alert.setContentText("Richtig!");
                this.aktuelleFrage.setGeloest();
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
                this.main.oeffneNaechsteFrageOderBeenden();
            });
        });

        setBottom(pruefen);
        BorderPane.setAlignment(pruefen, Pos.CENTER);
        BorderPane.setMargin(pruefen, new Insets(20));
    }
}