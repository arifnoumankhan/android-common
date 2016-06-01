package samuelpalmer.common.notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

final class NotificationStyles {

	private final StyleType title = new StyleType("TITLE");
	private final StyleType actionButton = new StyleType("ACTION");
	private final Context context;
	private final int exampleIconResource;
	private final int colourArgb;

	public NotificationStyles(Context context, int exampleIconResource, int colourArgb) {
		this.context = context;
		this.exampleIconResource = exampleIconResource;
		this.colourArgb = colourArgb;
		extractColors();
	}

	public int getActionButtonColour() {
		int result;

		if (actionButton.colour != null)
			result = actionButton.colour;
		else { //Android 4.0 doesn't have notification actions
			if (title.colour != null)
				result = Color.rgb(Color.red(title.colour), Color.green(title.colour), Color.blue(title.colour));
			else
				result = Color.BLACK;

			result = Color.argb((int) (255 * .8), Color.red(result), Color.green(result), Color.blue(result));
		}

		return result;
	}

	private void extractColors()
	{
		NotificationBuilderCompat builder = NotificationBuilderCompat.create(context)
			.setContentTitle(title.searchTip)
			.setColor(colourArgb); //Android N seems to use the colour on the action buttons

		//Add action button
		String actionTitle = actionButton.searchTip;
		PendingIntent intent = PendingIntent.getActivity(context, 0, new Intent(""), 0);
		builder.addAction(exampleIconResource, actionTitle, intent);

		//Build
		RemoteViews contentView = getContentView(builder);

		LinearLayout group = new LinearLayout(context);
		ViewGroup event = (ViewGroup) contentView.apply(context, group);
		recurseGroup(event);
		group.removeAllViews();

		if (title.colour == null)
			Log.w(NotificationStyles.class.getSimpleName(), "Couldn't find notification title colour");
		if (VERSION.SDK_INT >= 16 && actionButton.colour == null)
			Log.w(NotificationStyles.class.getSimpleName(), "Couldn't find notification action colour");
	}

	private RemoteViews getContentView(NotificationBuilderCompat builder) {
		if (VERSION.SDK_INT == 23 && VERSION.PREVIEW_SDK_INT == 3)
			return builder.createBigContentView();
		else if (VERSION.SDK_INT >= 16)
			//noinspection deprecation
			return builder.build().bigContentView;
		else
			//noinspection deprecation
			return builder.build().contentView;
	}

	private void recurseGroup(ViewGroup viewGroup)
	{
		for (int i = 0; i < viewGroup.getChildCount(); ++i)
		{
			View view = viewGroup.getChildAt(i);

			if (view instanceof TextView)
			{
				TextView textView = (TextView) view;
				String text = textView.getText().toString();

				for (StyleType type : new StyleType[] {title, actionButton})
					if (text.equals(type.searchTip))
					{
						DisplayMetrics metrics = new DisplayMetrics();
						WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
						windowManager.getDefaultDisplay().getMetrics(metrics);
						type.colour = textView.getTextColors().getDefaultColor();
					}
			}
			else if (view instanceof ViewGroup)
				recurseGroup((ViewGroup) view);
		}
	}

	private static class StyleType {
		public Integer colour;
		private final String searchTip;

		public StyleType(String tip) {
			searchTip = tip;
		}
	}
}
