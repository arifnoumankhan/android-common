package samuelpalmer.common;

public class IntegerUtilities {
	public static int wrap(int value, int length) {
		int positiveWrapped = value % length;
		int nonNegative = positiveWrapped + length;
		return nonNegative % length;
	}
}
