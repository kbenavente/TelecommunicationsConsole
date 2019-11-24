public class LowGainAntenna {

	private State currentLGAState;

	public LowGainAntenna(State currentHGAState) {

		this.currentLGAState = currentHGAState;

	}

	public void setCurrentLGAState(State currentLGAState) {
		this.currentLGAState = currentLGAState;
	}

	public State getCurrentLGAState() {
		return currentLGAState;
	}
}





