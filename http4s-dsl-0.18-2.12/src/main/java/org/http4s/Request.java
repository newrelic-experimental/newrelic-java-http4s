package org.http4s;

import com.newrelic.api.agent.Token;
//import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import scala.Option;
import scala.collection.immutable.Map;
import scala.collection.Seq;

@Weave(type=MatchType.BaseClass)
public abstract class Request<F> {
	
	@NewField
	public Token token = null;
	
//	@NewField
//	public Segment segment = null;
	
	public Request(Method method, Uri uri, HttpVersion httpVersion, Headers headers, fs2.internal.FreeC<?, scala.runtime.BoxedUnit> f, AttributeMap attrs) {
		
	}

	public abstract Uri uri();
	public abstract Option<String> remoteUser();
	public abstract Map<String, Seq<String>> multiParams();
	public abstract Map<String, String> params();
	public abstract Headers headers();
	public abstract Method method();
}
