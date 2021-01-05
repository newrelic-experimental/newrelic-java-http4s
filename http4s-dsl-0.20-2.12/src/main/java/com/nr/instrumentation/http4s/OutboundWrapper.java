package com.nr.instrumentation.http4s;

import java.util.List;
import java.util.logging.Level;

import org.http4s.Header;
import org.http4s.Header.Raw;
import org.http4s.util.CaseInsensitiveString;

import com.newrelic.api.agent.HeaderType;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.OutboundHeaders;

import scala.collection.JavaConversions;
import scala.collection.JavaConverters;
import scala.collection.Seq;


public class OutboundWrapper implements OutboundHeaders {
	
	private Seq<Header> headers = null;
	
	public OutboundWrapper(Seq<Header> h) {
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
		List<Header> orig = JavaConversions.seqAsJavaList(headers);
		orig.add(header);
		headers =  JavaConverters.asScalaIteratorConverter(orig.iterator()).asScala().toSeq();
		NewRelic.getAgent().getLogger().log(Level.FINEST, "Added header({0},{1})", name,value);
	}

	public Seq<Header> getHeaders() {
		return headers;
	}
}
