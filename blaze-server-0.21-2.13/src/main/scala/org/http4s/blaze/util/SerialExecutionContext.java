package org.http4s.blaze.util;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.http4s.NRRunnable;

@Weave
public abstract class SerialExecutionContext {

	public void execute(Runnable r) {
		NRRunnable wrapper = new NRRunnable(r, NewRelic.getAgent().getTransaction().getToken());
		r = wrapper;
		Weaver.callOriginal();
	}
}
