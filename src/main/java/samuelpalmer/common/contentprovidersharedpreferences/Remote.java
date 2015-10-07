package samuelpalmer.common.contentprovidersharedpreferences;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

class Remote {

	private final String preferencesName;
	private final String authority;
	private final ContentResolver contentResolver;

	public Remote(Context context, String authority, String preferencesName) {
		this.preferencesName = preferencesName;
		this.authority = authority;
		contentResolver = context.getContentResolver();
	}

	public <TArgs, TResult> TResult process(Contract<TArgs, TResult> contract, TArgs args) {
		Bundle serialisedResult = contentResolver.call(
			new Uri.Builder()
				.scheme("content")
				.authority(authority)
				.build(),
			contract.methodName(),
			preferencesName,
			contract.serialiseArgs(args)
		);

		if (serialisedResult == null)
			throw new RuntimeException("Got back null. The content provider probably crashed.");

		return contract.deserialiseResult(serialisedResult);
	}

}
