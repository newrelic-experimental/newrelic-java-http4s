package org.http4s.server;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.Interface)
public abstract class ServerBuilder<F> {

	public ServerBuilder<F> bindHttp(int port, java.lang.String prefix) {
		
		NewRelic.setAppServerPort(port);
		NewRelic.setProductName("HTTP4s");
		return Weaver.callOriginal();
	}
}
