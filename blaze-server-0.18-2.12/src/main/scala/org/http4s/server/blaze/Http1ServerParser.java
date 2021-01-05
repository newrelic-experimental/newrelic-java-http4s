package org.http4s.server.blaze;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import scala.util.Either;
import scala.Tuple2;
import org.http4s.ParseFailure;
import org.http4s.Request;
import org.http4s.HttpVersion;
import fs2.internal.FreeC;
import scala.runtime.BoxedUnit;

@Weave
public abstract class Http1ServerParser<F> {

	@Trace
	public Either<Tuple2<ParseFailure, HttpVersion>, Request<F>> collectMessage(FreeC<?, BoxedUnit> f, org.http4s.AttributeMap map) {
		Either<Tuple2<ParseFailure, HttpVersion>, Request<F>> result = Weaver.callOriginal();
	
		return result;
	}
}
