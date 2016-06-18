package samuelpalmer.common.contentprovidersharedpreferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

class CommitContract extends EditorContract<Boolean> {

	private static final String SUCCESS = "success";

	@Override
	public String methodName() {
		return "commit";
	}

	@Override
	public Boolean process(SharedPreferences sharedPreferences, EditorArgs editorArgs) {
		Editor editor = editorArgs.applyTo(sharedPreferences);
		return editor.commit();
	}

	@Override
	public Bundle serialiseResult(Boolean value) {
		Bundle result = new Bundle(1);
		result.putBoolean(SUCCESS, value);
		return result;
	}

	@Override
	public Boolean deserialiseResult(Bundle serialised) {
		return serialised.getBoolean(SUCCESS);
	}

}
