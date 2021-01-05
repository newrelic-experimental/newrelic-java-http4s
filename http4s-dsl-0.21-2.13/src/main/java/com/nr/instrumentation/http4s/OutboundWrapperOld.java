package com.nr.instrumentation.http4s;

import java.util.List;
import java.util.logging.Level;

import org.http4s.Header;
import org.http4s.Header.Raw;
import org.http4s.util.CaseInsensitiveString;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.OutboundHeaders;

import scala.collection.immutable.Seq;
import scala.jdk.javaapi.CollectionConverters;

public class OutboundWrapperOld implements OutboundHeaders {
	
	private Seq<Header> headers = null;
	
	public OutboundWrapperOld(Seq<Header> h) {
		headers = h;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public void setHeader(String name, String value) {
		CaseInsensitiveString s = new CaseInsensitiveString(name);
		Raw header = new Raw(s, value);
		List<Header> orig = CollectionConverters.asJava(headers);
		orig.add(header);
		headers =  CollectionConverters.asScala(orig).toSeq();
	}

	public Seq<Header> getHeaders() {
		return headers;
	}
}
