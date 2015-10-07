package samuelpalmer.common.contentprovidersharedpreferences;

import android.content.SharedPreferences;

class GetBooleanContract extends GetValueContract<Boolean> {
	@Override
	public String methodName() {
		return "getBoolean";
	}

	@Override
	public Boolean process(SharedPreferences sharedPreferences, GetValueArgs<Boolean> args) {
		return sharedPreferences.getBoolean(args.key, args.defValue);
	}
}
