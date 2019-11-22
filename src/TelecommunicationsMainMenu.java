import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TelecommunicationsMainMenu extends Application {

	@Override
	public void start(Stage stage) throws Exception {

		VBox container = new VBox(230);
		container.setStyle("-fx-background-image: url('images/mars-background.jpg'); -fx-background-size: cover; -fx-padding: 30px");
		container.setAlignment(Pos.BASELINE_CENTER);
		container.setPrefHeight(stage.getHeight());
		container.setPrefWidth(stage.getWidth());

		// Title Text

		Label title = new Label("Telecommunications Console");
		title.setStyle("-fx-font-family: 'Andale Mono'; -fx-font-size: 40px; -fx-text-fill: white; -fx-font-weight: bold;");

		// Connect to CCDS

		Button buttonConnectToCCDS = new Button("Connect to CCDS");
		buttonConnectToCCDS.setAlignment(Pos.BOTTOM_CENTER);

		// GridPane for Button(s)

		GridPane buttonContainer = new GridPane();
		buttonContainer.setAlignment(Pos.BASELINE_CENTER);

		buttonContainer.add(buttonConnectToCCDS, 0, 0);

		container.getChildren().addAll(title, buttonContainer);

		Scene scene = new Scene(container, 720, 720);
		scene.getStylesheets().clear();
		scene.getStylesheets().add(getClass().getResource("stylesheet/stylesheet.css").toExternalForm());

		stage.setScene(scene);
		stage.setTitle("Telecommunications Console");
		stage.show();

		// Event Handler(s)

		buttonConnectToCCDS.setOnAction(new TelecommunicationsConsole(scene, stage));

	}

	public static void main(String[] args) {

		Application.launch(args);

	}
}
