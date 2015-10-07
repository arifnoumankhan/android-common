package samuelpalmer.common.contentprovidersharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.Map;

class GetAllContract implements Contract<Void, Map<String,?>> {

	private static final String VALUES = "values";

	@Override
	public String methodName() {
		return "getAll";
	}

	@Override
	public Map<String, ?> process(SharedPreferences sharedPreferences, Void aVoid) {
		return sharedPreferences.getAll();
	}

	@Override
	public Bundle serialiseArgs(Void value) {
		return null;
	}

	@Override
	public Void deserialiseArgs(Bundle serialised) {
		return null;
	}

	@Override
	public Bundle serialiseResult(Map<String, ?> value) {
		Bundle result = new Bundle(1);
		SingleValueSerialiser.serialise(result, VALUES, value);
		return result;
	}

	@Override
	public Map<String, ?> deserialiseResult(Bundle serialised) {
		//noinspection unchecked
		return (Map<String, ?>) SingleValueSerialiser.deserialise(serialised.get(VALUES));
	}

}
