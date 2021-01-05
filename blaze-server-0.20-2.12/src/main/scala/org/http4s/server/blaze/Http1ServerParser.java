package org.http4s.server.blaze;

import org.http4s.HttpVersion;
import org.http4s.ParseFailure;
import org.http4s.Request;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.TransactionNamePriority;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import fs2.internal.FreeC;
import scala.Tuple2;
import scala.runtime.BoxedUnit;
import scala.util.Either;

@Weave
public abstract class Http1ServerParser<F> {

	@Trace
	public Either<Tuple2<ParseFailure, HttpVersion>, Request<F>> collectMessage(FreeC<?, BoxedUnit> f, io.chrisdavenport.vault.Vault map) {
		Either<Tuple2<ParseFailure, HttpVersion>, Request<F>> result = Weaver.callOriginal();
		
	
		return result;
	}
	
	@Trace(dispatcher=true)
	public boolean submitRequestLine(java.lang.String methodString, java.lang.String uri, java.lang.String scheme, int minor, int major) {
		Transaction transaction = NewRelic.getAgent().getTransaction();
		if(!transaction.isWebTransaction()) {
			transaction.convertToWebTransaction();
		}

		if(!transaction.isTransactionNameSet()) {
			transaction.setTransactionName(TransactionNamePriority.CUSTOM_LOW, true, "Blaze", methodString+" "+uri);
		}
		try {
			Thread.sleep(1000L);
		} catch(Exception e) {
			
		}
		return Weaver.callOriginal();
	}

}
