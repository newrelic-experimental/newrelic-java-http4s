package org.http4s;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.TransactionNamePriority;
//import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;

import scala.Option;
import scala.collection.Seq;
import scala.collection.immutable.Map;

@Weave(type=MatchType.BaseClass)
public abstract class Request<F> {
	
	@NewField
	public Token token = null;
	
//	@NewField
//	public Segment segment = null;
	
	public Request(Method method, Uri uri, HttpVersion httpVersion, Headers headers, fs2.internal.FreeC<?, scala.runtime.BoxedUnit> f, AttributeMap attrs) {
		Transaction transaction = NewRelic.getAgent().getTransaction();
		Token t = transaction.getToken();
		if(t.isActive()) {
			token = t;
			if(!transaction.isTransactionNameSet()) {
				transaction.setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "HTTP4S", uri.path());
			}
		}
		
	}

	public abstract Uri uri();
	public abstract Option<String> remoteUser();
	public abstract Map<String, Seq<String>> multiParams();
	public abstract Map<String, String> params();
	public abstract Headers headers();
	public abstract Method method();
}
