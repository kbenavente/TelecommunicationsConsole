public class TelecommunicationsSystem {

	private State currentSystemState;

	private boolean LGA_STATUS;
	private boolean HGA_STATUS;
	private boolean UHF_STATUS;

	public TelecommunicationsSystem() {

		this.currentSystemState = State.OFF;
		this.LGA_STATUS = false;
		this.HGA_STATUS = false;
		this.UHF_STATUS = false;

	}

	// COMMANDS

	public String TELE_POWER_ON() {

		this.currentSystemState = State.ON;

		return "TELECOMMUNICATIONS is powering on...\nTELECOMMUNICATIONS is ON\n" + TELE_LOW_GAIN_ON();

	}

	public String TELE_STATUS() {

		return "Low Gain Antenna: " + (LGA_STATUS ? "ON" : "OFF") + "\nHigh Gain Antenna: " + (HGA_STATUS ? "ON" : "OFF") + "\nUltra High Frequency Antenna: " + (UHF_STATUS ? "ON" : "OFF");


	}

	public String TELE_LOW_GAIN_ON() {


		LGA_STATUS = true;

		return "LOW GAIN ANTENNA is ON";

	}

	public String TELE_LOW_GAIN_OFF() {

		if(!UHF_STATUS && !HGA_STATUS) {

			return "ERROR: LOW GAIN must remain on.";

		} else {

			LGA_STATUS = false;

			return "LOW GAIN ANTENNA is OFF";

		}

	}

	public String TELE_INOPERABLE() {

		this.currentSystemState = State.INOPERABLE;

		return "inoperable";

	}

	public State getCurrentSystemState() {

		return currentSystemState;

	}

}
