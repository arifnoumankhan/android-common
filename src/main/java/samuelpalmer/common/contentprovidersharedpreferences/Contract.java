package samuelpalmer.common.contentprovidersharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;

interface Contract<TArgs, TResult> {

	String methodName();
	TResult process(SharedPreferences sharedPreferences, TArgs args);
	Bundle serialiseArgs(TArgs value);
	TArgs deserialiseArgs(Bundle serialised);

	/**
	 * Must not return null
	 */
	Bundle serialiseResult(TResult value);
	TResult deserialiseResult(Bundle serialised);

}
