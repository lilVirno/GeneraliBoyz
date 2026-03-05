package gui;

import backend.Frage;
import backend.FragenController;
import backend.FragenRepository;
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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.List;

import backend.Spieler;

public class Startbildschirm extends Application {

    private FragenController fragenController;
    private Stage stage;
    private static final String ABSOLUTE_PATH =
            "Rollenspiel/src/resources/Designer.png";

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 600;
    private static final double SPLASH_SECONDS = 2.5;

    private static final String SOLID_BG_HEX = "#2EA3A3";

    private Scene startScene;
    private Scene themenScene;
    private Scene profilScene;

    private Spieler aktuellerSpieler;

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

        // Hier liegt die Magie: Wir fügen das Namens-Feld in das Start-Root ein
        StackPane startRoot = createStartRoot(bgImage);
        showNameInputOverlay(startRoot); // Das PopUp wird hier drübergelegt

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

    private void showNameInputOverlay(StackPane root) {
        // Dunkler Hintergrund für den Fokus
        Region blurBg = new Region();
        blurBg.setStyle("-fx-background-color: rgba(0,0,0,0.7);");

        // Die Eingabebox
        VBox inputContainer = new VBox(20);
        inputContainer.setAlignment(Pos.CENTER);
        inputContainer.setMaxSize(400, 250);
        inputContainer.setStyle("-fx-background-color: #2EA3A3; -fx-background-radius: 20; -fx-border-color: white; -fx-border-width: 2;");
        inputContainer.setPadding(new Insets(30));

        Label frage = new Label("Wie lautet dein Name?");
        frage.setStyle("-fx-font-size: 22px; -fx-text-fill: white; -fx-font-weight: bold;");

        TextField nameField = new TextField();
        nameField.setPromptText("Dein Name...");
        nameField.setStyle("-fx-font-size: 18px; -fx-background-radius: 10; -fx-padding: 10;");
        // Erlaubt das Bestätigen durch Drücken der Enter-Taste
        Button confirmBtn = new Button("Bestätigen");
        nameField.setOnAction(e -> confirmBtn.fire());
        confirmBtn.setStyle(buttonMain()); // Nutzt dein vorhandenes Button-Styling

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

    // ---------- Splash ----------
    private StackPane createSplashRoot(Image bgImage) {
        ImageView bg = new ImageView(bgImage);
        bg.setPreserveRatio(true);
        bg.setSmooth(true);

        StackPane root = new StackPane(bg);
        root.setStyle("-fx-background-color: black;");

        root.widthProperty().addListener((obs, oldVal, newVal) -> bg.setFitWidth(newVal.doubleValue()));
        root.heightProperty().addListener((obs, oldVal, newVal) -> bg.setFitHeight(newVal.doubleValue()));

        return root;
    }

    // ---------- Startscreen ----------
    private StackPane createStartRoot(Image bgImage) {
        ImageView bg = new ImageView(bgImage);
        bg.setPreserveRatio(true);
        bg.setSmooth(true);

        Label titel = new Label("Willkommen zum Lernspiel!");
        titel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button startButton = new Button("Start");
        startButton.setStyle(buttonMain());

        VBox overlayBox = new VBox(12, titel, startButton);
        overlayBox.setPadding(new Insets(16));
        overlayBox.setAlignment(Pos.CENTER);
        titel.setTranslateY(-30);
        startButton.setTranslateY(120);

        Region overlayBackground = new Region();
        overlayBackground.setStyle("-fx-background-color: rgba(0,0,0,0.28); -fx-background-radius: 12;");

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

    // ---------- Splash Ablauf ----------
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
    private VBox createThemenRoot() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.TOP_CENTER);
        vbox.setSpacing(15);
        // Padding oben etwas verringern, da der Header nun Platz einnimmt
        vbox.setPadding(new Insets(10, 0, 0, 0));

        vbox.setBackground(new Background(new BackgroundFill(
                Color.web(SOLID_BG_HEX), CornerRadii.EMPTY, Insets.EMPTY
        )));

        // --- Header mit Home- und Profil-Button ---
        AnchorPane header = new AnchorPane();
        header.setPadding(new Insets(10, 20, 0, 20));

// Home Button (Links oben)
        Button homeBtn = new Button("🏠 Home");
        homeBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;");
        AnchorPane.setLeftAnchor(homeBtn, 10.0);
        AnchorPane.setTopAnchor(homeBtn, 0.0);

// Profil Button (Rechts oben)
        Button profilBtn = new Button("👤 Profil");
        profilBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;");
        AnchorPane.setRightAnchor(profilBtn, 10.0);
        AnchorPane.setTopAnchor(profilBtn, 0.0);

        homeBtn.setOnAction(e -> {
            // Deine bestehende Home-Logik
            FadeTransition ft = new FadeTransition(Duration.millis(300), themenScene.getRoot());
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.setOnFinished(ev -> {
                stage.setScene(startScene);
                startScene.getRoot().setOpacity(0);
                FadeTransition fadeIn = new FadeTransition(Duration.millis(300), startScene.getRoot());
                fadeIn.setToValue(1.0);
                fadeIn.play();
            });
            ft.play();
        });

        profilBtn.setOnAction(e -> {
            // Erstellt die Scene neu mit den aktuellen Werten aus 'aktuellerSpieler'
            profilScene = new Scene(createProfilRoot(), WIDTH, HEIGHT);
            stage.setScene(profilScene);
        });

        header.getChildren().addAll(homeBtn, profilBtn);
// ------------------------------------------

        Label auswahlLabel = new Label("Wähle einen Themenbereich:");
        auswahlLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        // Zuerst den Header, dann das Label und die Buttons hinzufügen
        vbox.getChildren().addAll(header, auswahlLabel);

        for (Themenbereich tb : Themenbereich.values()) {
            Button btn = createThemeButton(tb.name());
            btn.setOnAction(e -> ladeFragenUndÖffne(tb));
            vbox.getChildren().add(btn);
        }

        return vbox;
    }

