package samuelpalmer.common.contentprovidersharedpreferences;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

abstract class SingleValueSerialiser {
	public static void serialise(Bundle result, String key, Object currentValue) {
		if (currentValue == null || currentValue instanceof String)
			result.putString(key, (String)currentValue);
		else if (currentValue instanceof Set)
			result.putStringArray(key, (String[])((Set) currentValue).toArray());
		else if (currentValue instanceof Integer)
			result.putInt(key, (int) currentValue);
		else if (currentValue instanceof Long)
			result.putLong(key, (long) currentValue);
		else if (currentValue instanceof Float)
			result.putFloat(key, (float) currentValue);
		else if (currentValue instanceof Boolean)
			result.putBoolean(key, (boolean) currentValue);
		else if (currentValue instanceof Map) {
			//noinspection unchecked
			result.putBundle(key, serialiseMap((Map<String, ?>) currentValue));
		}
		else if (currentValue instanceof IBinder)
			result.putParcelable(key, new ParcelableBinder((IBinder)currentValue));
		else
			throw unsupportedValue(currentValue);
	}


	private static Bundle serialiseMap(Map<String, ?> value) {
		Bundle result = new Bundle();

		for (String key : value.keySet()) {
			Object currentValue = value.get(key);
			SingleValueSerialiser.serialise(result, key, currentValue);
		}

		return result;
	}

	public static Object deserialise(Object currentValue) {
		if (
			currentValue == null
			||
			currentValue instanceof String
			||
			currentValue instanceof Integer
			||
			currentValue instanceof Long
			||
			currentValue instanceof Float
			||
			currentValue instanceof Boolean
		)
			return currentValue;
		else if (currentValue instanceof String[])
			return new HashSet<>(Arrays.asList((String[]) currentValue));
		else if (currentValue instanceof Bundle)
			return deserialiseMap((Bundle) currentValue);
		else if (currentValue instanceof Parcelable)
			return ((ParcelableBinder)currentValue).binder;
		else
			throw unsupportedValue(currentValue);
	}

	private static Map<String, ?> deserialiseMap(Bundle serialised) {
		HashMap<String, Object> result = new HashMap<>();

		for (String key : serialised.keySet()) {
			Object currentValue = serialised.get(key);
			result.put(key, SingleValueSerialiser.deserialise(currentValue));
		}

		return result;
	}

	private static RuntimeException unsupportedValue(Object currentValue) {
		return new RuntimeException("Unsupported preference value: " + currentValue + " (" + currentValue.getClass() + ")");
	}
}
