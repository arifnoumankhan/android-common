package samuelpalmer.common.contentprovidersharedpreferences;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

class ParcelableBinder implements Parcelable {

	public final IBinder binder;

	public ParcelableBinder(IBinder binder) {
		this.binder = binder;
	}

	public ParcelableBinder(Parcel in) {
		binder = in.readStrongBinder();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeStrongBinder(binder);
	}

	public static final Parcelable.Creator<ParcelableBinder> CREATOR = new Parcelable.Creator<ParcelableBinder>() {
		public ParcelableBinder createFromParcel(Parcel in) {
			return new ParcelableBinder(in);
		}

		public ParcelableBinder[] newArray(int size) {
			return new ParcelableBinder[size];
		}
	};
}
