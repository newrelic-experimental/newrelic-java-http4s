package com.nr.instrumentation.http4s

import org.http4s.{Response, Header} 
import com.newrelic.api.agent.{ExtendedResponse, HeaderType}
import org.http4s.util.CaseInsensitiveString

class ResponseWrapper[F[_]](var response: Response[F]) extends ExtendedResponse {

	def getStatus: Int = {
			response.status.code
  }

  def getStatusMessage: String = {
		response.status.reason
	}

   def getContentType: String = {
    val option = response.headers.get(CaseInsensitiveString("Content-Type"))
    if(option.isEmpty) {
      null
    }
    option.get.value
  }

  def getHeaderType: HeaderType = {
    HeaderType.HTTP
  }

  def setHeader(name: String, value: String): Unit = {
    val raw = Header.apply(name,value)
    response.headers.put(raw)
  }

  def getContentLength: Long = {
   val option = response.headers.get(CaseInsensitiveString("Content-Length"))
    if(option.isEmpty) {
      null
    }
    val value = option.get.value
    java.lang.Long.valueOf(value)
  }

 

}