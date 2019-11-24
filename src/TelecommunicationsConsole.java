import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Stack;

public class TelecommunicationsConsole implements EventHandler<ActionEvent> {

	private static final String SERVER_IP_ADDRESS = "####"; // Add IP address here
	private static final int PORT = 9001; // Add Port here

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

		HBox headerContainer = new HBox();
		//headerContainer.setAlignment(Pos.BASELINE_RIGHT);
		headerContainer.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.1));
		headerContainer.prefWidthProperty().bind(primaryStage.widthProperty());
		headerContainer.setStyle("-fx-background-color: #2d3436;");

		// ITEMS THAT GO INSIDE OF headerContainer

		Label title = new Label("Telecommunications System Console");
		title.prefHeightProperty().bind(headerContainer.heightProperty());
		title.prefWidthProperty().bind(headerContainer.widthProperty().multiply(0.5));
		title.setStyle("-fx-text-fill: white; -fx-font-size: 26px; -fx-padding: 10px;");

		HBox headerInformationContainer = new HBox(14);
		headerInformationContainer.prefHeightProperty().bind(headerContainer.heightProperty());
		headerInformationContainer.prefWidthProperty().bind(headerContainer.widthProperty().multiply(0.5));
		headerInformationContainer.setAlignment(Pos.CENTER_RIGHT);
		headerInformationContainer.setStyle("-fx-padding: 10px;");

		Button buttonDisconnect = new Button("Disconnect");
		buttonDisconnect.getStyleClass().add("buttonDisconnect");

		Button buttonToggleInoperable = new Button("Toggle Inoperable");
		buttonToggleInoperable.getStyleClass().add("buttonInoperable");

		SystemStateIndicator stateIndicator = new SystemStateIndicator("System", alpha.getCurrentSystemState());
		stateIndicator.setAlignment(Pos.CENTER);

		headerInformationContainer.getChildren().addAll(stateIndicator, buttonToggleInoperable, buttonDisconnect);

		headerContainer.getChildren().addAll(title, headerInformationContainer);

		//****************************************

		HBox contentContainer = new HBox();
		contentContainer.prefHeightProperty().bind(primaryStage.heightProperty().multiply(0.9));
		contentContainer.prefWidthProperty().bind(primaryStage.widthProperty());
		container.setStyle("-fx-background-color: pink;");

		// ITEMS THAT GO INSIDE OF contentContainer

		ArrayList<ConsoleOutputText> consoleTextStorage = new ArrayList<>();
		consoleTextStorage.add(new ConsoleOutputText("CCDS Console", null));

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

		VBox telecommunicationSubsystemDetailsContainer = new VBox();
		telecommunicationSubsystemDetailsContainer.prefHeightProperty().bind(contentContainer.heightProperty());
		telecommunicationSubsystemDetailsContainer.prefWidthProperty().bind(contentContainer.widthProperty().multiply(0.5));
		telecommunicationSubsystemDetailsContainer.setStyle("-fx-background-color: #2d3436;");

		// ITEMS THAT GO INSIDE OF telecommunicationSubsystemDetailsContainer

		HBox queuesContainer = new HBox();
		queuesContainer.prefHeightProperty().bind(telecommunicationSubsystemDetailsContainer.heightProperty().multiply(0.5));
		queuesContainer.prefWidthProperty().bind(telecommunicationSubsystemDetailsContainer.widthProperty());
		queuesContainer.setStyle("-fx-background-color: #b2bec3;");

		ArrayList<InformationQueueBlock> data = new ArrayList<>();
