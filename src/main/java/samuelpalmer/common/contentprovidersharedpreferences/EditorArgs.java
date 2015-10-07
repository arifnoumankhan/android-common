package samuelpalmer.common.contentprovidersharedpreferences;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;
import java.util.Set;

class EditorArgs {
	public Map<String, Object> modified;
	public boolean clear;

	@SuppressLint("CommitPrefEdits")
	public Editor applyTo(SharedPreferences sharedPreferences) {
		Editor editor = sharedPreferences.edit();

		if (clear)
			editor.clear();

		for (String key : modified.keySet()) {
			Object value = modified.get(key);

			if (value == null)
				editor.remove(key);
			else if (value instanceof String)
				editor.putString(key, (String) value);
			else if (value instanceof Set)
				//noinspection unchecked
				editor.putStringSet(key, (Set<String>) value);
			else if (value instanceof Integer)
				editor.putInt(key, (Integer) value);
			else if (value instanceof Long)
				editor.putLong(key, (Long) value);
			else if (value instanceof Float)
				editor.putFloat(key, (Float) value);
			else if (value instanceof Boolean)
				editor.putBoolean(key, (Boolean) value);
			else
				throw new RuntimeException("Unsupported value: " + value + " (" + value.getClass() + ")");
		}

		return editor;
	}
}
