public class UltraHighFrequencyAntenna {

	private State currentUHFState;

	public UltraHighFrequencyAntenna(State currentHGAState) {

		this.currentUHFState = currentHGAState;

	}

	public void setCurrentUHFState(State currentUHFState) {
		this.currentUHFState = currentUHFState;
	}

	public State getCurrentUHFState() {
		return currentUHFState;
	}
}





