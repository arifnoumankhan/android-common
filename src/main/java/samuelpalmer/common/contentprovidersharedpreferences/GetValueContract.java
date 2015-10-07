package samuelpalmer.common.contentprovidersharedpreferences;

import android.content.SharedPreferences;
import android.os.Bundle;

@SuppressWarnings("unchecked")
abstract class GetValueContract<TValue> implements Contract<GetValueArgs<TValue>, TValue> {

	private static final String KEY = "key";
	private static final String DEF_VALUE = "defValue";
	private static final String VALUE = "value";

	@Override
	public abstract String methodName();

	@Override
	public abstract TValue process(SharedPreferences sharedPreferences, GetValueArgs<TValue> args);

	@Override
	public Bundle serialiseArgs(GetValueArgs<TValue> value) {
		Bundle result = new Bundle(2);
		result.putString(KEY, value.key);
		SingleValueSerialiser.serialise(result, DEF_VALUE, value.defValue);
		return result;
	}

	@Override
	public GetValueArgs<TValue> deserialiseArgs(Bundle serialised) {
		GetValueArgs<TValue> result = new GetValueArgs<>();
		result.key = serialised.getString(KEY);
		result.defValue = (TValue)SingleValueSerialiser.deserialise(serialised.get(DEF_VALUE));
		return result;
	}

	@Override
	public Bundle serialiseResult(TValue value) {
		Bundle result = new Bundle(1);
		SingleValueSerialiser.serialise(result, VALUE, value);
		return result;
	}

	@Override
	public TValue deserialiseResult(Bundle serialised) {
		return (TValue) SingleValueSerialiser.deserialise(serialised.get(VALUE));
	}
}
