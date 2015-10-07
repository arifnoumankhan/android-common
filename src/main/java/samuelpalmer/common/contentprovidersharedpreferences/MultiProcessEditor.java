package samuelpalmer.common.contentprovidersharedpreferences;

import android.annotation.SuppressLint;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

class MultiProcessEditor implements Editor {

	private final Remote remote;
	private final EditorArgs args;

	@SuppressLint("CommitPrefEdits")
	public MultiProcessEditor(Remote remote) {
		this.remote = remote;

		args = new EditorArgs();
		args.modified = new HashMap<>();
		args.clear = false;
	}

	public Editor putString(String key, String value) {
		synchronized (this) {
			args.modified.put(key, value);
			return this;
		}
	}
	public Editor putStringSet(String key, Set<String> values) {
		synchronized (this) {
			args.modified.put(key,
					(values == null) ? null : new HashSet<>(values));
			return this;
		}
	}
	public Editor putInt(String key, int value) {
		synchronized (this) {
			args.modified.put(key, value);
			return this;
		}
	}
	public Editor putLong(String key, long value) {
		synchronized (this) {
			args.modified.put(key, value);
			return this;
		}
	}
	public Editor putFloat(String key, float value) {
		synchronized (this) {
			args.modified.put(key, value);
			return this;
		}
	}
	public Editor putBoolean(String key, boolean value) {
		synchronized (this) {
			args.modified.put(key, value);
			return this;
		}
	}

	public Editor remove(String key) {
		synchronized (this) {
			args.modified.put(key, null);
			return this;
		}
	}

	public Editor clear() {
		synchronized (this) {
			args.clear = true;
			return this;
		}
	}

	public boolean commit() {
		synchronized (this) {
			Boolean result = remote.process(new CommitContract(), args);
			clearAllPending();
			return result;
		}
	}

	public void apply() {
		synchronized (this) {
			remote.process(new ApplyContract(), args);
			clearAllPending();
		}
	}

	private void clearAllPending() {
		args.clear = false;
		args.modified.clear();
	}

}
