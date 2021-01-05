package com.nr.instrumentation.http4s;

import java.lang.reflect.Method;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TransactionNamePriority;

import scala.Function1;

public class NRFunction1Wrapper<F> implements Function1<org.http4s.Request<F>, F> {
	
	public static boolean isInstrumented = false;
	
	public static final Method methodToInstrument;
	
	static {
		Class<?> thisClass = NRFunction1Wrapper.class;
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
	private Function1<org.http4s.Request<F>, F> delegate = null;
	
	public NRFunction1Wrapper(Function1<org.http4s.Request<F>,F> d) {
		delegate = d;
	}

	@Override
	@Trace(dispatcher=true)
	public F apply(org.http4s.Request<F> v1) {
		String path = v1.uri().path();
		if(path != null && !path.isEmpty()) {
			NewRelic.getAgent().getTransaction().setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "HTTP4S", path);
		}
		if(v1.token != null) {
			v1.token.linkAndExpire();
			v1.token = null;
		}
		NewRelic.getAgent().getTransaction().convertToWebTransaction();
//		NewRelic.getAgent().getTransaction().setWebRequest(new InboundWrapper<F>(v1));
		F f = delegate.apply(v1);
		return f;
	}

}
