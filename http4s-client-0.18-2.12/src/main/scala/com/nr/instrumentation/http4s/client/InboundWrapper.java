package com.nr.instrumentation.http4s.client;

import org.http4s.Header;
import org.http4s.Headers;
import org.http4s.Response;
import org.http4s.util.CaseInsensitiveString;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.InboundHeaders;

import scala.Option;

public class InboundWrapper implements InboundHeaders {
	
	private Response response = null;
	
	public InboundWrapper(Response resp) {
		response = resp;
	}

	@Override
	public String getHeader(String name) {
		Headers headers = response.headers();
		CaseInsensitiveString cis = new CaseInsensitiveString(name);
		Option<Header> option = headers.get(cis);
		if(option.nonEmpty()) {
			
			return option.get().value();
		}
		return null;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

}
