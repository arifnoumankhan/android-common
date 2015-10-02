package samuelpalmer.common;

/**
 * Represents the difference between two orientations; change in orientation
 */
public class Rotation {

	private final int clockwiseRotations;

	public Rotation(int clockwiseRotations) {
		this.clockwiseRotations = clockwiseRotations;
	}

	public int clockwiseCount() {
		return this.clockwiseRotations;
	}

	@Override
	public String toString() {
		return clockwiseRotations + " clockwise turn" + (clockwiseRotations == 1 ? "" : "s");
	}

}
