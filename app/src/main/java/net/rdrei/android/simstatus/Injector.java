package net.rdrei.android.simstatus;

import dagger.ObjectGraph;

public class Injector {
	private static ObjectGraph objectGraph;

	public static synchronized void inject(Object target) {
		objectGraph.inject(target);
	}

	public static synchronized void setModule(Object module) {
		objectGraph = ObjectGraph.create(module);
	}

}