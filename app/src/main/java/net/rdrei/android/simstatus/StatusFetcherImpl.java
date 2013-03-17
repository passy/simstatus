package net.rdrei.android.simstatus;

import net.rdrei.android.simstatus.StatusStore.Status;

import com.github.kevinsawicki.http.HttpRequest;

public class StatusFetcherImpl implements StatusFetcher {
	
	private static final String URL = "http://simcitystatus.com/status.php";

	/* (non-Javadoc)
	 * @see net.rdrei.android.simstatus.StatusFetcher#fetchStatus()
	 */
	@Override
	public Status fetchStatus() {
		final String response = HttpRequest.get(URL).body();
		
		return responseToStatus(response);
	}

	private Status responseToStatus(String response) {
		if ("YES".equals(response)) {
			return Status.YES;
		} else if ("NO".equals(response)) {
			return Status.NO;
		} else if ("MAYBE".equals(response)) {
			return Status.MAYBE;
		} else {
			return Status.UNKNOWN;
		}
	}
}
