package samuelpalmer.common;

@SuppressWarnings("unused")
public class Orientation {

	private static final int numberOfPossibleRotations = 4;
	public static final Orientation NATURAL = new Orientation(0);
	public static final Orientation RIGHT_SIDE = new Orientation(1);
	public static final Orientation UPSIDE_DOWN = new Orientation(2);
	public static final Orientation LEFT_SIDE = new Orientation(3);

	private final int clockwiseRotationsFromNaturalOrientation;

	public Orientation(int clockwiseRotationsFromNaturalOrientation) throws IllegalArgumentException {
		if (clockwiseRotationsFromNaturalOrientation < 0 || clockwiseRotationsFromNaturalOrientation >= numberOfPossibleRotations)
			throw new IllegalArgumentException("Unknown orientation: " + clockwiseRotationsFromNaturalOrientation);

		this.clockwiseRotationsFromNaturalOrientation = clockwiseRotationsFromNaturalOrientation;
	}

	public Rotation minus(Orientation other) {
		return new Rotation(getClockwiseRotationsFromNaturalOrientation() - other.getClockwiseRotationsFromNaturalOrientation());
	}

	public Orientation plus(Rotation change) {
		int unwrappedResultingClockwiseIndex = getClockwiseRotationsFromNaturalOrientation() + change.clockwiseCount();
		int wrappedResultingClockwiseIndex = IntegerUtilities.wrap(unwrappedResultingClockwiseIndex, numberOfPossibleRotations);

		return new Orientation(wrappedResultingClockwiseIndex);
	}

	@Override
	public boolean equals(Object o) {
		return o != null
				&& this.getClass() == o.getClass()
				&& this.getClockwiseRotationsFromNaturalOrientation() == ((Orientation) o).getClockwiseRotationsFromNaturalOrientation();
	}

	@Override
	public String toString() {
		switch (this.getClockwiseRotationsFromNaturalOrientation()) {
			case 0:
				return "Natural";
			case 1:
				return "Right Side";
			case 2:
				return "Upside-Down";
			case 3:
				return "Left Side";
			default:
				throw new RuntimeException("Unknown number of rotations: " + this.getClockwiseRotationsFromNaturalOrientation());
		}
	}

	public int getClockwiseRotationsFromNaturalOrientation() {
		return clockwiseRotationsFromNaturalOrientation;
	}

	public boolean isNaturalOrUpsideDown() {
		return clockwiseRotationsFromNaturalOrientation % 2 == 0;
	}

}
