package org.http4s.client;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.http4s.client.NRResponseFunction1;
import com.nr.instrumentation.http4s.client.OutboundWrapper;

@Weave(type=MatchType.Interface)
public abstract class Client<F> {

	public <A> F expectOr(org.http4s.Request<F> req, scala.Function1<org.http4s.Response<F>, F> f, org.http4s.EntityDecoder<F, A> decoder) {
		OutboundWrapper wrapper = new OutboundWrapper(req);
		NewRelic.getAgent().getTracedMethod().addOutboundRequestHeaders(wrapper);
		NRResponseFunction1<F> funcWrapper = new NRResponseFunction1<F>(f, req.uri(), "expectOr");
		f = funcWrapper;
		return Weaver.callOriginal();
	}

	public <A> F fetch(org.http4s.Request<F> req, scala.Function1<org.http4s.Response<F>, F> f) {
		OutboundWrapper wrapper = new OutboundWrapper(req);
		NewRelic.getAgent().getTracedMethod().addOutboundRequestHeaders(wrapper);
		NRResponseFunction1<F> funcWrapper = new NRResponseFunction1<F>(f, req.uri(), "fetch");
		f = funcWrapper;
		return Weaver.callOriginal();
	}
	
}
