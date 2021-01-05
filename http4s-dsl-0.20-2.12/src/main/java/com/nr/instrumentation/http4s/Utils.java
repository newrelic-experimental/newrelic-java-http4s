package com.nr.instrumentation.http4s;

import com.newrelic.api.agent.NewRelic;

public class Utils {

	public static boolean versionSet = false;
	
	public static void setVersion(int major, int minor) {
		String versionStr = major + "." + minor;
		
		NewRelic.setServerInfo("HTTP4s", versionStr);
		versionSet = true;
	}
}
