package com.nr.instrumentation.http4s.client

import com.newrelic.api.agent.{HeaderType, OutboundHeaders}
import org.http4s.{Request, Header} 

class OutboundWrapper[F[_]](var response: Request[F]) extends OutboundHeaders {
  
   def getHeaderType: HeaderType = {
     HeaderType.HTTP
   }
 
   def setHeader(name: String, value: String): Unit = {
    val raw = Header.apply(name,value)
    response.headers.put(raw)
  }

}