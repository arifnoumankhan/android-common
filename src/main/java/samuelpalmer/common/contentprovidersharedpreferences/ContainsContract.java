package samuelpalmer.common.contentprovidersharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;

class ContainsContract implements Contract<String, Boolean> {

	private static final String KEY = "key";
	private static final String CONTAINED_KEY = "containedKey";

	@Override
	public String methodName() {
		return "contains";
	}

	@Override
	public Boolean process(SharedPreferences sharedPreferences, String key) {
		return sharedPreferences.contains(key);
	}

	@Override
	public Bundle serialiseArgs(String key) {
		Bundle result = new Bundle(1);
		result.putString(KEY, key);
		return result;
	}

	@Override
	public String deserialiseArgs(Bundle serialised) {
		return serialised.getString(KEY);
	}

	@Override
	public Bundle serialiseResult(Boolean containedKey) {
		Bundle result = new Bundle(1);
		result.putBoolean(CONTAINED_KEY, containedKey);
		return result;
	}

	@Override
	public Boolean deserialiseResult(Bundle serialised) {
		return serialised.getBoolean(CONTAINED_KEY);
	}

}
