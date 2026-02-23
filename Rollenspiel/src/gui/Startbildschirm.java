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

    private static final String ABSOLUTE_PATH =
            "Rollenspiel/src/resources/Designer.png";

    private static final int WIDTH = 700;
    private static final int HEIGHT = 450;
    private static final double SPLASH_SECONDS = 2.5;

    private static final String SOLID_BG_HEX = "#2EA3A3";

    private Scene startScene;
    private Scene themenScene;

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
        vbox.setPadding(new Insets(30, 0, 0, 0));

        vbox.setBackground(new Background(new BackgroundFill(
                Color.web(SOLID_BG_HEX), CornerRadii.EMPTY, Insets.EMPTY
        )));

        Label auswahlLabel = new Label("Wähle einen Themenbereich:");
        auswahlLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        vbox.getChildren().add(auswahlLabel);

        for (Themenbereich tb : Themenbereich.values()) {
            Button btn = createThemeButton(tb.name());
            btn.setOnAction(e -> ladeFragenUndÖffne(tb));
            vbox.getChildren().add(btn);
        }

        return vbox;
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

    // ---------- Frage-GUI öffnen (JavaFX-Version) ----------
    private void öffneFrageGUI(Frage frage) {

        Pane panel;

        switch (frage.getFragenkategorie()) {
            case MULTIPLE_CHOICE -> panel = new MultipleChoiceGUI(frage);
            case WAHR_FALSCH -> panel = new WahrFalschGUI(frage);
            case LUECKENTEXT -> panel = new LueckentextGUI(frage);
            default -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Unbekannte Fragenkategorie!");
                alert.showAndWait();
                return;
            }
        }

        Stage frageStage = new Stage();
        frageStage.setTitle("Frage");
        frageStage.setScene(new Scene(panel, 600, 400));
        frageStage.show();
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
