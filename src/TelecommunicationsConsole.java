import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TelecommunicationsConsole implements EventHandler<ActionEvent> {

	private Scene previousScene;
	private Stage primaryStage;

	public TelecommunicationsConsole(Scene previousScene, Stage primaryStage) {

		this.previousScene = previousScene;
		this.primaryStage = primaryStage;

	}

	@Override
	public void handle(ActionEvent actionEvent) {

		TelecommunicationsSystem alpha = new TelecommunicationsSystem(); // An instance of a Telecommunications System -- Look into class to see defualt values/state

		VBox container = new VBox();
		container.prefWidthProperty().bind(primaryStage.widthProperty());
		container.prefHeightProperty().bind(primaryStage.heightProperty());

		HBox headerContainer = new HBox(30);
		headerContainer.setAlignment(Pos.BASELINE_RIGHT);
		headerContainer.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.1));
		headerContainer.prefWidthProperty().bind(primaryStage.widthProperty());
		headerContainer.setStyle("-fx-padding: 20px; -fx-background-color: #2d3436;");

		// ITEMS THAT GO INSIDE OF headerContainer

		Button buttonDisconnect = new Button("Disconnect");
		buttonDisconnect.getStyleClass().add("buttonDisconnect");

		SystemStateIndicator stateIndicator = new SystemStateIndicator(alpha.getCurrentSystemState());

		Label title = new Label("Telecommunications System Console");
		title.setStyle("-fx-text-alignment: left; -fx-text-fill: white; -fx-font-size: 20px; -fx-border-color: transparent white transparent transparent; -fx-padding: 20px 30px;");

		headerContainer.getChildren().addAll(title, stateIndicator, buttonDisconnect);

		//****************************************

		HBox contentContainer = new HBox();
		contentContainer.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.9));
		contentContainer.prefWidthProperty().bind(primaryStage.widthProperty());
		container.setStyle("-fx-background-color: pink;");

		// ITEMS THAT GO INSIDE OF contentContainer

		ArrayList<ConsoleOutputText> consoleTextStorage = new ArrayList<>();

		ScrollPane consoleScroller = new ScrollPane();
		consoleScroller.setVmax(1);

		VBox console = new VBox(4);
		console.prefHeightProperty().bind(consoleScroller.heightProperty());
		console.prefWidthProperty().bind(consoleScroller.widthProperty());

		console.setStyle("-fx-background-color: black");

		console.getChildren().addAll(consoleTextStorage);


		consoleScroller.setStyle("-fx-background-color: black;");
		consoleScroller.setContent(console);
		consoleScroller.setPrefHeight(contentContainer.getPrefHeight());
		consoleScroller.prefWidthProperty().bind(contentContainer.widthProperty().multiply(0.5));
		consoleScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		consoleScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);



		contentContainer.getChildren().addAll(consoleScroller);

		//****************************************

		container.getChildren().addAll(headerContainer, contentContainer);

		Scene currentScene = new Scene(container, 920, 720);
		currentScene.getStylesheets().clear();
		currentScene.getStylesheets().add(getClass().getResource("stylesheet/stylesheet.css").toExternalForm());

		primaryStage.setScene(currentScene);
		primaryStage.setTitle("Telecommunications Console - System");

	}

}
