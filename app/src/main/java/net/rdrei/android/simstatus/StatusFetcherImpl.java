package net.rdrei.android.simstatus;

import net.rdrei.android.simstatus.StatusResult.Status;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;

public class StatusFetcherImpl implements StatusFetcher {
	
	private static final String URL = "http://simcitystatus.com/status.php";

	/* (non-Javadoc)
	 * @see net.rdrei.android.simstatus.StatusFetcher#fetchStatus()
	 */
	@Override
	public Status fetchStatus() {
		HttpRequest response;
		
		try {
			response = HttpRequest.get(URL);
			if (response.code() != 200) {
				return Status.ERROR;
			}
		} catch (HttpRequestException err) {
			return Status.ERROR;
		}
		
		return responseToStatus(response.body());
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
