package com.nr.instrumentation.http4s;

import java.util.logging.Level;

import org.http4s.Request;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

import scala.Function1;
import scala.Option;
import scala.PartialFunction;

public class NRPartialFunction<F> implements PartialFunction<org.http4s.Request<F>, F> {
	

	private static boolean isTransformed = false;
	
	
	private PartialFunction<org.http4s.Request<F>, F> delegate = null;
	
	public NRPartialFunction(PartialFunction<org.http4s.Request<F>, F> d) {
		NewRelic.getAgent().getLogger().log(Level.FINE, "constructing NRPartialFunction using {0} of type{1}", d,d.getClass().getName());
		delegate = d;
		if(!isTransformed) {
			isTransformed = true;
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
		}
	}

	@Override
	@Trace(async=true)
	public F apply(org.http4s.Request<F> req) {
		NewRelic.getAgent().getLogger().log(Level.FINE, "call to NRPartialFunction.apply({0}), request token: {1}, will call {2}.apply", req,req.token,delegate.getClass().getName());
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HTTP4s","Request"});
		if(req.token != null) {
			req.token.linkAndExpire();
			req.token = null;
		}
		NewRelic.getAgent().getTransaction().convertToWebTransaction();
		NewRelic.getAgent().getTransaction().setWebRequest(new InboundWrapper<F>(req));
		F value = delegate.apply(req);
		return value;
	}

	@Override
	public boolean isDefinedAt(org.http4s.Request<F> x) {
		return delegate.isDefinedAt(x);
	}

	@Trace(async=true)
	public Object applyOrElse(Request req, scala.Function1<Request, Object> f) {
		NewRelic.getAgent().getLogger().log(Level.FINE, "call to NRPartialFunction.applyOrElse({0}), will call {1}.applyOrElse", req,delegate.getClass().getName());
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HTTP4s","Request"});
		if(req.token != null) {
			req.token.linkAndExpire();
			req.token = null;
		}
		NewRelic.getAgent().getTransaction().convertToWebTransaction();
		NewRelic.getAgent().getTransaction().setWebRequest(new InboundWrapper<F>(req));
		Object value = delegate.applyOrElse(req,f);
		return value;
	}

	@Override
	public Function1<Request<F>, Option<F>> lift() {
		Function1<Request<F>, Option<F>> result =  PartialFunction.super.lift();
		NRFunction1Wrapper2<F> wrapper = new NRFunction1Wrapper2<F>(result);
		return wrapper;
	}

}
