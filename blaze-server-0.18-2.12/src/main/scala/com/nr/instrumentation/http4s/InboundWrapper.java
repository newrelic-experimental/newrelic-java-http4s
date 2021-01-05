package com.nr.instrumentation.http4s;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;

import org.http4s.Header;
import org.http4s.Headers;
import org.http4s.Method;
import org.http4s.Request;
import org.http4s.util.CaseInsensitiveString;

import com.newrelic.api.agent.ExtendedRequest;
import com.newrelic.api.agent.HeaderType;

import scala.Option;
import scala.collection.Iterator;
import scala.collection.Seq;
import scala.collection.immutable.Map;

public class InboundWrapper<F> extends ExtendedRequest {
	
	private Request<F> request = null;
	
	public InboundWrapper(Request<F> r) {
		request = r;
	}

	@Override
	public String getRequestURI() {
		return request.uri().renderString();
	}

	@Override
	public String getRemoteUser() {
		Option<String> remoteUser = request.remoteUser();
		if(remoteUser.nonEmpty()) {
			return remoteUser.get();
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration getParameterNames() {
		Map<String, Seq<String>> params = request.multiParams();
		if(params != null) {
			scala.collection.immutable.Set<String> keys = params.keySet();
			Iterator<String> iterator = keys.iterator();
			ArrayList<String> keyNames = new ArrayList<String>();
			
			while(iterator.hasNext()) {
				String key = iterator.next();
				keyNames.add(key);
			}
			return Collections.enumeration(keyNames);
		}
		return null;
	}

	@Override
	public String[] getParameterValues(String name) {
		Map<String, String> params = request.params();
		if (params != null) {
			java.util.Map<String, String> jParams = scala.collection.JavaConversions.mapAsJavaMap(params);
			if (jParams != null) {
				Collection<String> values = jParams.values();
				String[] paramValues = new String[values.size()];
				values.toArray(paramValues);
				return paramValues;
			} 
		}
		return null;
	}

	@Override
	public Object getAttribute(String name) {
		return null;
	}

	@Override
	public String getCookieValue(String name) {
		return null;
	}

	@Override
	public HeaderType getHeaderType() {
		return HeaderType.HTTP;
	}

	@Override
	public String getHeader(String name) {
		Headers headers = request.headers();
		
		
		if (headers != null) {
			CaseInsensitiveString key = new CaseInsensitiveString(name);
			Option<Header> value = headers.get(key);
			if(value != null && !value.isEmpty()) {
				Header header = value.get();
				return header == null ? null : header.value();
			}
		}
		return null;
	}

	@Override
	public String getMethod() {
		Method method = request.method();
		if(method != null) {
			return method.name();
		}
		return null;
	}

}
