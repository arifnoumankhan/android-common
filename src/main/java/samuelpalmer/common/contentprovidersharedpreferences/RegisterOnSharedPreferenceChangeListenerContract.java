package samuelpalmer.common.contentprovidersharedpreferences;

import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.RemoteException;

class RegisterOnSharedPreferenceChangeListenerContract extends OnSharedPreferenceChangeListenerContract {

	@Override
	public String methodName() {
		return "registerOnSharedPreferenceChangeListener";
	}

	@Override
	public Void process(final SharedPreferences sharedPreferences, final IContentProviderSharedPreferenceChangeListener listener) {

		final IBinder binder = listener.asBinder();

		DeathRecipient dr = new DeathRecipient() {
			@Override
			public void binderDied() {
				unsubscribe(sharedPreferences, binder);
			}
		};

		ServerListener serverListener = new ServerListener(listener, dr);

		sharedPreferences.registerOnSharedPreferenceChangeListener(serverListener.nativeListener);

		try {
			binder.linkToDeath(dr, 0);
		} catch (RemoteException e) {
			return null;
		}

		synchronized (listeners) {
			listeners.put(binder, serverListener);
		}

		return null;
	}

}
