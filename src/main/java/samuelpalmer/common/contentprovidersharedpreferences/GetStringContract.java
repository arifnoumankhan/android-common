package samuelpalmer.common.contentprovidersharedpreferences;

import android.content.SharedPreferences;

class GetStringContract extends GetValueContract<String> {
	@Override
	public String methodName() {
		return "getString";
	}

	@Override
	public String process(SharedPreferences sharedPreferences, GetValueArgs<String> args) {
		return sharedPreferences.getString(args.key, args.defValue);
	}
}
