package samuelpalmer.common.settings.application;

import android.content.SharedPreferences;

import java.util.Set;

public class StringSetSetting extends SettingType<Set<String>> {

	@Override
	public void put(SharedPreferences.Editor editor, String key, Set<String> value) {
		editor.putStringSet(key, value);
	}

	@Override
	public boolean supportsNull() {
		return true;
	}

	@Override
	public Set<String> getOrDefault(SharedPreferences prefs, String key, Set<String> defaultValue) {
		return prefs.getStringSet(key, defaultValue);
	}

}
