package samuelpalmer.common.settings.application;

import android.content.SharedPreferences;

abstract class SettingType<T> {
	@SuppressWarnings("unchecked")
	public T convertFromRaw(Object raw) {
		return (T)raw;
	}
	public abstract void put(SharedPreferences.Editor editor, String key, T value);
	public abstract boolean supportsNull();
	public abstract T getOrDefault(SharedPreferences prefs, String key, T defaultValue);
}
