package org.http4s.server.blaze;

import java.nio.ByteBuffer;

import org.http4s.Request;
import org.http4s.Response;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.http4s.ResponseWrapper;

import scala.Function0;
import scala.concurrent.Future;

@Weave(type=MatchType.BaseClass)
public abstract class Http1ServerStage<F> {
	
	@NewField
	private Token token = null;
	
	 public Http1ServerStage(cats.data.Kleisli<F, org.http4s.Request<F>, org.http4s.Response<F>> httpApp, scala.Function0<io.chrisdavenport.vault.Vault> attrs, scala.concurrent.ExecutionContext ec, int i1, int i2, int i3, scala.Function1<org.http4s.Request<F>, scala.PartialFunction<java.lang.Throwable, F>> f2, scala.concurrent.duration.Duration d1, scala.concurrent.duration.Duration d2, org.http4s.blaze.util.TickWheelExecutor te, cats.effect.ConcurrentEffect<F> ce, cats.effect.Timer<F> timer) {
		 
	 }
	

	@Trace(async=true)
	public void renderResponse(Request<F> req, Response<F> response, Function0<Future<ByteBuffer>> f) {
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HttpServerStage","renderResponse"});
		ResponseWrapper<F> wrapper = new ResponseWrapper<F>(response);
		NewRelic.getAgent().getTransaction().setWebResponse(wrapper);
		Weaver.callOriginal();		
	}

	@Trace(dispatcher=true)
	private void runRequest(java.nio.ByteBuffer buff) {
		token = NewRelic.getAgent().getTransaction().getToken();
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HttpServerStage","runRequest"});
		NewRelic.getAgent().getTransaction().convertToWebTransaction();
		Weaver.callOriginal();
	}
}
