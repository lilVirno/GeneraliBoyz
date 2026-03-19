package gui;

import backend.*;
import enums.Themenbereich;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

/**
 * Hauptklasse der JavaFX-Anwendung. Verantwortlich für:
 * <ul>
 *   <li>Startbildschirm mit Splash-Screen</li>
 *   <li>Nameingabe beim ersten Start</li>
 *   <li>Navigation zu Themenauswahl und Profil</li>
 *   <li>Erstellen und Initialisieren des Spielers</li>
 * </ul>
 */
public class Startbildschirm extends Application {

    /**
     * Controller, der Fragen verwaltet.
     */
    private FragenController fragenController;

    /**
     * Hauptfenster der Anwendung.
     */
    private Stage stage;

    /**
     * Absoluter Pfad zum Hintergrundbild.
     */
    private static final String ABSOLUTE_PATH =
            "Rollenspiel/src/resources/Designer.png";

    /**
     * Breite des Fensters.
     */
    private static final int WIDTH = 1000;

    /**
     * Höhe des Fensters.
     */
    private static final int HEIGHT = 600;

    /**
     * Dauer des Splash Screens in Sekunden.
     */
    private static final double SPLASH_SECONDS = 2.5;

    /**
     * Primärfarbe für GUI-Elemente.
     */
//    private static final String SOLID_BG_HEX = "#2EA3A3";

    /**
     * Startszene.
     */
    private Scene startScene;

    /**
     * Themenszene.
     */
    private Scene themenScene;

    /**
     * Profilseite (wird nach Eingabe des Namens erzeugt).
     */
    private Scene profilScene;

    /**
     * Aktuell eingeloggter Spieler.
     */
    private Spieler aktuellerSpieler;

    /**
     * Einstiegspunkt der JavaFX-Anwendung.
     *
     * @param stage Hauptfenster
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;

        // Spieler erst mal "leer" anlegen
        this.aktuellerSpieler = new Spieler("");

        Image bgImage = new Image(
                new File(ABSOLUTE_PATH).toURI().toString(),
                WIDTH, HEIGHT, true, true
        );

        StackPane splashRoot = createSplashRoot(bgImage);
        Scene splashScene = new Scene(splashRoot, WIDTH, HEIGHT, Color.BLACK);

        // Startbildschirm erstellen
        StackPane startRoot = createStartRoot(bgImage);
        showNameInputOverlay(startRoot);

        startScene = new Scene(startRoot, WIDTH, HEIGHT);

        // ... Rest wie gehabt (ThemenScene, ProfilScene)
        VBox themenVBox = createThemenRoot();
        themenScene = new Scene(themenVBox, WIDTH, HEIGHT);

        // Wichtig: ProfilScene hier noch nicht final erstellen,
        // da der Name erst später kommt!

        stage.setTitle("Gamification – Lernspiel");
        stage.setScene(splashScene);
        stage.show();

        runSplashSequence(stage, splashScene, startScene);
    }

    /**
     * Zeigt ein Overlay zur Nameingabe, bevor das Spiel startet.
     *
     * @param root Startbildschirm-Root
     */

