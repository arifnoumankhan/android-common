package samuelpalmer.common.notifications;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build.VERSION;
import android.widget.RemoteViews;

import samuelpalmer.common.R;

public class NotificationButtons {

	private final RemoteViews content;
	private final Context context;

	public NotificationButtons(Context context, int layoutResource) {
		this.context = context;
		content = new RemoteViews(context.getPackageName(), layoutResource);
		content.removeAllViews(R.id.notification_buttons_main_content);
	}

	/**
	 * @param icon 32 dip x 32 dip (including padding), white
	 */
	public NotificationButtons configureButton(int id, int icon, PendingIntent onClickIntent, CharSequence contentDescription) {
		content.setImageViewResource(id, icon);
		content.setInt(id, "setColorFilter", actionButtonColour(icon));
		content.setOnClickPendingIntent(id, onClickIntent);

		if (VERSION.SDK_INT >= 15)
			content.setContentDescription(id, contentDescription);

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
