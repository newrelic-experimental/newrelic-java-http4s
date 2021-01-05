package com.nr.instrumentation.http4s.client;

import java.net.URI;

import org.http4s.Response;
import org.http4s.Uri;
import org.http4s.Uri.Host;
import org.http4s.Uri.Scheme;

import com.newrelic.api.agent.HttpParameters;
import com.newrelic.api.agent.NewRelic;

import scala.Function1;
import scala.Option;

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
		Option<Host> hostOption = uri.host();
		Option<Object> portOption = uri.port();
		Option<Scheme> schemeOption = uri.scheme();
		String path = uri.path();

		String host = "Unknown";
		Integer port = null;
		String scheme = "http";
		
		if(hostOption.nonEmpty()) {
			Host h = hostOption.get();
			host = h.value();
		}
		
		if(portOption.nonEmpty()) {
			Object portObj = portOption.get();
			if(portObj instanceof Number) {
				port = ((Number)portObj).intValue();
			} else {
				String s = portObj.toString();
				try {
					Float f = Float.parseFloat(s);
					port = f.intValue();
				} catch(Exception e) {
					
				}
			}
		}
		
		if(schemeOption.nonEmpty()) {
			Scheme schemeObj = schemeOption.get();
			scheme = schemeObj.value();
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append(scheme);
		sb.append("://");
		sb.append(host);
		if(port != null) {
			sb.append(':');
			sb.append(port);
		}
		sb.append('/');
		sb.append(path);
		
		HttpParameters params = HttpParameters.library("HTTP4S").uri(URI.create(sb.toString())).procedure(proc).inboundHeaders(new InboundWrapper<F>(response)).build();
		NewRelic.getAgent().getTracedMethod().reportAsExternal(params);
		return delegate.apply(response);
	}

}
