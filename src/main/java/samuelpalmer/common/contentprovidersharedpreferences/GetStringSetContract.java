package samuelpalmer.common.contentprovidersharedpreferences;

import android.content.SharedPreferences;

import java.util.Set;

class GetStringSetContract extends GetValueContract<Set<String>> {
	@Override
	public String methodName() {
		return "getStringSet";
	}

	@Override
	public Set<String> process(SharedPreferences sharedPreferences, GetValueArgs<Set<String>> args) {
		return sharedPreferences.getStringSet(args.key, args.defValue);
	}
}
