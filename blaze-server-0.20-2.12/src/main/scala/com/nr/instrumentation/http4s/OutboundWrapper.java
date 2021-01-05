package com.nr.instrumentation.http4s;

import java.util.ArrayList;
import java.util.List;

import org.http4s.Header;
import org.http4s.Headers;
import org.http4s.Response;

import com.newrelic.api.agent.ExtendedResponse;
import com.newrelic.api.agent.HeaderType;

import scala.Option;

public class OutboundWrapper extends ExtendedResponse {
	
	Response response = null;
	
	public OutboundWrapper(Response resp) {
		response = resp;
	}

	@Override
	public int getStatus() throws Exception {
		// TODO Auto-generated method stub
		return response.status().code();
	}

	@Override
	public String getStatusMessage() throws Exception {
		return response.status().reason();
	}

	@Override
	public String getContentType() {
		Option<org.http4s.headers.Content$minusType> ct = response.contentType();
		if(ct.nonEmpty()) {
			return ct.get().name().toString();
		}
		return null;
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
		
		response.putHeaders(scala.collection.JavaConversions.asScalaBuffer(list).toList());
	}

	@Override
	public long getContentLength() {
		Option<Object> cl = response.contentLength();
		if(cl.nonEmpty()) {
			Object obj = cl.get();
			if(obj instanceof Number) {
				 return ((Number)obj).longValue();
			} else {
				String s = obj.toString();
				try {
					return Long.parseLong(s);
				} catch(NumberFormatException e) {
					return 0;
				}
			}
		}
		return 0;
	}

}
