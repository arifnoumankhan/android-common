package samuelpalmer.common.contentprovidersharedpreferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.IBinder.DeathRecipient;
import android.os.RemoteException;

class ServerListener {
	private final IContentProviderSharedPreferenceChangeListener client;
	public final DeathRecipient deathRecipient;

	public final OnSharedPreferenceChangeListener nativeListener = new OnSharedPreferenceChangeListener() {
		@Override
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
			try {
				client.onSharedPreferenceChanged(key);
			} catch (RemoteException ignored) {}
		}
	};

	public ServerListener(IContentProviderSharedPreferenceChangeListener client, DeathRecipient deathRecipient) {
		this.client = client;
		this.deathRecipient = deathRecipient;
	}
}
