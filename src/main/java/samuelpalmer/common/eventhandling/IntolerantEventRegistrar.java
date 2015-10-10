package samuelpalmer.common.eventhandling;

import java.util.HashSet;
import java.util.Set;

public abstract class IntolerantEventRegistrar implements EventRegistrar {
	private final Set<EventHandler> subscriptions = new HashSet<>();

	@Override
	public void subscribe(EventHandler subscriber) {
		if (subscribed(subscriber))
			throw new RuntimeException("Already subscribed: " + subscriber);

		subscriptions.add(subscriber);
	}

	@Override
	public void unsubscribe(EventHandler subscriber) {
		if (!subscribed(subscriber))
			throw new RuntimeException("Unknown subscriber: " + subscriber);

		subscriptions.remove(subscriber);
	}

	private boolean subscribed(EventHandler subscriber) {
		return subscriptions.contains(subscriber);
	}

	protected void report() {
		for (EventHandler subscriber : subscriptions)
			subscriber.update();
	}

	protected boolean noSubscriptions() {
		return subscriptions.isEmpty();
	}
}