//		data.add(new InformationQueueBlock("Sample data something.jpg"));

		VBox dataQueueContainer = new VBox();
		dataQueueContainer.prefHeightProperty().bind(queuesContainer.prefHeightProperty());
		dataQueueContainer.prefWidthProperty().bind(queuesContainer.prefWidthProperty().multiply(0.5));

		Label dataQueueTitle = new Label("Data Buffer");
		dataQueueTitle.prefHeightProperty().bind(dataQueueContainer.heightProperty().multiply(0.1));
		dataQueueTitle.prefWidthProperty().bind(dataQueueContainer.widthProperty());
		dataQueueTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

		ScrollPane dataQueueScroller = new ScrollPane();
		dataQueueScroller.prefHeightProperty().bind(dataQueueContainer.prefHeightProperty().multiply(0.9));
		dataQueueScroller.prefWidthProperty().bind(dataQueueContainer.prefWidthProperty());
		dataQueueScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		dataQueueScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


		VBox dataQueueContentContainer = new VBox(5);
		dataQueueContentContainer.prefHeightProperty().bind(dataQueueScroller.prefHeightProperty());
		dataQueueContentContainer.prefWidthProperty().bind(dataQueueScroller.prefWidthProperty());
		dataQueueContentContainer.setStyle("-fx-background-color: #b2bec3;");

		dataQueueScroller.setContent(dataQueueContentContainer);

		dataQueueContentContainer.getChildren().addAll(data);

		dataQueueContainer.getChildren().addAll(dataQueueTitle, dataQueueScroller);

		ArrayList<InformationQueueBlock> instructions = new ArrayList<>();
