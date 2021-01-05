package com.nr.instrumentation.http4s;

import java.lang.reflect.Method;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

import scala.PartialFunction;

public class NRPartialFunction<F> implements PartialFunction<org.http4s.Request<F>, F> {
	
	public static boolean isInstrumented = false;
	
	public static final Method methodToInstrument;
	
	static {
		Class<?> thisClass = NRPartialFunction.class;
		Method[] methods = thisClass.getMethods();
		boolean found = false;
		Method foundMethod = null;
		for(int i=0;i<methods.length && !found; i++) {
			Method method = methods[i];
			if(method.getName().equalsIgnoreCase("apply")) {
				foundMethod = method;
				found = true;
			}
		}
		methodToInstrument = foundMethod;

	}
	
	private PartialFunction<org.http4s.Request<F>, F> delegate = null;
	
	public NRPartialFunction(PartialFunction<org.http4s.Request<F>, F> d) {
		delegate = d;
	}

	@Override
	@Trace(dispatcher=true)
	public F apply(org.http4s.Request<F> v1) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HTTP4s","Request"});
		if(v1.token == null) {
			v1.token = NewRelic.getAgent().getTransaction().getToken();
		}
//		if(v1.segment == null) {
//			v1.segment = NewRelic.getAgent().getTransaction().startSegment("Request");
//		}
		NewRelic.getAgent().getTransaction().convertToWebTransaction();
		NewRelic.getAgent().getTransaction().setWebRequest(new InboundWrapper<F>(v1));
		F value = delegate.apply(v1);
		return value;
	}

	@Override
	public boolean isDefinedAt(org.http4s.Request<F> x) {
		return delegate.isDefinedAt(x);
	}

}
