import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ConsoleOutputText extends Label {

	public ConsoleOutputText(String text, Color color) {

		super(">>>> " + text);

		this.setStyle("-fx-text-fill: greenyellow; -fx-font-size: 12px; -fx-font-family: 'Andale Mono';");
		this.setWrapText(true);

	}

}
