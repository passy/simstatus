/*
 * Copyright (c) 2011 Kevin Sawicki <kevinsawicki@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package net.rdrei.android.simstatus.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.nio.SelectChannelConnector;
import org.junit.AfterClass;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

/**
 * Base test case that provides a running HTTP server
 */
public class TestServer {

	/**
	 * Simplified handler
	 */
	protected static abstract class RequestHandler extends AbstractHandler {

		private Request request;

		private HttpServletResponse response;

		/**
		 * Handle request
		 *
		 * @param request
		 * @param response
		 */
		public abstract void handle(Request request,
				HttpServletResponse response);

		/**
		 * Read content
		 *
		 * @return content
		 */
		protected byte[] read() {
			final ByteArrayOutputStream content = new ByteArrayOutputStream();
			final byte[] buffer = new byte[8196];
			int read;
			try {
				final InputStream input = request.getInputStream();
				while ((read = input.read(buffer)) != -1)
					content.write(buffer, 0, read);
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
			return content.toByteArray();
		}

		/**
		 * Write value
		 *
		 * @param value
		 */
		protected void write(final String value) {
			try {
				response.getWriter().print(value);
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * Write line
		 *
		 * @param value
		 */
		protected void writeln(final String value) {
			try {
				response.getWriter().println(value);
			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public void handle(final String target, final Request baseRequest,
				final HttpServletRequest request,
				final HttpServletResponse response) throws IOException,
				ServletException {
			this.request = (Request) request;
			this.response = response;
			this.request.setHandled(true);
			handle(this.request, response);
		}

	}

	/**
	 * Server
	 */
	protected static Server server;

	/**
	 * Set up server with handler
	 *
	 * @param handler
	 * @return port
	 * @throws Exception
	 */
	public static String setUpServer(final Handler handler) throws Exception {
		server = new Server();
		if (handler != null)
			server.setHandler(handler);
		final Connector connector = new SelectChannelConnector();
		connector.setPort(0);
		server.setConnectors(new Connector[] { connector });
		server.start();
		return "http://localhost:" + connector.getLocalPort();
	}

	/**
	 * Tear down server if created
	 *
	 * @throws Exception
	 */
	@AfterClass
	public static void tearDown() throws Exception {
		if (server != null)
			server.stop();
	}
}