package samuelpalmer.common.contentprovidersharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ContentProviderSharedPreferences implements SharedPreferences {

	private final Remote remote;

	public ContentProviderSharedPreferences(Context context, String contentProviderAuthority, String preferencesName) {
		remote = new Remote(context, contentProviderAuthority, preferencesName);
	}

	@Override
	public Map<String, ?> getAll() {
		return remote.process(new GetAllContract(), null);
	}

	@Override
	public String getString(String key, String defValue) {
		return remote.process(new GetStringContract(), new GetValueArgs<>(key, defValue));
	}

	@Override
	public Set<String> getStringSet(String key, Set<String> defValues) {
		return remote.process(new GetStringSetContract(), new GetValueArgs<>(key, defValues));
	}

	@Override
	public int getInt(String key, int defValue) {
		return remote.process(new GetIntContract(), new GetValueArgs<>(key, defValue));
	}

	@Override
	public long getLong(String key, long defValue) {
		return remote.process(new GetLongContract(), new GetValueArgs<>(key, defValue));
	}

	@Override
	public float getFloat(String key, float defValue) {
		return remote.process(new GetFloatContract(), new GetValueArgs<>(key, defValue));
	}

	@Override
	public boolean getBoolean(String key, boolean defValue) {
		return remote.process(new GetBooleanContract(), new GetValueArgs<>(key, defValue));
	}

	@Override
	public boolean contains(String key) {
		return remote.process(new ContainsContract(), key);
	}

	@Override
	public Editor edit() {
		return new MultiProcessEditor(remote);
	}

	private final HashMap<OnSharedPreferenceChangeListener, ClientListener> mListeners = new HashMap<>();

	public void registerOnSharedPreferenceChangeListener(final OnSharedPreferenceChangeListener listener) {
		synchronized(this) {
			ClientListener client = new ClientListener(this, listener);
			remote.process(new RegisterOnSharedPreferenceChangeListenerContract(), client);
			mListeners.put(listener, client);
		}
	}

	public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
		synchronized(this) {
			ClientListener client = mListeners.remove(listener);
			if (client != null)
				remote.process(new UnregisterOnSharedPreferenceChangeListenerContract(), client);
		}
	}

}
