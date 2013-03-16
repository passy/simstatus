package net.rdrei.android.simstatus;


public class StatusStoreImpl implements StatusStore {
	
	/* (non-Javadoc)
	 * @see net.rdrei.android.simstatus.StatusStore#getLastStatus()
	 */
	@Override
	public Status getLastStatus() {
		return Status.UNKNOWN;
	}

}
