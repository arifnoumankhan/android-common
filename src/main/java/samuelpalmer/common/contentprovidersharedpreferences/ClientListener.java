package samuelpalmer.common.contentprovidersharedpreferences;

import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;

class ClientListener extends IContentProviderSharedPreferenceChangeListener.Stub {

	private final ContentProviderSharedPreferences sharedPreferences;
	private final OnSharedPreferenceChangeListener client;
	private final Handler handler;

	public ClientListener(ContentProviderSharedPreferences sharedPreferences, OnSharedPreferenceChangeListener client) {
		this.sharedPreferences = sharedPreferences;
		this.client = client;
		handler = new Handler(Looper.getMainLooper());
	}

	@Override
	public void onSharedPreferenceChanged(final String key) throws RemoteException {
		handler.post(new Runnable() {
			@Override
			public void run() {
				client.onSharedPreferenceChanged(sharedPreferences, key);
			}
		});
	}

}
