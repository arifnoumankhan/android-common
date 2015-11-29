package samuelpalmer.common.settings.application;

import android.content.SharedPreferences;

public class BooleanSetting extends SettingType<Boolean> {
	public void put(SharedPreferences.Editor editor, String key, Boolean value) {
		editor.putBoolean(key, value);
	}

	@Override
	public boolean supportsNull() {
		return false;
	}

	@Override
	public Boolean getOrDefault(SharedPreferences prefs, String key, Boolean defaultValue) {
		return prefs.getBoolean(key, defaultValue);
	}
}
