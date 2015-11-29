package samuelpalmer.common.settings.application;

public class EnumSetting<T extends Enum<T>> extends WrapperSetting<T, String> {
	private final Class<T> clazz;

	public EnumSetting(Class<T> clazz) {
		super(new StringSetting());
		this.clazz = clazz;
	}

	@Override
	protected String convertToUnderlying(T value) {
		return value.name();
	}

	@Override
	protected T convertFromUnderlying(String underlying) {
		return Enum.valueOf(clazz, underlying);
	}
}
