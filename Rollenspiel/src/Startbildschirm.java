
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class Startbildschirm extends Application {

    // --- Konfiguration ---
    // Absoluter Pfad (Variante mit Backslashes; alternativ weiter unten die Forward-Slash-Variante)
    private static final String ABSOLUTE_PATH =
            "U:\\Documents\\workspace\\3.Jahr\\java\\Projekt\\Rollenspiel\\src\\resources\\Designer.png";

    private static final int WIDTH = 700;
    private static final int HEIGHT = 450;
    private static final double SPLASH_SECONDS = 2.5;    // Dauer Title Screen

    // Einfarbige Farbe für die Themen-Seite (an die Splash-Farbe angelehnt)
    private static final String SOLID_BG_HEX = "#2EA3A3"; // Teal; bei Bedarf anpassen

    // Szenen
    private Scene startScene;
    private Scene themenScene;

    @Override
    public void start(Stage stage) {

        // ---------- Bild laden (einmal) ----------
        Image bgImage = new Image(
                new File(ABSOLUTE_PATH).toURI().toString(),
                WIDTH, HEIGHT, true, true
        );

        // ---------- TITLE SCREEN (nur Bild) ----------
        StackPane splashRoot = createSplashRoot(bgImage);
        Scene splashScene = new Scene(splashRoot, WIDTH, HEIGHT, Color.BLACK);

        // ---------- START-SCREEN (Bild + Text/Buttons unten) ----------
        StackPane startRoot = createStartRoot(bgImage);
        startScene = new Scene(startRoot, WIDTH, HEIGHT);

        // ---------- THEMEN-SEITE (einfarbig) ----------
        VBox themenVBox = createThemenRoot();
        themenScene = new Scene(themenVBox, WIDTH, HEIGHT);

        // ---------- Anzeigen: zuerst Splash ----------
        stage.setTitle("Gamification – Lernspiel");
        stage.setScene(splashScene);
        stage.show();

        // ---------- Ablauf: Splash -> Start ----------
        runSplashSequence(stage, splashScene, startScene);
    }

    // ---------- Splash-Root: volles Bild, keine Overlays ----------
    private StackPane createSplashRoot(Image bgImage) {
        ImageView bg = new ImageView(bgImage);
        bg.setPreserveRatio(true);
        bg.setSmooth(true);

        StackPane root = new StackPane(bg);
        root.setStyle("-fx-background-color: black;");

        // Dynamische Anpassung (neue Werte verwenden)
        root.widthProperty().addListener((obs, oldVal, newVal) -> bg.setFitWidth(newVal.doubleValue()));
        root.heightProperty().addListener((obs, oldVal, newVal) -> bg.setFitHeight(newVal.doubleValue()));
        return root;
    }

    // ---------- Start-Screen: Bild + Overlay unten (Logo bleibt frei) ----------
    private StackPane createStartRoot(Image bgImage) {
        // Hintergrundbild
        ImageView bg = new ImageView(bgImage);
        bg.setPreserveRatio(true);
        bg.setSmooth(true);

        // Overlay-Container (unten)
        Label titel = new Label("Willkommen zum Lernspiel!");
        titel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button startButton = new Button("Start");
        startButton.setStyle(buttonMain());

        VBox overlayBox = new VBox(12, titel, startButton);
        overlayBox.setPadding(new Insets(16));
        overlayBox.setAlignment(Pos.CENTER);
        titel.setTranslateY(-10);
        startButton.setTranslateY(100);

        // Halbtransparenter Hintergrund für bessere Lesbarkeit
        Region overlayBackground = new Region();
        overlayBackground.setStyle("-fx-background-color: rgba(0,0,0,0.28); -fx-background-radius: 12;");
        overlayBackground.setPickOnBounds(false);

        StackPane overlay = new StackPane(overlayBackground, overlayBox);
        overlay.setMaxWidth(420);  // Panel-Breite begrenzen
        overlay.setPadding(new Insets(10));

        // Alles in einen StackPane: Bild hinten, Overlay unten (Logo bleibt meist mittig frei)
        StackPane root = new StackPane(bg);
        StackPane.setAlignment(overlay, Pos.BOTTOM_CENTER);
        StackPane.setMargin(overlay, new Insets(0, 0, 48, 0)); // Abstand vom unteren Rand
        root.getChildren().add(overlay);

        // Bild dynamisch anpassen
        root.widthProperty().addListener((o, ov, nv) -> bg.setFitWidth(nv.doubleValue()));
        root.heightProperty().addListener((o, ov, nv) -> bg.setFitHeight(nv.doubleValue()));

        // Klick auf Start -> einfarbige Themen-Seite
        startButton.setOnAction(e -> {
            Stage stage = (Stage) root.getScene().getWindow();
            // kleiner Fade für den Wechsel
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

    // ---------- Ablauf/Animation Splash ----------
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

    // ---------- Themen-Seite: einfarbiger Hintergrund ----------
    private VBox createThemenRoot() {
        VBox themenVBox = new VBox();
        themenVBox.setAlignment(Pos.TOP_CENTER);
        themenVBox.setSpacing(15);
        themenVBox.setPadding(new Insets(30, 0, 0, 0));

        // Einfarbiger Hintergrund in der Startscreen-Farbe
        themenVBox.setBackground(new Background(new BackgroundFill(
                Color.web(SOLID_BG_HEX), CornerRadii.EMPTY, Insets.EMPTY
        )));

        Label auswahlLabel = new Label("Wähle einen Themenbereich:");
        auswahlLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button thema1 = createThemeButton("Themenbereich 1");
        Button thema2 = createThemeButton("Themenbereich 2");
        Button thema3 = createThemeButton("Themenbereich 3");
        Button thema4 = createThemeButton("Themenbereich 4");
        Button thema5 = createThemeButton("Themenbereich 5");

        thema1.setOnAction(e -> showTheme(thema1.getText()));
        thema2.setOnAction(e -> showTheme(thema2.getText()));
        thema3.setOnAction(e -> showTheme(thema3.getText()));
        thema4.setOnAction(e -> showTheme(thema4.getText()));
        thema5.setOnAction(e -> showTheme(thema5.getText()));

        themenVBox.getChildren().addAll(auswahlLabel, thema1, thema2, thema3, thema4, thema5);
        return themenVBox;
    }

    // ---------- Button-Stil (Start-Button) ----------
    private String buttonMain() {
        return "-fx-font-size: 20px;"
                + "-fx-background-color: #3498db;"
                + "-fx-text-fill: white;"
                + "-fx-padding: 10px 44px;"
                + "-fx-background-radius: 10;";
    }

    // ---------- Buttons für Themenbereiche ----------
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

    // ---------- Popup ----------
    private void showTheme(String thema) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Thema");
        alert.setContentText("Themenbereich geöffnet: " + thema);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
