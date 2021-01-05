package com.nr.instrumentation.http4s;

import java.util.ArrayList;
import java.util.List;

import org.http4s.Header;
import org.http4s.Header.Raw;
import org.http4s.Response;
import org.http4s.Status;
import org.http4s.headers.Content$minusType;
import org.http4s.util.CaseInsensitiveString;

import com.newrelic.api.agent.ExtendedResponse;
import com.newrelic.api.agent.HeaderType;

import scala.Option;
import scala.collection.JavaConversions;
import scala.collection.Seq;

public class OutboundResponse<F> extends ExtendedResponse {
	
	private Response<F> response = null;
	
	public OutboundResponse(Response<F> r) {
		response = r;
	}

	@Override
	public int getStatus() throws Exception {
		Status status = response.status();
		return status.code();
	}

	@Override
	public String getStatusMessage() throws Exception {
		Status status = response.status();
		return status.reason();
	}

	@Override
	public String getContentType() {
		Option<Content$minusType> contentType = response.contentType();
		
		return contentType.toString();
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public void setHeader(String name, String value) {
		CaseInsensitiveString s = new CaseInsensitiveString(name);
		Raw raw = new Raw(s, value);
		List<Header> list = new ArrayList<Header>();
		list.add(raw);
		Seq<Header> seq = JavaConversions.asScalaIterator(list.iterator()).toSeq();
		
		response.putHeaders(seq);

	}

	@Override
	public long getContentLength() {
		return 0;
	}

}