    // ---------- Logik aus Swing: Fragen laden ----------
    private void ladeFragenUndÖffne(Themenbereich thema) {
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
        öffneFrageGUI(fragenController.getAktuelleFrage());
    }

    // ---------- Frage-GUI öffnen (JavaFX-Version) ----------
    private void öffneFrageGUI(Frage frage) {
        VBox frageRoot = new VBox(20);
        frageRoot.setAlignment(Pos.TOP_CENTER); // Von Center auf TOP_CENTER ändern
        frageRoot.setPadding(new Insets(10, 0, 40, 0));
        frageRoot.setBackground(new Background(new BackgroundFill(Color.web(SOLID_BG_HEX), CornerRadii.EMPTY, Insets.EMPTY)));

        // Header für Abbruch
        AnchorPane header = new AnchorPane();
        header.setPadding(new Insets(10, 20, 0, 20));

        Button cancelBtn = new Button("✕ Abbrechen");
        cancelBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;");
        cancelBtn.setOnAction(e -> stage.setScene(themenScene));

        AnchorPane.setLeftAnchor(cancelBtn, 10.0);
        AnchorPane.setTopAnchor(cancelBtn, 0.0);
        header.getChildren().add(cancelBtn);

        // Frage-Inhalt (muss jetzt in eine eigene Box, damit er zentriert bleibt)
        VBox content = new VBox(20);
        content.setAlignment(Pos.CENTER);
        VBox.setVgrow(content, Priority.ALWAYS);

        Pane spezifischesPanel;
        switch (frage.getFragenkategorie()) {
            case MULTIPLE_CHOICE -> spezifischesPanel = new MultipleChoiceGUI(frage, this);
            case WAHR_FALSCH     -> spezifischesPanel = new WahrFalschGUI(frage, this);
            case LUECKENTEXT     -> spezifischesPanel = new LueckentextGUI(frage, this);
            default -> { return; }
        }
        spezifischesPanel.setStyle("-fx-background-color: transparent;");
        content.getChildren().add(spezifischesPanel);

        frageRoot.getChildren().addAll(header, content);
        stage.setScene(new Scene(frageRoot, WIDTH, HEIGHT));
    }

    public void oeffneNaechsteFrageOderBeenden() {
        if (fragenController.hatNaechsteFrage()) {
            fragenController.naechsteFrage();
            öffneFrageGUI(fragenController.getAktuelleFrage());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Alle Fragen beantwortet!");
            alert.showAndWait();

            // zurück zum Themenbildschirm
            stage.setScene(themenScene);
        }
    }

