package com.nr.instrumentation.http4s.client;

import org.http4s.Header;
import org.http4s.Response;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.InboundHeaders;

import scala.jdk.javaapi.*;

@SuppressWarnings("unchecked")
public class InboundWrapper<F> implements InboundHeaders {
	
	private Response<F> response = null;
	
	public InboundWrapper(Response<F> resp) {
		response = resp;
	}

	@Override
	public String getHeader(String name) {
		scala.collection.immutable.List<Header> headers = response.headers();
		
		java.util.List<Header> javaList = CollectionConverters.asJava(headers);

		for(Header header : javaList) {
			 org.http4s.util.CaseInsensitiveString headerName = header.name();
			 if(headerName.equals(name)) {
				 return header.value();
			 }
		}
		return null;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

}
