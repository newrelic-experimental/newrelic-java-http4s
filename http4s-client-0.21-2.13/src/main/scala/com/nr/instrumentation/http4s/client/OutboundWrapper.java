package com.nr.instrumentation.http4s.client;

import java.util.ArrayList;
import java.util.List;

import org.http4s.Header;
import org.http4s.Request;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.OutboundHeaders;
import scala.jdk.javaapi.CollectionConverters;

@SuppressWarnings("unchecked")
public class OutboundWrapper implements OutboundHeaders {
	
	private Request request = null;
	
	public OutboundWrapper(Request req) {
		request = req;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public void setHeader(String name, String value) {
		org.http4s.Header raw = org.http4s.Header.apply(name,value);
		List<org.http4s.Header> list = new ArrayList<org.http4s.Header>();
		list.add(raw);
		
		request.putHeaders(CollectionConverters.asScala(list).toSeq());
	}

}