    private VBox createProfilRoot() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10, 20, 0, 20));
        root.setBackground(new Background(new BackgroundFill(
                Color.web(SOLID_BG_HEX), CornerRadii.EMPTY, Insets.EMPTY
        )));

        // --- Header ---
        AnchorPane header = new AnchorPane();
        header.setPadding(new Insets(10, 20, 0, 20));

        Button backBtn = new Button("← Zurück");
        backBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;");
        backBtn.setOnAction(e -> stage.setScene(themenScene));

        AnchorPane.setLeftAnchor(backBtn, 10.0);
        AnchorPane.setTopAnchor(backBtn, 0.0);
        header.getChildren().add(backBtn);

        // --- Titel ---
        Label titel = new Label("Hallo, " + aktuellerSpieler.getName());
        titel.setStyle("-fx-font-size: 26px; -fx-font-weight: bold; -fx-text-fill: white;");

        // --- Allgemeine Stats (Level & Punkte) ---
        HBox generalStats = new HBox(40);
        generalStats.setAlignment(Pos.CENTER);
        generalStats.setStyle("-fx-background-color: rgba(0,0,0,0.15); -fx-padding: 10; -fx-background-radius: 10;");

        Label levelLabel = new Label("Rang: " + aktuellerSpieler.getLevel());
        Label punkteLabel = new Label("Punkte: " + aktuellerSpieler.getPunktekonto());
        levelLabel.setStyle("-fx-text-fill: #f1c40f; -fx-font-weight: bold; -fx-font-size: 16px;");
        punkteLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 16px;");

        generalStats.getChildren().addAll(levelLabel, punkteLabel);

        // --- Themen-Fortschritte (Grid) ---
        GridPane progressGrid = new GridPane();
        progressGrid.setHgap(15);
        progressGrid.setVgap(8);
        progressGrid.setAlignment(Pos.CENTER);
        progressGrid.setMaxWidth(500);
        progressGrid.setPadding(new Insets(10));
        progressGrid.setStyle("-fx-background-color: rgba(255,255,255,0.05); -fx-background-radius: 10;");

        // Wir erstellen eine kleine Hilfsfunktion oder schleifen durch die 5 Bereiche
        addProgressRow(progressGrid, "SQL", aktuellerSpieler.getFortschrittSQL(), 0);
        addProgressRow(progressGrid, "UML", aktuellerSpieler.getFortschrittUML(), 1);
        addProgressRow(progressGrid, "DATENBANK", aktuellerSpieler.getFortschrittDATENBANK(), 2);
        addProgressRow(progressGrid, "PSEUDOCODE", aktuellerSpieler.getFortschrittPSEUDOCODE(), 3);
        addProgressRow(progressGrid, "DESIGNPATTERN", aktuellerSpieler.getFortschrittDESIGNPATTERN(), 4);

        // --- Gesamtfortschritt ---
        VBox gesamtBox = new VBox(5);
        gesamtBox.setAlignment(Pos.CENTER);
        Label gesamtLabel = new Label("Gesamtfortschritt");
        gesamtLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        ProgressBar gesamtBar = new ProgressBar(aktuellerSpieler.getGesamtFortschritt());
        gesamtBar.setPrefWidth(400);
        gesamtBar.setStyle("-fx-accent: #27ae60;"); // Ein schönes Grün
        gesamtBox.getChildren().addAll(gesamtLabel, gesamtBar);

        // --- Medaillen ---
        Label medTitel = new Label("Deine Erfolge:");
        medTitel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        HBox medailenGalerie = new HBox(10);
        medailenGalerie.setAlignment(Pos.CENTER);
        medailenGalerie.setPrefHeight(70);

        for (String pfad : aktuellerSpieler.getMedallien()) {
            try {
                Image img = new Image(new File(pfad).toURI().toString());
                ImageView iv = new ImageView(img);
                iv.setFitHeight(50);
                iv.setFitWidth(50);
                iv.setPreserveRatio(true);
                medailenGalerie.getChildren().add(iv);
            } catch (Exception e) {
                // Falls ein Pfad nicht stimmt, einfach ignorieren
            }
        }

        if (medailenGalerie.getChildren().isEmpty()) {
            Label leer = new Label("Noch keine Medaillen vorhanden.");
            leer.setStyle("-fx-text-fill: rgba(255,255,255,0.4);");
            medailenGalerie.getChildren().add(leer);
        }

        root.getChildren().addAll(header, titel, generalStats, progressGrid, gesamtBox, medTitel, medailenGalerie);
        return root;
    }

    // Hilfsmethode für die Zeilen im Grid
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
    private String buttonMain() {
        return "-fx-font-size: 20px;"
                + "-fx-background-color: #3498db;"
                + "-fx-text-fill: white;"
                + "-fx-padding: 10px 44px;"
                + "-fx-background-radius: 10;";
    }

    private Button createThemeButton(String text) {
        Button btn = new Button(text);
        btn.setStyle(
                "-fx-font-size: 18px;"
                        + "-fx-background-color: rgba(255,255,255,0.90);"
                        + "-fx-text-fill: #1f2937;"
                        + "-fx-padding: 10px 20px;"
                        + "-fx-background-radius: 10;"
                        + "-fx-border-radius: 10;"
                        + "-fx-border-color: rgba(255,255,255,0.35);"
                        + "-fx-border-width: 2;"
        );

        btn.setOnMouseEntered(e ->
                btn.setStyle(
                        "-fx-font-size: 18px;"
                                + "-fx-background-color: rgba(255,255,255,0.98);"
                                + "-fx-text-fill: #111827;"
                                + "-fx-padding: 10px 20px;"
                                + "-fx-background-radius: 10;"
                                + "-fx-border-radius: 10;"
                                + "-fx-border-color: rgba(255,255,255,0.6);"
                                + "-fx-border-width: 2;"
                )
        );

        btn.setOnMouseExited(e ->
                btn.setStyle(
                        "-fx-font-size: 18px;"
                                + "-fx-background-color: rgba(255,255,255,0.90);"
                                + "-fx-text-fill: #1f2937;"
                                + "-fx-padding: 10px 20px;"
                                + "-fx-background-radius: 10;"
                                + "-fx-border-radius: 10;"
                                + "-fx-border-color: rgba(255,255,255,0.35);"
                                + "-fx-border-width: 2;"
                )
        );

        return btn;
    }

    public static void main(String[] args) {
        launch();
    }
}
