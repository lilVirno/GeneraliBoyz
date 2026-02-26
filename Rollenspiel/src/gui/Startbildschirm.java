package gui;

import backend.Frage;
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

public class Startbildschirm extends Application {

    private static final String ABSOLUTE_PATH = "Rollenspiel/src/resources/Designer.png";
    private static final int WIDTH = 700;
    private static final int HEIGHT = 450;
    private static final double SPLASH_SECONDS = 2.5;
    private static final String SOLID_BG_HEX = "#2EA3A3";

    private Scene startScene;
    private Scene themenScene;
    private Scene profilScene; // Neu: Szene für das Profil

    @Override
    public void start(Stage stage) {
        Image bgImage = new Image(
                new File(ABSOLUTE_PATH).toURI().toString(),
                WIDTH, HEIGHT, true, true
        );

        StackPane splashRoot = createSplashRoot(bgImage);
        Scene splashScene = new Scene(splashRoot, WIDTH, HEIGHT, Color.BLACK);

        StackPane startRoot = createStartRoot(bgImage);
        startScene = new Scene(startRoot, WIDTH, HEIGHT);

        VBox themenVBox = createThemenRoot();
        themenScene = new Scene(themenVBox, WIDTH, HEIGHT);

        // Neu: Profilseite initialisieren
        VBox profilVBox = createProfilRoot();
        profilScene = new Scene(profilVBox, WIDTH, HEIGHT);

        stage.setTitle("Gamification – Lernspiel");
        stage.setScene(splashScene);
        stage.show();

        runSplashSequence(stage, splashScene, startScene);
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
        titel.setTranslateY(-10);
        startButton.setTranslateY(100);

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
        vbox.setPadding(new Insets(10, 0, 0, 0));
        vbox.setBackground(new Background(new BackgroundFill(
                Color.web(SOLID_BG_HEX), CornerRadii.EMPTY, Insets.EMPTY
        )));

        // --- Header mit Home- und Profil-Button ---
        AnchorPane header = new AnchorPane();
        header.setPadding(new Insets(0, 20, 0, 20));

        Button homeBtn = new Button("🏠 Home");
        homeBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;");

        // NEU: Profil Button
        Button profilBtn = new Button("👤 Profil");
        profilBtn.setStyle("-fx-background-color: rgba(255,255,255,0.2); -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 5; -fx-cursor: hand;");

        // Positionierung
        AnchorPane.setTopAnchor(homeBtn, 10.0);
        AnchorPane.setRightAnchor(homeBtn, 10.0);

        AnchorPane.setTopAnchor(profilBtn, 10.0);
        AnchorPane.setRightAnchor(profilBtn, 100.0); // Etwas links vom Home-Button

        homeBtn.setOnAction(e -> switchSceneWithFade(themenScene, startScene));

        // Aktion für Profil-Button
        profilBtn.setOnAction(e -> switchSceneWithFade(themenScene, profilScene));

        header.getChildren().addAll(homeBtn, profilBtn);
        // ------------------------------

        Label auswahlLabel = new Label("Wähle einen Themenbereich:");
        auswahlLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        vbox.getChildren().addAll(header, auswahlLabel);

        for (Themenbereich tb : Themenbereich.values()) {
            Button btn = createThemeButton(tb.name());
            btn.setOnAction(e -> ladeFragenUndÖffne(tb));
            vbox.getChildren().add(btn);
        }

        return vbox;
    }

    // ---------- NEU: Profil-Bildschirm (Leer) ----------
    private VBox createProfilRoot() {
        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);
        vbox.setBackground(new Background(new BackgroundFill(
                Color.web(SOLID_BG_HEX), CornerRadii.EMPTY, Insets.EMPTY
        )));

        Label info = new Label("Dein Profil");
        info.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        Label placeholder = new Label("(Hier werden bald deine Statistiken angezeigt)");
        placeholder.setStyle("-fx-font-size: 16px; -fx-text-fill: white; -fx-opacity: 0.7;");

        Button backBtn = new Button("← Zurück zur Auswahl");
        backBtn.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; -fx-cursor: hand;");
        backBtn.setOnAction(e -> switchSceneWithFade(profilScene, themenScene));

        vbox.getChildren().addAll(info, placeholder, backBtn);
        return vbox;
    }

    // Hilfsmethode für den Szenenwechsel mit Fade
    private void switchSceneWithFade(Scene current, Scene next) {
        FadeTransition ft = new FadeTransition(Duration.millis(300), current.getRoot());
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setOnFinished(ev -> {
            Stage stage = (Stage) current.getWindow();
            stage.setScene(next);
            next.getRoot().setOpacity(0);
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), next.getRoot());
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        ft.play();
    }

    // ---------- Logik aus Swing: Fragen laden ----------
    private void ladeFragenUndÖffne(Themenbereich thema) {
        List<Frage> fragen = FragenRepository.getAlleFragen().stream()
                .filter(f -> f.getThemenbereich() == thema)
                .toList();

        if (fragen.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Keine Fragen für dieses Thema gefunden.");
            alert.showAndWait();
            return;
        }
        öffneFrageGUI(fragen.get(0));
    }

    private void öffneFrageGUI(Frage frage) {
        VBox frageRoot = new VBox(20);
        frageRoot.setAlignment(Pos.CENTER);
        frageRoot.setPadding(new Insets(40));
        frageRoot.setBackground(new Background(new BackgroundFill(
                Color.web(SOLID_BG_HEX), CornerRadii.EMPTY, Insets.EMPTY
        )));

        Pane spezifischesPanel;
        switch (frage.getFragenkategorie()) {
            case MULTIPLE_CHOICE -> spezifischesPanel = new MultipleChoiceGUI(frage);
            case WAHR_FALSCH     -> spezifischesPanel = new WahrFalschGUI(frage);
            case LUECKENTEXT     -> spezifischesPanel = new LueckentextGUI(frage);
            default -> { return; }
        }

        spezifischesPanel.setStyle("-fx-background-color: transparent;");

        Button backBtn = new Button("← Zurück");
        backBtn.setStyle("-fx-background-color: rgba(255,255,255,0.15); -fx-text-fill: white; -fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            Stage stage = (Stage) frageRoot.getScene().getWindow();
            stage.setScene(themenScene);
        });

        frageRoot.getChildren().addAll(backBtn, spezifischesPanel);
        VBox.setVgrow(spezifischesPanel, Priority.ALWAYS);

        Stage stage = (Stage) themenScene.getWindow();
        Scene scene = new Scene(frageRoot, WIDTH, HEIGHT);
        stage.setScene(scene);
    }

    // ---------- Styling ----------
    private String buttonMain() {
        return "-fx-font-size: 20px;" + "-fx-background-color: #3498db;" + "-fx-text-fill: white;" + "-fx-padding: 10px 44px;" + "-fx-background-radius: 10;";
    }

    private Button createThemeButton(String text) {
        Button btn = new Button(text);
        String idle = "-fx-font-size: 18px; -fx-background-color: rgba(255,255,255,0.90); -fx-text-fill: #1f2937; -fx-padding: 10px 20px; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: rgba(255,255,255,0.35); -fx-border-width: 2;";
        String hover = "-fx-font-size: 18px; -fx-background-color: rgba(255,255,255,0.98); -fx-text-fill: #111827; -fx-padding: 10px 20px; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: rgba(255,255,255,0.6); -fx-border-width: 2;";

        btn.setStyle(idle);
        btn.setOnMouseEntered(e -> btn.setStyle(hover));
        btn.setOnMouseExited(e -> btn.setStyle(idle));
        return btn;
    }

    public static void main(String[] args) {
        launch();
    }
}