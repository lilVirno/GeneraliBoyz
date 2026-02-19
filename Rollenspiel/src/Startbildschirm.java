import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Startbildschirm extends Application {

    @Override
    public void start(Stage stage) {

        // ---------- STARTBILDSCHIRM ----------
        BorderPane startPane = new BorderPane();
        startPane.setStyle("-fx-background-color: #f5f5f5;");

        Label titel = new Label("Willkommen zum Lernspiel!");
        titel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        Button startButton = new Button("Start");
        startButton.setStyle(buttonMain());

        BorderPane.setAlignment(titel, Pos.CENTER);
        BorderPane.setMargin(titel, new Insets(40, 0, 20, 0));

        VBox startVBox = new VBox(titel, startButton);
        startVBox.setAlignment(Pos.CENTER);
        startVBox.setSpacing(40);

        startPane.setCenter(startVBox);

        Scene startScene = new Scene(startPane, 700, 450);

        // ---------- THEMENBEREICHE ----------
        VBox themenVBox = new VBox();
        themenVBox.setAlignment(Pos.TOP_CENTER);
        themenVBox.setSpacing(15);
        themenVBox.setPadding(new Insets(30, 0, 0, 0));
        themenVBox.setStyle("-fx-background-color: #f5f5f5;");

        Label auswahlLabel = new Label("Wähle einen Themenbereich:");
        auswahlLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button thema1 = createThemeButton("Themenbereich 1");
        Button thema2 = createThemeButton("Themenbereich 2");
        Button thema3 = createThemeButton("Themenbereich 3");
        Button thema4 = createThemeButton("Themenbereich 4");
        Button thema5 = createThemeButton("Themenbereich 5");

        themenVBox.getChildren().addAll(
                auswahlLabel, thema1, thema2, thema3, thema4, thema5
        );

        Scene themenScene = new Scene(themenVBox, 700, 450);

        // ---------- ACTION: Start ----------
        startButton.setOnAction(e -> stage.setScene(themenScene));

        // ---------- ACTION: Themen ----------
        thema1.setOnAction(e -> showTheme(thema1.getText()));
        thema2.setOnAction(e -> showTheme(thema2.getText()));
        thema3.setOnAction(e -> showTheme(thema3.getText()));
        thema4.setOnAction(e -> showTheme(thema4.getText()));
        thema5.setOnAction(e -> showTheme(thema5.getText()));

        // ---------- Anzeigen ----------
        stage.setScene(startScene);
        stage.setTitle("Gamification – Lernspiel");
        stage.show();
    }

    // ---------- Button-Stil (Start-Button) ----------
    private String buttonMain() {
        return "-fx-font-size: 20px;"
                + "-fx-background-color: #3498db;"
                + "-fx-text-fill: white;"
                + "-fx-padding: 10px 24px;"
                + "-fx-background-radius: 10;";
    }

    // ---------- Buttons für Themenbereiche ----------
    private Button createThemeButton(String text) {
        Button btn = new Button(text);
        btn.setStyle(
                "-fx-font-size: 18px;"
                        + "-fx-background-color: white;"
                        + "-fx-padding: 10px 20px;"
                        + "-fx-background-radius: 10;"
                        + "-fx-border-radius: 10;"
                        + "-fx-border-color: #d0d0d0;"
                        + "-fx-border-width: 2;"
        );

        btn.setOnMouseEntered(e ->
                btn.setStyle(
                        "-fx-font-size: 18px;"
                                + "-fx-background-color: #eaeaea;"
                                + "-fx-padding: 10px 20px;"
                                + "-fx-background-radius: 10;"
                                + "-fx-border-radius: 10;"
                                + "-fx-border-color: #d0d0d0;"
                                + "-fx-border-width: 2;"
                )
        );

        btn.setOnMouseExited(e ->
                btn.setStyle(
                        "-fx-font-size: 18px;"
                                + "-fx-background-color: white;"
                                + "-fx-padding: 10px 20px;"
                                + "-fx-background-radius: 10;"
                                + "-fx-border-radius: 10;"
                                + "-fx-border-color: #d0d0d0;"
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
