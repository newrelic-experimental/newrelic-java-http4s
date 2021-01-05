package org.http4s.dsl.impl;

import org.http4s.Header;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.http4s.OutboundWrapperOld;

@Weave(type=MatchType.Interface)
public abstract class EntityResponseGenerator<F,G> {
	
	@Trace
	public F apply(scala.collection.immutable.Seq<Header> headers, cats.Applicative<F> app) {
		OutboundWrapperOld wrapper = new OutboundWrapperOld(headers);
		NewRelic.getAgent().getTracedMethod().addOutboundRequestHeaders(wrapper);
		headers = wrapper.getHeaders();
		return Weaver.callOriginal();
	}
	
	@Trace
	public <A> F apply(A a, scala.collection.immutable.Seq<org.http4s.Header> headers, cats.Applicative<F> app, org.http4s.EntityEncoder<G, A> encoder) {
		OutboundWrapperOld wrapper = new OutboundWrapperOld(headers);
		NewRelic.getAgent().getTracedMethod().addOutboundRequestHeaders(wrapper);
		headers = wrapper.getHeaders();
		return Weaver.callOriginal();
	}

}
