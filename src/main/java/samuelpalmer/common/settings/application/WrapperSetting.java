package samuelpalmer.common.settings.application;

import android.content.SharedPreferences;

public abstract class WrapperSetting<TValue, TUnderlying> extends SettingType<TValue> {
	private final SettingType<TUnderlying> underlying;

	public WrapperSetting(SettingType<TUnderlying> underlying) {
		this.underlying = underlying;
	}

	@Override
	public void put(SharedPreferences.Editor editor, String key, TValue value) {
		underlying.put(editor, key, convertToUnderlying(value));
	}

	@Override
	public boolean supportsNull() {
		return underlying.supportsNull();
	}

	@Override
	public TValue getOrDefault(SharedPreferences prefs, String key, TValue defaultValue) {
		TUnderlying underlying = this.underlying.getOrDefault(prefs, key, convertToUnderlying(defaultValue));
		if (underlying == null)
			return null;

		return convertFromUnderlying(underlying);
	}

	@Override
	public TValue convertFromRaw(Object raw) {
		return convertFromUnderlying(underlying.convertFromRaw(raw));
	}

	protected abstract TUnderlying convertToUnderlying(TValue value);
	protected abstract TValue convertFromUnderlying(TUnderlying underlying);
}
