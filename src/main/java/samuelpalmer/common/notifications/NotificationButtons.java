package samuelpalmer.common.notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Build.VERSION;
import android.widget.RemoteViews;

import samuelpalmer.common.R;

public class NotificationButtons {

	private final RemoteViews content;
	private final Context context;
	private final int colourArgb;

	public NotificationButtons(Context context, int layoutResource, int colourArgb) {
		this.context = context;
		this.colourArgb = colourArgb;
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

	public RemoteViews getContentView() {
		return content;
	}

	private int actionButtonColour(int exampleIcon) {
		//Not thread-safe, but hopefully that won't matter
		//TODO: Don't store this as a static anymore since it depends on the passed-in colour
		if (actionButtonColour == null)
			actionButtonColour = new NotificationStyles(context, exampleIcon, colourArgb).getActionButtonColour();

		return actionButtonColour;
	}

	private static Integer actionButtonColour;

}
