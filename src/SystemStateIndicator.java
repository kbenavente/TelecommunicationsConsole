import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class SystemStateIndicator extends HBox {

	private State currentSystemState;
	private Color color;

	public SystemStateIndicator(State currentSystemState) {

		super(5);

		this.setAlignment(Pos.BASELINE_CENTER);

		this.currentSystemState = currentSystemState;

		if(this.currentSystemState == State.OFF)
			this.color = Color.RED;
		else if(this.currentSystemState == State.ON)
			this.color = Color.GREEN;
		else if(this.currentSystemState == State.INOPERABLE)
			this.color = Color.GOLD;

		Label title = new Label(currentSystemState.name());
		title.setStyle("-fx-font-weight: bold; -fx-font-size: 15px; -fx-text-fill: white;");

		Circle statusIndicator = new Circle(7, color);

		this.getChildren().addAll(title, statusIndicator);

	}

}
