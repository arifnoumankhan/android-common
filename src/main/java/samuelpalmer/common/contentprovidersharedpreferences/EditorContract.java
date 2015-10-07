package samuelpalmer.common.contentprovidersharedpreferences;

import android.os.Bundle;

import java.util.Map;

abstract class EditorContract<TResult> implements Contract<EditorArgs, TResult> {

	private static final String MODIFIED = "modified";
	private static final String CLEAR = "clear";

	public Bundle serialiseArgs(EditorArgs value) {
		Bundle result = new Bundle(2);
		SingleValueSerialiser.serialise(result, MODIFIED, value.modified);
		result.putBoolean(CLEAR, value.clear);
		return result;
	}

	public EditorArgs deserialiseArgs(Bundle serialised) {
		EditorArgs result = new EditorArgs();
		//noinspection unchecked
		result.modified = (Map<String, Object>) SingleValueSerialiser.deserialise(serialised.get(MODIFIED));
		result.clear = serialised.getBoolean(CLEAR);
		return result;
	}

}
