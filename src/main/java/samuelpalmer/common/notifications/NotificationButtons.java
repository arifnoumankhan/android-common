package samuelpalmer.common.notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Build.VERSION;
import android.widget.RemoteViews;

import samuelpalmer.common.R;

public class NotificationButtons {

	private final RemoteViews content;
	private final int buttonColourArgb;

	public NotificationButtons(Context context, int layoutResource, int buttonColourArgb) {
		content = new RemoteViews(context.getPackageName(), layoutResource);
		content.removeAllViews(R.id.notification_content);
		this.buttonColourArgb = buttonColourArgb;
	}

	/**
	 * @param icon 32 dip x 32 dip (including padding), white
	 */
	public NotificationButtons configureButton(int id, int icon, PendingIntent onClickIntent, CharSequence contentDescription) {
		content.setImageViewResource(id, icon);
		content.setInt(id, "setColorFilter", buttonColourArgb);
		content.setOnClickPendingIntent(id, onClickIntent);

		if (VERSION.SDK_INT >= 15)
			content.setContentDescription(id, contentDescription);

		return this;
	}

	public RemoteViews getContentView() {
		return content;
	}

}
