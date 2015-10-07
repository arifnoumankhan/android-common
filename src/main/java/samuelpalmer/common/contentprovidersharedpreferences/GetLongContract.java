package samuelpalmer.common.contentprovidersharedpreferences;

import android.content.SharedPreferences;

class GetLongContract extends GetValueContract<Long> {
	@Override
	public String methodName() {
		return "getLong";
	}

	@Override
	public Long process(SharedPreferences sharedPreferences, GetValueArgs<Long> args) {
		return sharedPreferences.getLong(args.key, args.defValue);
	}
}
