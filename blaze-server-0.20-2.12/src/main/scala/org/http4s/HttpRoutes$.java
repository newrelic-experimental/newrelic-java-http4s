package org.http4s;

import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.http4s.NRPartialFunction;

@Weave
public abstract class HttpRoutes$ {

//	public <F> cats.data.Kleisli<?, org.http4s.Request<F>, org.http4s.Response<F>> of(scala.PartialFunction<org.http4s.Request<F>, F> req, cats.effect.Sync<F> sync) {
//		NRPartialFunction<F> pf = new NRPartialFunction<F>(req);
//		req = pf;
//
//		return Weaver.callOriginal();
//	}
//
//	public <F> cats.data.Kleisli<?, org.http4s.Request<F>, org.http4s.Response<F>> local(scala.Function1<org.http4s.Request<F>, org.http4s.Request<F>> f, cats.data.Kleisli<?, org.http4s.Request<F>, org.http4s.Response<F>> kl, cats.effect.Sync<F> sync) {
//		return Weaver.callOriginal();
//	}
//
//	public <F> cats.data.Kleisli<?, org.http4s.Request<F>, org.http4s.Response<F>> pure(org.http4s.Response<F> resp, cats.Applicative<?> app) {
//		return Weaver.callOriginal();
//	}
//	
//	public <F> cats.data.Kleisli<?, org.http4s.Request<F>, org.http4s.Response<F>> liftF(cats.data.OptionT<F, org.http4s.Response<F>> option) {
//		return Weaver.callOriginal();
//	}
//	
//	public <F> cats.data.Kleisli<?, org.http4s.Request<F>, org.http4s.Response<F>> apply(scala.Function1<org.http4s.Request<F>, cats.data.OptionT<F, org.http4s.Response<F>>> resp, cats.effect.Sync<F> sync) {
//		return Weaver.callOriginal();
//	}

//	public static final Object $anonfun$of$2(scala.PartialFunction pf, org.http4s.Request req, cats.effect.Sync syn) {
//		return Weaver.callOriginal();
//	}
}
