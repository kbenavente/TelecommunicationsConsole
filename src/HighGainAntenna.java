public class HighGainAntenna {

	private State currentHGAState;

	public HighGainAntenna(State currentHGAState) {

		this.currentHGAState = currentHGAState;

	}

	public void setCurrentHGAState(State currentHGAState) {
		this.currentHGAState = currentHGAState;
	}

	public State getCurrentHGAState() {
		return currentHGAState;
	}
}





