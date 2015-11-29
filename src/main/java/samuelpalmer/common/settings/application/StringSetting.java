package samuelpalmer.common.settings.application;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class StringSetting extends SettingType<String> {
	@Override
	public void put(Editor editor, String key, String value) {
		editor.putString(key, value);
	}

	@Override
	public boolean supportsNull() {
		return true;
	}

	@Override
	public String getOrDefault(SharedPreferences prefs, String key, String defaultValue) {
		return prefs.getString(key, defaultValue);
	}
}
