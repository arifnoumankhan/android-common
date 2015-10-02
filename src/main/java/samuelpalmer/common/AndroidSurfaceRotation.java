package samuelpalmer.common;

import android.view.Surface;

public class AndroidSurfaceRotation {

	private final int value;

	public AndroidSurfaceRotation(int value) throws IllegalArgumentException {
		if (value < Surface.ROTATION_0 || value > Surface.ROTATION_270)
			throw new IllegalArgumentException("Value " + value + " is not a valid surface rotation");

		this.value = value;
	}

	public AndroidSurfaceRotation(Orientation value) {
		this(Surface.ROTATION_0 + value.getClockwiseRotationsFromNaturalOrientation());
	}

	public Orientation orientation() throws IllegalArgumentException {
		return new Orientation(offset());
	}

	private int offset() {
		return getValue() - Surface.ROTATION_0;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "ROTATION_" + offset() * 90;
	}
}
