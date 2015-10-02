package samuelpalmer.common;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.WindowManager;

public class Screen {

	private final Display display;

	public Screen(WindowManager windowManager) {
		display = windowManager.getDefaultDisplay();
	}

	/**
	 * Expensive; prefer ScreenRotationMonitor.currentRotation()
	 */
	public Orientation realOrientation() {
		AndroidSurfaceRotation rotation = new AndroidSurfaceRotation(display.getRotation());
		return rotation.orientation();
	}

	public Point naturalResolution() {
		ScreenRotationAndResolution display = getDisplayHack();

		if (display == null)
			display = getDisplay();

		if (display.rotation.isNaturalOrUpsideDown())
			return new Point(display.width, display.height);
		else
			//noinspection SuspiciousNameCombination
			return new Point(display.height, display.width);
	}

	/**
	 * This should provide perfectly accurate results when it actually works
	 */
	private ScreenRotationAndResolution getDisplayHack() {
		if (VERSION.SDK_INT >= 17) {
			try {
				DisplayInfo displayInfo = new DisplayInfo();
				display.getClass().getMethod("getDisplayInfo", DisplayInfo.class).invoke(display, displayInfo);
				ScreenRotationAndResolution result = new ScreenRotationAndResolution();
				result.rotation = new AndroidSurfaceRotation(displayInfo.rotation).orientation();
				result.width = displayInfo.logicalWidth;
				result.height = displayInfo.logicalHeight;
				return result;
			}
			catch (Exception ex) {
				Log.w(getClass().getSimpleName(), ex);
			}
		}

		return null;
	}

	/**
	 *
	 */
	private ScreenRotationAndResolution getDisplay() {
		//Getting the screen dimensions and rotation should be as close together as possible
		//since it's possible for them to be retrieved out of sync

		ScreenRotationAndResolution result = new ScreenRotationAndResolution();
		Point screenResolution = currentScreenResolution();
		result.rotation = realOrientation();
		result.width = screenResolution.x;
		result.height = screenResolution.y;
		return result;
	}

	@SuppressLint("NewApi") /** Since {@link Display#getRealSize(Point)} is actually available as of API 14 */
	private Point currentScreenResolution() {
		Point screenResolution = new Point();
		display.getRealSize(screenResolution);
		return screenResolution;
	}

	private class ScreenRotationAndResolution {
		public Orientation rotation;
		public int width;
		public int height;
	}
}
