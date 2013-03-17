package net.rdrei.android.simstatus;

import java.util.Date;

import javax.inject.Inject;

import net.rdrei.android.simstatus.StatusResult.Status;
import android.content.SharedPreferences;

public class StatusStoreImpl implements StatusStore {
	final private String KEY_UPDATED = "status_updated";
	final private String KEY_STATUS = "status_status";

	SharedPreferences mPreferences;

	public StatusStoreImpl(SharedPreferences preferences) {
		super();
		
		mPreferences = preferences;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.rdrei.android.simstatus.StatusStore#getLastStatus()
	 */
	@Override
	public StatusResult loadStatus() {
		final int statusId = mPreferences.getInt(KEY_STATUS,
				Status.UNKNOWN.ordinal());
		final Status status = Status.values()[statusId];
		final long updated = mPreferences.getLong(KEY_UPDATED, 0);
		
		if (status != Status.UNKNOWN) {
			return new StatusResult(status, new Date(updated));
		}
		
		return new StatusResult();
	}

	@Override
	public void saveStatus(StatusResult result) {
		mPreferences.edit()
			.putInt(KEY_STATUS, result.status.ordinal())
			.putLong(KEY_UPDATED, result.updated.getTime())
			.commit();
	}
}