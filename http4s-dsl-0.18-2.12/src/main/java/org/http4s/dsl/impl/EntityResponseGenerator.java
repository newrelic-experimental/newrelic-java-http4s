package org.http4s.dsl.impl;

import org.http4s.Header;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.http4s.OutboundWrapper;

@SuppressWarnings("deprecation")
@Weave(type=MatchType.Interface)
public abstract class EntityResponseGenerator<F> {
	
	@Trace
	public <A> F apply(A a, scala.collection.Seq<org.http4s.Header> headers, cats.Monad<F> m, org.http4s.EntityEncoder<F, A> ec) {
		OutboundWrapper wrapper = new OutboundWrapper(headers);
		AgentBridge.getAgent().getTransaction().getCrossProcessState().processOutboundResponseHeaders(wrapper, 0);
		headers = wrapper.getHeaders();
		return Weaver.callOriginal();
	}

	@Trace
	public F apply(scala.collection.Seq<Header> headers, cats.Applicative<F> app) {
		OutboundWrapper wrapper = new OutboundWrapper(headers);
		AgentBridge.getAgent().getTransaction().getCrossProcessState().processOutboundResponseHeaders(wrapper, 0);
		headers = wrapper.getHeaders();
		return Weaver.callOriginal();
	}
}
