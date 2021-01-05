package com.nr.instrumentation.http4s;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import org.http4s.Header;
import org.http4s.Method;
import org.http4s.Request;

import com.newrelic.api.agent.ExtendedRequest;
import com.newrelic.api.agent.HeaderType;

import scala.Option;
import scala.collection.Iterator;
import scala.collection.Seq;
import scala.collection.immutable.Map;
import scala.jdk.javaapi.CollectionConverters;

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
			java.util.Map<String, String> javaMap = CollectionConverters.asJava(params);
			List<String> values = new ArrayList<String>();
			
			for(String key : javaMap.keySet()) {
				String value = javaMap.get(key);
				values.add(value);
			}
			
			String[] results = new String[values.size()];
			values.toArray(results);
			return results;
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

	@SuppressWarnings("unchecked")
	@Override
	public String getHeader(String name) {
		scala.collection.immutable.List<Header> headers = request.headers();
		
		java.util.List<Header> javaList = CollectionConverters.asJava(headers);

		for(Header header : javaList) {
			 org.http4s.util.CaseInsensitiveString headerName = header.name();
			 if(headerName.equals(name)) {
				 return header.value();
			 }
		}
//		if (headers != null) {
//			CaseInsensitiveString key = new CaseInsensitiveString(name);
//			Option<Header> value = headers.
//			if(value != null && !value.isEmpty()) {
//				Header header = value.get();
//				return header == null ? null : header.value();
//			}
//		}
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
