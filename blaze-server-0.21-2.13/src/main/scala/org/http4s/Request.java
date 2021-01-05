package org.http4s;


import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.TransactionNamePriority;
//import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.WeaveAllConstructors;
import com.newrelic.api.agent.weaver.Weaver;

import scala.Option;
import scala.collection.Seq;
import scala.collection.immutable.Map;

@Weave(type=MatchType.BaseClass)
public abstract class Request<F> {
	
	@NewField
	public Token token = null;
	
	@WeaveAllConstructors
	public Request() {
		Transaction transaction = NewRelic.getAgent().getTransaction();
		Token t = transaction.getToken();
		if(t.isActive()) {
			token = t;
			if(!transaction.isWebTransaction()) {
				transaction.convertToWebTransaction();
			}
//			if(!transaction.isTransactionNameSet()) {
//				String path = uri.getPath();
//				if(path.startsWith("/")) path = path.substring(1);
//				transaction.setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "HTTP4S", method.name(),path);
//			}
		}
	}
//	
//	public	Request(Method method, URI uri, HttpVersion httpVersion, scala.collection.immutable.List<org.http4s.Header> headers, fs2.internal.FreeC<?, scala.runtime.BoxedUnit> f, io.chrisdavenport.vault.Vault attrs) {
//		Transaction transaction = NewRelic.getAgent().getTransaction();
//		Token t = transaction.getToken();
//		if(t.isActive()) {
//			token = t;
//			if(!transaction.isWebTransaction()) {
//				transaction.convertToWebTransaction();
//			}
//			if(!transaction.isTransactionNameSet()) {
//				String path = uri.getPath();
//				if(path.startsWith("/")) path = path.substring(1);
//				transaction.setTransactionName(TransactionNamePriority.FRAMEWORK_LOW, false, "HTTP4S", method.name(),path);
//			}
//		}
//	}

	public abstract Uri uri();
	public abstract Option<String> remoteUser();
	public abstract Map<String, Seq<String>> multiParams();
	public abstract Map<String, String> params();
	public abstract Method method();
	public abstract scala.collection.immutable.List headers(); 
	

//	@Trace(async=true)
//	private Request<F> copy(org.http4s.Method method, org.http4s.Uri uri, org.http4s.HttpVersion v, scala.collection.immutable.List<org.http4s.Header> headers, fs2.internal.FreeC<?, scala.runtime.BoxedUnit> f, io.chrisdavenport.vault.Vault attrs) {
//		if(token != null) {
//			token.linkAndExpire();
//			token = null;
//		}
//		Request<F> copied = Weaver.callOriginal();
//		return copied;
//	}

}
