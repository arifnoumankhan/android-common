package samuelpalmer.common.settings.application;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import samuelpalmer.common.eventhandling.EventRegistrarAdaptor;

public class Settings {

	private final SharedPreferences prefs;

	protected Settings(SharedPreferences prefs) {
		this.prefs = prefs;
	}

	public class Transaction {
		private final Editor editor;

		@SuppressLint("CommitPrefEdits")
		public Transaction() {
			editor = prefs.edit();
		}

		public void commit() {
			//It'd be nice to call commit() and check the return value, but doing so introduces lag
			editor.apply();
		}

	}

	public class Setting<T> extends EventRegistrarAdaptor implements samuelpalmer.common.settings.Setting<T> {

		private final SettingType<T> type;
		private final String key;
		private final T defaultValue;

		public Setting(SettingType<T> type, String key, T defaultValue) {
			this.type = type;
			this.key = key;
			this.defaultValue = defaultValue;
		}

		/**
		 * Sets this to its default value if it hasn't been set before.
		 */
		public void ensureInitialised() {
			if (!hasValue() && defaultValue != null)
				set(defaultValue);
		}

		public T tryGet() {
			if (defaultValue != null || type.supportsNull())
				return type.getOrDefault(prefs, key, defaultValue);
			else {
				Object raw = prefs.getAll().get(key);
				if (raw == null)
					return null;

				return type.convertFromRaw(raw);
			}
		}

		public T get() {
			T value = tryGet();
			if (value == null)
				throw new RuntimeException("No preference value found for key " + key);

			return value;
		}

		public boolean hasValue() {
			return prefs.contains(key);
		}

		public void set(T value) {
			Transaction transaction = new Transaction();
			enqueue(value, transaction);
			transaction.commit();
		}

		public void remove() {
			Transaction transaction = new Transaction();
			remove(transaction);
			transaction.commit();
		}

		public void enqueue(T value, Transaction transaction) {
			if (value == null)
				throw new IllegalArgumentException("value is null");

			type.put(transaction.editor, key, value);
		}

		public void remove(Transaction transaction) {
			transaction.editor.remove(key);
		}

		public String getKey() {
			return key;
		}

		@Override
		protected void subscribeToAdaptee() {
			prefs.registerOnSharedPreferenceChangeListener(listener);
		}

		@Override
		protected void unsubscribeFromAdaptee() {
			prefs.unregisterOnSharedPreferenceChangeListener(listener);
		}

		private final SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
			@Override
			public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
				if (key.equals(Settings.Setting.this.key))
					report();
			}
		};

	}
}
