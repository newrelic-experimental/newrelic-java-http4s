package com.nr.instrumentation.http4s.client;

import java.net.URI;

import org.http4s.Response;
import org.http4s.Uri;

import com.newrelic.api.agent.HttpParameters;
import com.newrelic.api.agent.NewRelic;

import scala.Function1;

public class NRResponseFunction1<F> implements Function1<org.http4s.Response<F>, F> {

	
	private Function1<org.http4s.Response<F>, F>  delegate = null;
	private Uri uri = null;
	private String proc = null;
	
	public NRResponseFunction1(Function1<org.http4s.Response<F>, F> f, Uri u, String p) {
		delegate = f;
		uri = u;
		proc = p;
	}
	
	@Override
	public F apply(Response<F> response) {
		HttpParameters params = HttpParameters.library("HTTP4S").uri(URI.create(uri.toString())).procedure(proc).inboundHeaders(new InboundWrapper(response)).build();
		NewRelic.getAgent().getTracedMethod().reportAsExternal(params);
		return delegate.apply(response);
	}

}
