package samuelpalmer.common.settings.application;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class FloatSetting extends SettingType<Float> {
	@Override
	public void put(Editor editor, String key, Float value) {
		editor.putFloat(key, value);
	}

	@Override
	public boolean supportsNull() {
		return false;
	}

	@Override
	public Float getOrDefault(SharedPreferences prefs, String key, Float defaultValue) {
		return prefs.getFloat(key, defaultValue);
	}
}
