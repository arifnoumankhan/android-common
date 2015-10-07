package samuelpalmer.common.contentprovidersharedpreferences;

import java.util.HashMap;

class ContractMap {

	public static Contract lookup(String method) {
		Contract result = lookup.get(method);
		if (result == null)
			throw new RuntimeException("Unknown method: " + method);

		return result;
	}

	private static final HashMap<String, Contract> lookup;
	static {
		Contract[] contracts = new Contract[] {
			new ApplyContract(),
			new CommitContract(),
			new ContainsContract(),
			new GetAllContract(),
			new GetBooleanContract(),
			new GetFloatContract(),
			new GetIntContract(),
			new GetLongContract(),
			new GetStringContract(),
			new GetStringSetContract(),
			new RegisterOnSharedPreferenceChangeListenerContract(),
			new UnregisterOnSharedPreferenceChangeListenerContract()
		};

		lookup = new HashMap<>(contracts.length);

		for (Contract contract : contracts)
			lookup.put(contract.methodName(), contract);
	}
}
