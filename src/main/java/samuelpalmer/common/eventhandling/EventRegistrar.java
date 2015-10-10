package samuelpalmer.common.eventhandling;

public interface EventRegistrar {

	void subscribe(EventHandler subscriber);
	void unsubscribe(EventHandler subscriber);

}