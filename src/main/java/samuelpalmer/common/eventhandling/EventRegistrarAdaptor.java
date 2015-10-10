package samuelpalmer.common.eventhandling;

public abstract class EventRegistrarAdaptor extends IntolerantEventRegistrar {

	@Override
	public void subscribe(EventHandler subscriber) {
		boolean wasEmpty = noSubscriptions();
		super.subscribe(subscriber);
		if (wasEmpty)
			try {
				subscribeToAdaptee();
			}
			catch (Exception ex) {
				super.unsubscribe(subscriber);
				throw ex;
			}
	}

	@Override
	public void unsubscribe(EventHandler subscriber) {
		super.unsubscribe(subscriber);
		if (noSubscriptions())
			unsubscribeFromAdaptee();
	}

	protected abstract void subscribeToAdaptee();
	protected abstract void unsubscribeFromAdaptee();

}