//		instructions.add(new InformationQueueBlock("MOBILITY_MOVE_FORWARD"));
//		instructions.add(new InformationQueueBlock("MOBILITY_MOVE_FORWARD"));

		VBox instructionsQueueContainer = new VBox();
		instructionsQueueContainer.prefHeightProperty().bind(queuesContainer.prefHeightProperty());
		instructionsQueueContainer.prefWidthProperty().bind(queuesContainer.prefWidthProperty().multiply(0.5));

		Label instructionsQueueTitle = new Label("Instructions Buffer");
		instructionsQueueTitle.prefHeightProperty().bind(instructionsQueueContainer.heightProperty().multiply(0.1));
		instructionsQueueTitle.prefWidthProperty().bind(instructionsQueueContainer.widthProperty());
		instructionsQueueTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

		ScrollPane instructionsQueueScroller = new ScrollPane();
		instructionsQueueScroller.prefHeightProperty().bind(instructionsQueueContainer.prefHeightProperty().multiply(0.9));
		instructionsQueueScroller.prefWidthProperty().bind(instructionsQueueContainer.prefWidthProperty());
		instructionsQueueScroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		instructionsQueueScroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

		VBox instructionsQueueContentContainer = new VBox(5);
		instructionsQueueContentContainer.prefHeightProperty().bind(instructionsQueueScroller.prefHeightProperty());
		instructionsQueueContentContainer.prefWidthProperty().bind(instructionsQueueScroller.prefWidthProperty());
		instructionsQueueContentContainer.setStyle("-fx-background-color: #b2bec3;");

		instructionsQueueScroller.setContent(instructionsQueueContentContainer);

		instructionsQueueContentContainer.getChildren().addAll(instructions);

		instructionsQueueContainer.getChildren().addAll(instructionsQueueTitle, instructionsQueueScroller);

		queuesContainer.getChildren().addAll(dataQueueContainer, instructionsQueueContainer);


		VBox systemDetailsContainer = new VBox();
		systemDetailsContainer.prefHeightProperty().bind(contentContainer.prefHeightProperty().multiply(0.5));
		systemDetailsContainer.prefWidthProperty().bind(contentContainer.prefWidthProperty());
		systemDetailsContainer.setStyle("-fx-background-color: #2d3436;");

		HBox antennasDetailContainer = new HBox();
		antennasDetailContainer.prefHeightProperty().bind(systemDetailsContainer.heightProperty().multiply(1));
		antennasDetailContainer.prefWidthProperty().bind(systemDetailsContainer.widthProperty());

		VBox hgaDetailsContainer = new VBox();
		hgaDetailsContainer.prefHeightProperty().bind(antennasDetailContainer.heightProperty());
		hgaDetailsContainer.prefWidthProperty().bind(antennasDetailContainer.widthProperty().multiply(0.33));
		//hgaDetailsContainer.setStyle("-fx-background-color: palegoldenrod;");

		StackPane hgaImageContainer = new StackPane();
		hgaImageContainer.setAlignment(Pos.CENTER);
		hgaImageContainer.setStyle("-fx-alignment: center;");
		hgaImageContainer.prefHeightProperty().bind(hgaDetailsContainer.heightProperty().multiply(0.5));
		hgaImageContainer.prefWidthProperty().bind(hgaDetailsContainer.widthProperty());
		//hgaImageContainer.setStyle("-fx-background-color: purple;");

		ImageView hgaImage = new ImageView("images/antenna-image.png");
		hgaImage.setStyle("-fx-alignment: center;");
		hgaImage.setX(hgaImageContainer.getPrefWidth() / 2);
		hgaImage.setFitHeight(80);
		hgaImage.setFitWidth(80);

		hgaImageContainer.getChildren().add(hgaImage);

		StackPane hgaStatusContainer = new StackPane();
		hgaStatusContainer.setAlignment(Pos.CENTER);
		hgaStatusContainer.prefHeightProperty().bind(hgaDetailsContainer.heightProperty().multiply(0.5));
		hgaStatusContainer.prefWidthProperty().bind(hgaDetailsContainer.widthProperty());
		//hgaStatusContainer.setStyle("-fx-background-color: greenyellow;");

		SystemStateIndicator hgaStatusIndicator = new SystemStateIndicator("HGA", alpha.getHGAState());

		hgaStatusContainer.getChildren().add(hgaStatusIndicator);

		hgaDetailsContainer.getChildren().addAll(hgaImageContainer, hgaStatusContainer);

		// LGA Details

		VBox lgaDetailsContainer = new VBox();
		lgaDetailsContainer.prefHeightProperty().bind(antennasDetailContainer.heightProperty());
		lgaDetailsContainer.prefWidthProperty().bind(antennasDetailContainer.widthProperty().multiply(0.33));
		//lgaDetailsContainer.setStyle("-fx-background-color: palegoldenrod;");

		StackPane lgaImageContainer = new StackPane();
		lgaImageContainer.setAlignment(Pos.CENTER);
		lgaImageContainer.setStyle("-fx-alignment: center;");
		lgaImageContainer.prefHeightProperty().bind(lgaDetailsContainer.heightProperty().multiply(0.5));
		lgaImageContainer.prefWidthProperty().bind(lgaDetailsContainer.widthProperty());
		//lgaImageContainer.setStyle("-fx-background-color: purple;");

		ImageView lgaImage = new ImageView("images/antenna-image.png");
		lgaImage.setStyle("-fx-alignment: center;");
		lgaImage.setX(lgaImageContainer.getPrefWidth() / 2);
		lgaImage.setFitHeight(80);
		lgaImage.setFitWidth(80);

		lgaImageContainer.getChildren().add(lgaImage);

		StackPane lgaStatusContainer = new StackPane();
		lgaStatusContainer.setAlignment(Pos.CENTER);
		lgaStatusContainer.prefHeightProperty().bind(lgaDetailsContainer.heightProperty().multiply(0.5));
		lgaStatusContainer.prefWidthProperty().bind(lgaDetailsContainer.widthProperty());
		//lgaStatusContainer.setStyle("-fx-background-color: greenyellow;");

		SystemStateIndicator lgaStatusIndicator = new SystemStateIndicator("LGA", alpha.getLGAState());

		lgaStatusContainer.getChildren().add(lgaStatusIndicator);

		lgaDetailsContainer.getChildren().addAll(lgaImageContainer, lgaStatusContainer);
		
		// UHF

		VBox uhfDetailsContainer = new VBox();
		uhfDetailsContainer.prefHeightProperty().bind(antennasDetailContainer.heightProperty());
		uhfDetailsContainer.prefWidthProperty().bind(antennasDetailContainer.widthProperty().multiply(0.33));
		//uhfDetailsContainer.setStyle("-fx-background-color: palegoldenrod;");

		StackPane uhfImageContainer = new StackPane();
		uhfImageContainer.setAlignment(Pos.CENTER);
		uhfImageContainer.setStyle("-fx-alignment: center;");
		uhfImageContainer.prefHeightProperty().bind(uhfDetailsContainer.heightProperty().multiply(0.5));
		uhfImageContainer.prefWidthProperty().bind(uhfDetailsContainer.widthProperty());
		//uhfImageContainer.setStyle("-fx-background-color: purple;");

		ImageView uhfImage = new ImageView("images/antenna-image.png");
		uhfImage.setStyle("-fx-alignment: center;");
		uhfImage.setX(uhfImageContainer.getPrefWidth() / 2);
		uhfImage.setFitHeight(80);
		uhfImage.setFitWidth(80);

		uhfImageContainer.getChildren().add(uhfImage);

		StackPane uhfStatusContainer = new StackPane();
		uhfStatusContainer.setAlignment(Pos.CENTER);
		uhfStatusContainer.prefHeightProperty().bind(uhfDetailsContainer.heightProperty().multiply(0.5));
		uhfStatusContainer.prefWidthProperty().bind(uhfDetailsContainer.widthProperty());
		//uhfStatusContainer.setStyle("-fx-background-color: greenyellow;");

		SystemStateIndicator uhfStatusIndicator = new SystemStateIndicator("UHF", alpha.getUHFState());

		uhfStatusContainer.getChildren().add(uhfStatusIndicator);

		uhfDetailsContainer.getChildren().addAll(uhfImageContainer, uhfStatusContainer);

		antennasDetailContainer.getChildren().addAll(hgaDetailsContainer, lgaDetailsContainer, uhfDetailsContainer);

		systemDetailsContainer.getChildren().addAll(antennasDetailContainer);

		//*******************************************************************

		telecommunicationSubsystemDetailsContainer.getChildren().addAll(queuesContainer, systemDetailsContainer);

		contentContainer.getChildren().addAll(consoleScroller, telecommunicationSubsystemDetailsContainer);

		//****************************************

		container.getChildren().addAll(headerContainer, contentContainer);

		Scene currentScene = new Scene(container, 920, 720);
		currentScene.getStylesheets().clear();
		currentScene.getStylesheets().add(getClass().getResource("stylesheet/stylesheet.css").toExternalForm());

		primaryStage.setScene(currentScene);
		primaryStage.setTitle("Telecommunications Console - System");

		buttonToggleInoperable.setOnAction((e) -> {

			if(alpha.getCurrentSystemState() != State.INOPERABLE) {


				alpha.setCurrentSystemState(State.INOPERABLE);
				alpha.setUHFStatus(State.INOPERABLE);
				alpha.setHGAStatus(State.INOPERABLE);
				alpha.setLGAStatus(State.INOPERABLE);

				stateIndicator.setState(State.INOPERABLE);
				hgaStatusIndicator.setState(State.INOPERABLE);
				lgaStatusIndicator.setState(State.INOPERABLE);
				uhfStatusIndicator.setState(State.INOPERABLE);

			} else {

				alpha.setCurrentSystemState(State.ON);
				alpha.setUHFStatus(State.OFF);
				alpha.setHGAStatus(State.OFF);
				alpha.setLGAStatus(State.ON);

				stateIndicator.setState(State.ON);
				hgaStatusIndicator.setState(State.OFF);
				lgaStatusIndicator.setState(State.ON);
				uhfStatusIndicator.setState(State.OFF);

			}

		});

		new Thread(() -> {

			Socket socket = null;
			try {
				socket = new Socket(SERVER_IP_ADDRESS, PORT);
			} catch (IOException e) {
				e.printStackTrace();
			}

			BufferedReader in = null;

			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}

			PrintWriter out = null;

			try {
				out = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}

			String line = null;
			try {
				line = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.print(line);

			out.println("TELE");

			while (true) {

				try {
					line = in.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.print(line);

				if(line.startsWith("MESSAGE")) {

					String finalLine = line;

					int index = 0;
					for(int i = 0; i < 3; i++) {
						index =  finalLine.indexOf(" ");
						finalLine = finalLine.substring(index);
					}
					finalLine = finalLine.substring(index + 1);

					String finalLine1 = finalLine;

					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							consoleTextStorage.add(new ConsoleOutputText(finalLine1, null));
							console.getChildren().clear();
							console.getChildren().addAll(consoleTextStorage);
							consoleScroller.setVmax(1);

						}
					});

				}

				if(line.contains("TELE_POWER_ON")) {

					PrintWriter finalOut = out;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							if(alpha.getCurrentSystemState() == State.ON) {

								finalOut.println("TELECOMMUNICATIONS SUBSYSTEM is already ON");

							} else if(alpha.getCurrentSystemState() == State.INOPERABLE) {

								finalOut.println("TELECOMMUNICATIONS SUBSYSTEM is INOPERABLE");

							} else {

								stateIndicator.setState(State.ON);
								alpha.setCurrentSystemState(State.ON);
								lgaStatusIndicator.setState(State.ON);
								alpha.setLGAStatus(State.ON);

								finalOut.println("TELECOMMUNICATIONS SUBSYSTEM IS TURNING ON...");
								finalOut.println("TELECOMMUNICATIONS SUBSYSTEM IS ON");

							}

						}
					});

				} else if(line.contains("TELE_POWER_OFF")) {

					PrintWriter finalOut1 = out;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							if(alpha.getCurrentSystemState() == State.OFF) {

								finalOut1.println("TELECOMMUNICATIONS SUBSYTEM is already OFF");

							} else {

								stateIndicator.setState(State.OFF);
								lgaStatusIndicator.setState(State.OFF);
								hgaStatusIndicator.setState(State.OFF);
								uhfStatusIndicator.setState(State.OFF);

								finalOut1.println("TELECOMMUNICATIONS SUBSYSTEM IS POWERING OFF...");
								alpha.setCurrentSystemState(State.OFF);
								finalOut1.println("TELECOMMUNICATIONS SUBSYSTEM IS OFF");
								alpha.setLGAStatus(State.OFF);
								alpha.setHGAStatus(State.OFF);
								alpha.setUHFStatus(State.OFF);

							}

						}
					});

				} else if(line.contains("TELE_STATUS")) {

					PrintWriter finalOut1 = out;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							finalOut1.println("*****************************");
							finalOut1.println("TELECOMMUNICATIONS SUBSYSTEM is " + alpha.getCurrentSystemState());
							finalOut1.println("Low Gain Antenna (LGA) is " + alpha.getLGAState());
							finalOut1.println("High Gain Antenna (HGA) is " + alpha.getHGAState());
							finalOut1.println("Ultra High Frequency Antenna (UHF) is " + alpha.getUHFState());
							finalOut1.println("Data Buffer has " + data.size() + " items.");
							finalOut1.println("Instructions Buffer has " + instructions.size() + " items.");
							finalOut1.println("*****************************");

						}
					});

				} else if(line.contains("TELE_LGA_ON")) {

					PrintWriter finalOut1 = out;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							if(alpha.getLGAState() == State.OFF) {


								lgaStatusIndicator.setState(State.ON);
								hgaStatusIndicator.setState(State.OFF);
								uhfStatusIndicator.setState(State.OFF);
								finalOut1.println("Low Gain Antenna (LGA) is ON");
								finalOut1.println("ALL OTHER ANTENNAS HAVE BEEN POWERED ON");
								alpha.setLGAStatus(State.ON);
								alpha.setHGAStatus(State.OFF);
								alpha.setUHFStatus(State.OFF);

							} else {

								finalOut1.println("Low Gain Antenna (LGA) is already ON");

							}

						}
					});

				} else if(line.contains("TELE_HGA_ON")) {

					PrintWriter finalOut1 = out;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							if(alpha.getHGAState() == State.OFF) {

								lgaStatusIndicator.setState(State.OFF);
								hgaStatusIndicator.setState(State.ON);
								uhfStatusIndicator.setState(State.OFF);
								finalOut1.println("High Gain Antenna (HGA) is ON");
								finalOut1.println("ALL OTHER ANTENNAS HAVE BEEN POWERED OFF");
								alpha.setLGAStatus(State.OFF);
								alpha.setHGAStatus(State.ON);
								alpha.setUHFStatus(State.OFF);

							} else {

								finalOut1.println("High Gain Antenna (HGA) is already ON");

							}

						}
					});

				} else if(line.contains("TELE_HGA_OFF")) {

					PrintWriter finalOut1 = out;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							if(alpha.getHGAState() == State.ON) {

								lgaStatusIndicator.setState(State.ON);
								hgaStatusIndicator.setState(State.OFF);
								uhfStatusIndicator.setState(State.OFF);
								finalOut1.println("High Gain Antenna (HGA) is OFF");
								finalOut1.println("Low Gain Antenna is ON");
								alpha.setLGAStatus(State.ON);
								alpha.setHGAStatus(State.OFF);
								alpha.setUHFStatus(State.OFF);

							} else {

								finalOut1.println("High Gain Antenna (HGA) is already OFF");

							}

						}
					});

				}else if(line.contains("TELE_UHF_ON")) {

					PrintWriter finalOut1 = out;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							if(alpha.getUHFState() == State.OFF) {

								lgaStatusIndicator.setState(State.OFF);
								hgaStatusIndicator.setState(State.OFF);
								uhfStatusIndicator.setState(State.ON);
								finalOut1.println("Ultra High Frequency Antenna (UHF) is ON");

								alpha.setLGAStatus(State.OFF);
								alpha.setHGAStatus(State.OFF);
								alpha.setUHFStatus(State.ON);

							} else {

								finalOut1.println("Ultra High Frequency Antenna (UHF) is already ON");

							}

						}
					});

				} else if(line.contains("TELE_UHF_OFF")) {

					PrintWriter finalOut1 = out;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							if(alpha.getHGAState() == State.ON) {

								lgaStatusIndicator.setState(State.ON);
								hgaStatusIndicator.setState(State.OFF);
								uhfStatusIndicator.setState(State.OFF);
								finalOut1.println("Ultra High Frequency Antenna (HGA) is OFF");
								finalOut1.println("Low Gain Antenna is ON");
								alpha.setLGAStatus(State.ON);
								alpha.setHGAStatus(State.OFF);
								alpha.setUHFStatus(State.OFF);

							} else {

								finalOut1.println("Ultra High Frequency Antenna (UHF) is already OFF");

							}

						}
					});

				} else if(line.contains("TELE_DATA_SET") || line.contains("TELE_COM_DATA_SET")) {

					PrintWriter finalOut1 = out;
					String finalLine1 = line;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							if(alpha.getCurrentSystemState() == State.ON) {

								String sentContent = finalLine1.substring(finalLine1.indexOf("(") + 1, finalLine1.indexOf(")"));

								data.add(new InformationQueueBlock(sentContent));

								dataQueueContentContainer.getChildren().clear();
								dataQueueContentContainer.getChildren().addAll(data);

								finalOut1.println("Data has been saved");

								if (data.size() == 3) {

									data.clear();
									dataQueueContentContainer.getChildren().clear();
									dataQueueContentContainer.getChildren().addAll(data);

									finalOut1.println("DATA HAS BEEN SENT TO EARTH");

								}

							} else {

								finalOut1.println("TELECOMMUNICATIONS is currently " + alpha.getCurrentSystemState() + ". Failed to save.");

							}

						}
					});

				} else if(line.contains("TELE_COMMAND_SET")) {

					PrintWriter finalOut1 = out;
					String finalLine1 = line;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							if (alpha.getCurrentSystemState() == State.ON) {

								String sentContent = finalLine1.substring(finalLine1.indexOf("(") + 1, finalLine1.indexOf(")"));

								instructions.add(new InformationQueueBlock(sentContent));

								instructionsQueueContentContainer.getChildren().clear();
								instructionsQueueContentContainer.getChildren().addAll(instructions);

								if (instructions.size() == 3) {

									for (InformationQueueBlock x : instructions)
										finalOut1.println(x.getText());

									instructions.clear();
									instructionsQueueContentContainer.getChildren().clear();
									instructionsQueueContentContainer.getChildren().addAll(instructions);

									finalOut1.println("Instructions Buffer has been cleared.");

								}

							} else if(alpha.getCurrentSystemState() == State.INOPERABLE) {

								finalOut1.println("TELECOMMUNICATIONS is currently " + alpha.getCurrentSystemState() + ". Connection Failure.");

							}

						}

					});

				} else if(line.contains("TELE_DATA_QUEUE")) {

					PrintWriter finalOut1 = out;
					String finalLine1 = line;
					Platform.runLater(new Runnable() {
						@Override
						public void run() {

							String sentContent = finalLine1.substring(finalLine1.indexOf("(") + 1, finalLine1.indexOf(")"));

							instructions.add(new InformationQueueBlock(sentContent));

							instructionsQueueContentContainer.getChildren().clear();
							instructionsQueueContentContainer.getChildren().addAll(instructions);

						}
					});

				}

			}

		}).start();


	}

}


