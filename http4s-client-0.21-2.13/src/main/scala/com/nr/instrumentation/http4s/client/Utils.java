package com.nr.instrumentation.http4s.client;

import java.net.URI;

import org.http4s.Uri;

public class Utils {

	
	public static URI getURI(Uri uri) {
		String s = uri.toString();
		return URI.create(s);
	}
}
