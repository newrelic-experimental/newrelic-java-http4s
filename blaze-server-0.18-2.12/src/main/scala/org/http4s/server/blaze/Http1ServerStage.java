package org.http4s.server.blaze;

import java.nio.ByteBuffer;
//import java.util.logging.Level;

import org.http4s.Request;
import org.http4s.Response;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.http4s.OutboundWrapper;
import com.nr.instrumentation.http4s.*;
import scala.Function0;
import scala.concurrent.Future;

@Weave(type=MatchType.BaseClass)
public abstract class Http1ServerStage<A> {

	@Trace(async=true)
	public <F> void renderResponse(Request<F> req, Response<F> response, Function0<Future<ByteBuffer>> f) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HttpServerStage","renderResponse"});
		if(req.token != null) {
			req.token.linkAndExpire();
			req.token = null;
		}
		ResponseWrapper wrapper = new ResponseWrapper(response);
		NewRelic.getAgent().getTransaction().setWebResponse(wrapper);
		Weaver.callOriginal();		
	}

	@Trace(dispatcher=true)
	private void runRequest(java.nio.ByteBuffer buff) {
		NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","HttpServerStage","runRequest"});
		NewRelic.getAgent().getTransaction().convertToWebTransaction();
		Weaver.callOriginal();
	}
}
