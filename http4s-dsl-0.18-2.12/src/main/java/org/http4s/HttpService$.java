package org.http4s;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.http4s.NRFunction1Wrapper;
import com.nr.instrumentation.http4s.NRPartialFunction;

import cats.Applicative;
import cats.data.Kleisli;


@Weave
public class HttpService$ {

	@Trace(dispatcher=true)
	public <F> Kleisli<?, Request<F>, Response<F>> empty(Applicative<F> app) {
		Kleisli<?, Request<F>, Response<F>> k = Weaver.callOriginal();
		
		return k;
	}
	
	@Trace(dispatcher=true)
	public <F> Kleisli<?, Request<F>, Response<F>> apply(scala.PartialFunction<Request<F>, F> req, Applicative<F> app) {
		if(!NRPartialFunction.isInstrumented) {
			AgentBridge.instrumentation.instrument(NRPartialFunction.methodToInstrument, "");
			NRPartialFunction.isInstrumented = true;
		}
		NRPartialFunction<F> pf = new NRPartialFunction<F>(req);
		req = pf;
		Kleisli<?, Request<F>, Response<F>> k = Weaver.callOriginal();
	
		return k;
	}
	
	@Trace(dispatcher=true)
	public <F> Kleisli<?, Request<F>, Response<F>> lift(scala.Function1<Request<F>, F> req, cats.Functor<F> f) {
		if(!NRFunction1Wrapper.isInstrumented) {
			AgentBridge.instrumentation.instrument(NRFunction1Wrapper.methodToInstrument, "");
			NRFunction1Wrapper.isInstrumented = true;
		}
		NRFunction1Wrapper<F> wrapper = new NRFunction1Wrapper<F>(req);
		req=wrapper;
		Kleisli<?, Request<F>, Response<F>> k = Weaver.callOriginal();
		
		return k;
	}

}
