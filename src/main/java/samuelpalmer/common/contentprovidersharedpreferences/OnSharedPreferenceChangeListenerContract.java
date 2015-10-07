package samuelpalmer.common.contentprovidersharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;

import java.util.HashMap;
import java.util.Map;

abstract class OnSharedPreferenceChangeListenerContract implements Contract<IContentProviderSharedPreferenceChangeListener, Void> {

	public static final String BINDER = "binder";
	protected static final Map<IBinder, ServerListener> listeners = new HashMap<>();

	protected static void unsubscribe(SharedPreferences sharedPreferences, IBinder binder) {
		synchronized (listeners) {
			ServerListener removed = listeners.remove(binder);

			if (removed != null) {
				sharedPreferences.unregisterOnSharedPreferenceChangeListener(removed.nativeListener);

				if (binder != null)
					binder.unlinkToDeath(removed.deathRecipient, 0);
			}
		}
	}

	public Bundle serialiseArgs(IContentProviderSharedPreferenceChangeListener value) {
		Bundle result = new Bundle(1);
		SingleValueSerialiser.serialise(result, BINDER, value.asBinder());
		return result;
	}

	public IContentProviderSharedPreferenceChangeListener deserialiseArgs(Bundle serialised) {
		serialised.setClassLoader(ParcelableBinder.class.getClassLoader());
		IBinder binder = (IBinder) SingleValueSerialiser.deserialise(serialised.get(BINDER));
		return IContentProviderSharedPreferenceChangeListener.Stub.asInterface(binder);
	}

	public Bundle serialiseResult(Void value) {
		return new Bundle();
	}

	public Void deserialiseResult(Bundle serialised) {
		return null;
	}

}
