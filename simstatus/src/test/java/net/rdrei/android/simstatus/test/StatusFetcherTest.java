package net.rdrei.android.simstatus.test;

import net.rdrei.android.simstatus.SimStatusApplication;
import net.rdrei.android.simstatus.StatusFetcher;
import net.rdrei.android.simstatus.StatusFetcherImpl;
import net.rdrei.android.simstatus.StatusResult.Status;

import org.eclipse.jetty.server.Request;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(TestRunner.class)
public class StatusFetcherTest extends TestServer {
	@Inject
	StatusFetcher mStatusFetcher;

	private static RequestHandler sHandler;
	private static String sUrl;

	@Before
	public void setUpInjection() {
		final ObjectGraph objectGraph = ((SimStatusApplication) Robolectric.application).getObjectGraph();
		objectGraph
				.plus(new StatusFetcherModule())
				.inject(this);
	}

	/**
	 * Clear handler
	 */
	@After
	public void clearHandler() {
		sHandler = null;
	}

	/**
	 * Set up server
	 *
	 * @throws Exception
	 */
	@BeforeClass
	public static void startServer() throws Exception {
		sUrl = setUpServer(new RequestHandler() {

			@Override
			public void handle(final String target, final Request baseRequest,
					final HttpServletRequest request,
					final HttpServletResponse response) throws IOException,
					ServletException {
				if (sHandler != null)
					sHandler.handle(target, baseRequest, request, response);
			}

			@Override
			public void handle(final Request request,
					final HttpServletResponse response) {
				if (sHandler != null)
					sHandler.handle(request, response);
			}
		});
	}

	@Test
	public void testMaybeRequest() {
		sHandler = new TestServer.RequestHandler() {

			@Override
			public void handle(final Request request,
					final HttpServletResponse response) {
				response.setStatus(java.net.HttpURLConnection.HTTP_OK);
				write("Maybe");
			}
		};

		final Status status = mStatusFetcher.fetchStatus(sUrl);
		assertThat(status.toString(), is("MAYBE"));
	}

	@Module(injects = {StatusFetcherTest.class})
	public class StatusFetcherModule {
		@Provides
		public StatusFetcher provideStatusFetcher() {
			return new StatusFetcherImpl();
		}
	}

}