    private void showNameInputOverlay(StackPane root) {
        // Dunkler Hintergrund für den Fokus
        Region blurBg = new Region();
        blurBg.setStyle(UIStyles.OVERLAY_BLUR);

        // Die Eingabebox
        VBox inputContainer = new VBox(20);
        inputContainer.setAlignment(Pos.CENTER);
        inputContainer.setMaxSize(400, 250);
        inputContainer.setStyle(UIStyles.POPUP_CONTAINER);
        inputContainer.setPadding(new Insets(30));

        Label frage = new Label("Wie lautet dein Name?");
        frage.setStyle(UIStyles.FRAGE);

        TextField nameField = new TextField();
        nameField.setPromptText("Dein Name...");
        nameField.setStyle(UIStyles.TEXT_FIELD);
        // Erlaubt das Bestätigen durch Drücken der Enter-Taste
        Button confirmBtn = new Button("Bestätigen");
        nameField.setOnAction(e -> confirmBtn.fire());
        confirmBtn.setStyle(UIStyles.BUTTON_MAIN); // Nutzt dein vorhandenes Button-Styling

        inputContainer.getChildren().addAll(frage, nameField, confirmBtn);

        // Alles zum Root hinzufügen
        root.getChildren().addAll(blurBg, inputContainer);

        // Logik beim Klicken
        confirmBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                aktuellerSpieler.setName(name);

                // Profilseite erst jetzt mit dem richtigen Namen bauen
                profilScene = new Scene(createProfilRoot(), WIDTH, HEIGHT);

                // Animation: Overlay ausfaden
                FadeTransition ft = new FadeTransition(Duration.millis(400), blurBg);
                FadeTransition ft2 = new FadeTransition(Duration.millis(400), inputContainer);
                ft.setToValue(0);
                ft2.setToValue(0);
                ft2.setOnFinished(ev -> root.getChildren().removeAll(blurBg, inputContainer));
                ft.play();
                ft2.play();
            }
        });
    }

    /**
     * Erstellt das Root für den Splash Screen.
     *
     * @param bgImage Hintergrundbild
     * @return fertiger Splash-Root
     */
    private StackPane createSplashRoot(Image bgImage) {
        ImageView bg = new ImageView(bgImage);
        bg.setPreserveRatio(true);
        bg.setSmooth(true);

        StackPane root = new StackPane(bg);
        root.setStyle(UIStyles.BLACK);

        root.widthProperty().addListener((obs, oldVal, newVal) -> bg.setFitWidth(newVal.doubleValue()));
        root.heightProperty().addListener((obs, oldVal, newVal) -> bg.setFitHeight(newVal.doubleValue()));

        return root;
    }

    /**
     * Erstellt den Startbildschirm mit Titel und Start-Button.
     *
     * @param bgImage Hintergrundbild
     * @return fertiges Startscreen-Root
     */
    private StackPane createStartRoot(Image bgImage) {
        ImageView bg = new ImageView(bgImage);
        bg.setPreserveRatio(true);
        bg.setSmooth(true);

        Label titel = new Label("Willkommen zum Lernspiel!");
        titel.setStyle(UIStyles.LABEL_TITLE);

        Button startButton = new Button("Start");
        startButton.setStyle(UIStyles.BUTTON_MAIN);

        VBox overlayBox = new VBox(12, titel, startButton);
        overlayBox.setPadding(new Insets(16));
        overlayBox.setAlignment(Pos.CENTER);
        titel.setTranslateY(-30);
        startButton.setTranslateY(120);

        Region overlayBackground = new Region();
        overlayBackground.setStyle(UIStyles.OVERLAY_BACKGROUND);

        StackPane overlay = new StackPane(overlayBackground, overlayBox);
        overlay.setMaxWidth(420);

        StackPane root = new StackPane(bg);
        StackPane.setAlignment(overlay, Pos.BOTTOM_CENTER);
        StackPane.setMargin(overlay, new Insets(0, 0, 48, 0));
        root.getChildren().add(overlay);

        root.widthProperty().addListener((o, ov, nv) -> bg.setFitWidth(nv.doubleValue()));
        root.heightProperty().addListener((o, ov, nv) -> bg.setFitHeight(nv.doubleValue()));

        startButton.setOnAction(e -> {
            Stage stage = (Stage) root.getScene().getWindow();

            FadeTransition out = new FadeTransition(Duration.millis(300), root);
            out.setFromValue(1);
            out.setToValue(0);
            out.setOnFinished(ev -> {
                stage.setScene(themenScene);
                FadeTransition in = new FadeTransition(Duration.millis(300), themenScene.getRoot());
                in.setFromValue(0);
                in.setToValue(1);
                in.play();
            });
            out.play();
        });

        return root;
    }

    /**
     * Steuert den Ablauf der Splash-Animation:
     * kurze Pause → Fade-Out → Wechsel → Fade-In.
     *
     * @param stage       Hauptfenster
     * @param splashScene Splashszene
     * @param nextScene   Szene nach dem Splash
     */
    private void runSplashSequence(Stage stage, Scene splashScene, Scene nextScene) {
        PauseTransition pause = new PauseTransition(Duration.seconds(SPLASH_SECONDS));
        FadeTransition fadeOut = new FadeTransition(Duration.millis(600), splashScene.getRoot());
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        SequentialTransition seq = new SequentialTransition(pause, fadeOut);
        seq.setOnFinished(e -> {
            stage.setScene(nextScene);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), nextScene.getRoot());
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        seq.play();
    }

    // ---------- Themenbereiche ----------
    /**
     * Erzeugt die komplette Themenauswahl-Seite.
     * Diese Seite enthält:
     * <ul>
     *   <li>Header mit Home- und Profil-Button</li>
     *   <li>Titelbereich</li>
     *   <li>FlowPane mit modernen Themen-Kacheln</li>
     *   <li>ScrollPane für kleinere Bildschirme</li>
     * </ul>
     *
     * @return ein VBox-Root-Container für die Themenübersicht
     */
    private VBox createThemenRoot() {
        var root = new VBox();
        root.setAlignment(Pos.TOP_CENTER);
        root.setSpacing(30);
        root.setPadding(new Insets(0, 0, 40, 0)); // Padding unten für Scroll-Freiraum
        root.setBackground(new Background(new BackgroundFill(
                Color.web(UIStyles.MAIN_COLOR), CornerRadii.EMPTY, Insets.EMPTY
        )));

        // --- Header ---
        var header = new AnchorPane();
        header.setPadding(new Insets(20, 30, 10, 30));

        /**
         * Home-Button (links oben), navigiert zurück zum Startbildschirm.
         */
        Button homeBtn = new Button("🏠 Home");
        homeBtn.setFocusTraversable(false);
        homeBtn.setStyle(UIStyles.NAV_BUTTON);
        AnchorPane.setLeftAnchor(homeBtn, 10.0);
        AnchorPane.setTopAnchor(homeBtn, 0.0);

        /**
         * Profil-Button (rechts oben), navigiert zur Profilseite.
         */
        Button profilBtn = new Button("👤 Profil");
        profilBtn.setFocusTraversable(false);
        profilBtn.setStyle(UIStyles.NAV_BUTTON);
        AnchorPane.setRightAnchor(profilBtn, 10.0);
        AnchorPane.setTopAnchor(profilBtn, 0.0);

        // Navigation Logik
        homeBtn.setOnAction(_ -> {
            var ft = new FadeTransition(Duration.millis(300), root);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.setOnFinished(_ -> {
                stage.setScene(startScene);
                startScene.getRoot().setOpacity(0);
                var fadeIn = new FadeTransition(Duration.millis(300), startScene.getRoot());
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            ft.play();
        });

        profilBtn.setOnAction(_ -> {
            profilScene = new Scene(createProfilRoot(), WIDTH, HEIGHT);
            stage.setScene(profilScene);
        });

        header.getChildren().addAll(homeBtn, profilBtn);

        // --- Titel-Bereich ---
        var titleBox = new VBox(10);
        titleBox.setAlignment(Pos.CENTER);
        var auswahlLabel = new Label("Wähle einen Themenbereich");
        auswahlLabel.setStyle(UIStyles.THEMEN_LABEL);
        var subLabel = new Label("Meistere alle Fragen, um neue Medaillen zu verdienen!");
        subLabel.setStyle(UIStyles.LABEL_SUBTITLE);
        titleBox.getChildren().addAll(auswahlLabel, subLabel);

        // --- Themen-Grid (FlowPane mit Kacheln) ---
        var flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setHgap(25);
        flowPane.setVgap(25);
        flowPane.setPadding(new Insets(20, 40, 20, 40));

        // Kacheln für alle Themenbereiche erzeugen
        for (var tb : Themenbereich.values()) {
            var btn = createThemeTile(tb);
            flowPane.getChildren().add(btn);
        }

        // ScrollPane, um auch auf kleinen Displays alle Kacheln sehen zu können
        var scrollPane = new ScrollPane(flowPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle(UIStyles.SCROLL_PANE);
        scrollPane.setPannable(true); // Erlaubt "Ziehen" mit der Maus wie am Handy

        root.getChildren().addAll(header, titleBox, scrollPane);
        return root;
    }

    /**
     * Erstellt eine einzelne Themen-Kachel als modernen Button
     * mit Glassmorphism-Optik und Hover-Effekten.
     * Beim Anklicken wird der entsprechende Themenbereich geladen.
     *
     * @param tb Themenbereich (Enum)
     * @return fertiger Themen-Button
     */
    private Button createThemeTile(Themenbereich tb) {
        var btn = new Button(tb.getName());
        btn.setPrefSize(200, 140);
        btn.setWrapText(true);
        btn.setTextAlignment(TextAlignment.CENTER);
        btn.setFocusTraversable(false);

        // Standard-Style setzen
        btn.setStyle(UIStyles.KACHELN);

        // Hover-Effekte: Wir tauschen den kompletten Style-String gegen die Hover-Variante
        btn.setOnMouseEntered(_ -> btn.setStyle(UIStyles.KACHELN_HOVER));
        btn.setOnMouseExited(_ -> btn.setStyle(UIStyles.KACHELN));

        // Öffnet Fragen für das Thema
        btn.setOnAction(_ -> ladeFragenUndOeffne(tb));

        return btn;
    }

// ---------- Logik aus Swing: Fragen laden ----------

    /**
     * Lädt alle ungelösten Fragen zu einem bestimmten Themenbereich.
     * Falls keine Fragen übrig sind, wird eine Information angezeigt.
     * Sonst wird ein FragenController erstellt und die erste Frage geöffnet.
     *
     * @param thema Themenbereich, dessen Fragen geladen werden sollen
     */
    private void ladeFragenUndOeffne(Themenbereich thema) {
        List<Frage> fragen = FragenRepository.getUngeloesteFragen(thema);

        if (fragen.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Glückwunsch!");
            alert.setHeaderText(null);
            alert.setContentText("Du hast bereits alle Fragen zu " + thema + " beantwortet!");
            alert.showAndWait();
            return;
        }

        this.fragenController = new FragenController(fragen);
        oeffneFrageGUI(fragenController.getAktuelleFrage());
    }


// ---------- Frage-GUI öffnen (JavaFX-Version) ----------

    /**
     * Öffnet die GUI für die jeweilige Frage abhängig von ihrer Kategorie.
     * Unterstützte Kategorie-Views:
     * <ul>
     *   <li>Multiple Choice</li>
     *   <li>Wahr/Falsch</li>
     *   <li>Lückentext</li>
     * </ul>
     *
     * @param frage Die Frage, deren GUI geöffnet werden soll
     */
    private void oeffneFrageGUI(Frage frage) {
        VBox frageRoot = new VBox(20);
        frageRoot.setAlignment(Pos.TOP_CENTER);
        frageRoot.setPadding(new Insets(10, 0, 40, 0));
        frageRoot.setBackground(new Background(new BackgroundFill(Color.web(UIStyles.MAIN_COLOR), CornerRadii.EMPTY, Insets.EMPTY)));

        // Header für Abbruch
        AnchorPane header = new AnchorPane();
        header.setPadding(new Insets(10, 20, 0, 20));

        Button cancelBtn = new Button("✕ Abbrechen");
        cancelBtn.setFocusTraversable(false);
        cancelBtn.setStyle(UIStyles.NAV_BUTTON);
        cancelBtn.setOnAction(e -> stage.setScene(themenScene));

        AnchorPane.setLeftAnchor(cancelBtn, 10.0);
        AnchorPane.setTopAnchor(cancelBtn, 0.0);
        header.getChildren().add(cancelBtn);

        // Inhalt des Fragepanels
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        VBox.setVgrow(content, Priority.ALWAYS);

        Pane spezifischesPanel;
        switch (frage.getFragenkategorie()) {
            case MULTIPLE_CHOICE -> spezifischesPanel = new MultipleChoiceGUI(frage, this, aktuellerSpieler);
            case WAHR_FALSCH     -> spezifischesPanel = new WahrFalschGUI(frage, this, aktuellerSpieler);
            case LUECKENTEXT     -> spezifischesPanel = new LueckentextGUI(frage, this, aktuellerSpieler);
            default -> { return; }
        }
        spezifischesPanel.setStyle(UIStyles.PANEL_DURCHSICHTIG);
        content.getChildren().add(spezifischesPanel);

        frageRoot.getChildren().addAll(header, content);
        stage.setScene(new Scene(frageRoot, WIDTH, HEIGHT));
    }

    /**
     * Öffnet die nächste Frage, falls vorhanden.
     * Falls alle Fragen beantwortet wurden, erscheint eine Meldung und der Nutzer
     * wird zurück zur Themenübersicht geleitet.
     */
    public void oeffneNaechsteFrageOderBeenden() {
        if (fragenController.hatNaechsteFrage()) {
            fragenController.naechsteFrage();
            oeffneFrageGUI(fragenController.getAktuelleFrage());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Alle Fragen beantwortet!");
            alert.showAndWait();

            // zurück zum Themenbildschirm
            stage.setScene(themenScene);
        }
    }


    /**
     * Erstellt das Profilfenster des Spielers inkl.:
     * <ul>
     *   <li>Name</li>
     *   <li>Rang & Punktestand</li>
     *   <li>Fortschritt in allen Themen</li>
     *   <li>Gesamtfortschritt</li>
     *   <li>Medaillen</li>
     * </ul>
     *
     * @return Layout für die Profilseite
     */
    private VBox createProfilRoot() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10, 20, 0, 20));
        root.setBackground(new Background(new BackgroundFill(
                Color.web(UIStyles.MAIN_COLOR), CornerRadii.EMPTY, Insets.EMPTY
        )));

        // Header
        AnchorPane header = new AnchorPane();
        header.setPadding(new Insets(10, 20, 0, 20));

        Button backBtn = new Button("← Zurück");
        backBtn.setFocusTraversable(false);
        backBtn.setStyle(UIStyles.NAV_BUTTON);
        backBtn.setOnAction(e -> stage.setScene(themenScene));

        AnchorPane.setLeftAnchor(backBtn, 10.0);
        AnchorPane.setTopAnchor(backBtn, 0.0);
        header.getChildren().add(backBtn);

        // Titel
        Label titel = new Label("Hallo, " + aktuellerSpieler.getName());
        titel.setStyle(UIStyles.PROFIL_TITEL);

        // Stats (Level + Punkte)
        HBox generalStats = new HBox(40);
        generalStats.setAlignment(Pos.CENTER);
        generalStats.setStyle(UIStyles.PROFIL_STATS);

        Label levelLabel = new Label("Rang: " + aktuellerSpieler.getLevel());
        Label punkteLabel = new Label("Punkte: " + aktuellerSpieler.getPunktekonto());
        levelLabel.setStyle(UIStyles.PROFIL_LEVEL_LABEL);
        punkteLabel.setStyle(UIStyles.PROFIL_PUNKTE_LABEL);

        generalStats.getChildren().addAll(levelLabel, punkteLabel);

        // Fortschritte (Grid)
        GridPane progressGrid = new GridPane();
        progressGrid.setHgap(15);
        progressGrid.setVgap(8);
        progressGrid.setAlignment(Pos.CENTER);
        progressGrid.setMaxWidth(500);
        progressGrid.setPadding(new Insets(10));
        progressGrid.setStyle(UIStyles.PROFIL_BALKEN);

        addProgressRow(progressGrid, "SQL", aktuellerSpieler.getFortschrittSQL(), 0);
        addProgressRow(progressGrid, "UML", aktuellerSpieler.getFortschrittUML(), 1);
        addProgressRow(progressGrid, "DATENBANK", aktuellerSpieler.getFortschrittDATENBANK(), 2);
        addProgressRow(progressGrid, "PSEUDOCODE", aktuellerSpieler.getFortschrittPSEUDOCODE(), 3);
        addProgressRow(progressGrid, "RECHT", aktuellerSpieler.getFortschrittRECHT(), 4);
        addProgressRow(progressGrid, "WIRTSCHAFT", aktuellerSpieler.getFortschrittWIRTSCHAFT(), 5);
        addProgressRow(progressGrid, "MASCHINELLES LERNEN", aktuellerSpieler.getFortschrittMASCHINELLES_LEARNING(), 6);

        // Gesamtfortschritt
        VBox gesamtBox = new VBox(5);
        gesamtBox.setAlignment(Pos.CENTER);
        Label gesamtLabel = new Label("Gesamtfortschritt");
        gesamtLabel.setStyle(UIStyles.PROFIL_GESAMT_BALKEN);
        ProgressBar gesamtBar = new ProgressBar(aktuellerSpieler.getGesamtFortschritt());
        gesamtBar.setPrefWidth(400);
        gesamtBar.setStyle("-fx-accent: #27ae60;"); // Ein schönes Grün
        gesamtBox.getChildren().addAll(gesamtLabel, gesamtBar);

        // Medaillenanzeige
        Label medTitel = new Label("Deine Erfolge:");
        medTitel.setStyle(UIStyles.PROFIL_MED_TITEL);

        HBox medailenGalerie = new HBox(15);
        medailenGalerie.setAlignment(Pos.CENTER);
        medailenGalerie.setPrefHeight(120);

        for (String pfad : aktuellerSpieler.getMedallien()) {
            try {
                Image img = new Image(new File(pfad).toURI().toString());
                ImageView iv = new ImageView(img);
                iv.setFitHeight(100);
                iv.setFitWidth(100);
                iv.setPreserveRatio(true);
                iv.setSmooth(true);
                medailenGalerie.getChildren().add(iv);
            } catch (Exception e) {
                // Bilder, die nicht geladen werden können, ignorieren
            }
        }

        if (medailenGalerie.getChildren().isEmpty()) {
            Label leer = new Label("Noch keine Medaillen vorhanden.");
            leer.setStyle(UIStyles.PROFIL_NO_MED);
            medailenGalerie.getChildren().add(leer);
        }

        root.getChildren().addAll(header, titel, generalStats, progressGrid, gesamtBox, medTitel, medailenGalerie);
        return root;
    }

    /**
     * Fügt dem Fortschrittsraster eine Zeile aus:
     * <ul>
     *   <li>Label</li>
     *   <li>ProgressBar</li>
     *   <li>Prozentanzeige</li>
     * </ul>
     *
     * @param grid Zielraster
     * @param labelText Text links
     * @param value Fortschrittswert 0.0 – 1.0
     * @param row Tabellenzeile
     */
    private void addProgressRow(GridPane grid, String labelText, double value, int row) {
        Label lbl = new Label(labelText);
        lbl.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        ProgressBar pb = new ProgressBar(value);
        pb.setPrefWidth(250);
        pb.setStyle("-fx-accent: #3498db;"); // Blau für die Themenbereiche

        Label prozentLbl = new Label((int)(value * 100) + "%");
        prozentLbl.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        grid.add(lbl, 0, row);
        grid.add(pb, 1, row);
        grid.add(prozentLbl, 2, row);
    }


// ---------- Styling ----------

    /**
     * Standard-Button-Design für primäre Aktionen im Spiel.
     *
     * @return CSS Style-String
     */
    private String buttonMain() {
        return "-fx-font-size: 20px;"
                + "-fx-background-color: #3498db;"
                + "-fx-text-fill: white;"
                + "-fx-padding: 10px 44px;"
                + "-fx-background-radius: 10;";
    }

    public static void main(String[] args) {
        DatabaseController.setupDatabase();
        launch();
    }
}
