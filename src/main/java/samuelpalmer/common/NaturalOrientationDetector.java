package samuelpalmer.common;

import android.graphics.Point;

public class NaturalOrientationDetector {

	private final Screen screen;

	public NaturalOrientationDetector(Screen screen) {
		this.screen = screen;
	}

	public boolean deviceIsNaturallyPortrait() {
		if (result == null)
			result = calculate();

		return result;
	}

	private Boolean result;

	private boolean calculate() {
		Point naturalResolution = screen.naturalResolution();
		return naturalResolution.x <= naturalResolution.y;
	}

}
