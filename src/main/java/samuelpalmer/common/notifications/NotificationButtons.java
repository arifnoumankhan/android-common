package samuelpalmer.common.notifications;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import android.widget.RemoteViews;

import samuelpalmer.common.R;

public class NotificationButtons {

	private final RemoteViews content;
	private final Context context;

	public NotificationButtons(Context context) {
		this.context = context;
		content = new RemoteViews(context.getPackageName(), R.layout.notification_buttons);
		content.removeAllViews(R.id.notification_buttons_main_content);
		content.removeAllViews(R.id.notification_buttons_container);
	}

	/**
	 * One of the LAYOUT_DIRECTION_ constants
	 * @see android.view.View
	 */
	@TargetApi(VERSION_CODES.JELLY_BEAN_MR1)
	public NotificationButtons setLayoutDirection(int layoutDirection) {
		content.setInt(R.id.notification_buttons_container, "setLayoutDirection", layoutDirection);
		return this;
	}

	public NotificationButtons configureButton(int icon, PendingIntent onClickIntent, CharSequence contentDescription) {
		RemoteViews button = new RemoteViews(context.getPackageName(), R.layout.notification_button);

		button.setImageViewResource(R.id.notification_button, icon);
		button.setInt(R.id.notification_button, "setColorFilter", actionButtonColour(icon));
		button.setOnClickPendingIntent(R.id.notification_button, onClickIntent);

		if (VERSION.SDK_INT >= 15)
			button.setContentDescription(R.id.notification_button, contentDescription);

		content.addView(R.id.notification_buttons_container, button);

		return this;
	}

	public void applyTo(Notification notification) {
		content.addView(R.id.notification_buttons_main_content, notification.contentView);
		notification.contentView = content;
	}

	private int actionButtonColour(int exampleIcon) {
		//Not thread-safe, but hopefully that won't matter
		if (actionButtonColour == null)
			actionButtonColour = new NotificationStyles(context, exampleIcon).getActionButtonColour();

		return actionButtonColour;
	}

	private static Integer actionButtonColour;

}
