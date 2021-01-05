package org.http4s;

import com.newrelic.api.agent.weaver.Weave;
import com.nr.instrumentation.http4s.Utils;

@Weave
public abstract class Http4sVersion {

	public Http4sVersion(int major,int minor) {
		if(!Utils.versionSet) {
			Utils.setVersion(major, minor);
		}
	}
}
