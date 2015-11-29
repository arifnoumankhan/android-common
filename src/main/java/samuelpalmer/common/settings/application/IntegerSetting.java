package samuelpalmer.common.settings.application;

import android.content.SharedPreferences;

public class IntegerSetting extends SettingType<Integer> {
	public void put(SharedPreferences.Editor editor, String key, Integer value) {
		editor.putInt(key, value);
	}

	@Override
	public boolean supportsNull() {
		return false;
	}

	@Override
	public Integer getOrDefault(SharedPreferences prefs, String key, Integer defaultValue) {
		return prefs.getInt(key, defaultValue);
	}
}
