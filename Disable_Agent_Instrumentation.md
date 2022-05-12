# Disabling Java Agent Instrumentation
   
If you are using New Relic Java Agent 7.2.0 or later, the agent already contains instrumentation for Http4S but it is a somewhat different version of instrumentation.  Because the two sets of instrumentation are different it can cause conflicts in the agent so you will need to disable the agent instrumentation before starting to use this instrumentation.
  
## Steps to disable agent instrumentation
  
1.  Edit newrelic.yml
2.  Locate the following

  class_transformer:

    # This instrumentation reports the name of the user principal returned from
    # HttpServletRequest.getUserPrincipal() when servlets and filters are invoked.
    com.newrelic.instrumentation.servlet-user:
      enabled: false

    com.newrelic.instrumentation.spring-aop-2:
      enabled: false


3.  Add the following lines, making sure to use spaces to the previous lines

    com.newrelic.instrumentation.http4s-blaze-client-2.12_0.21:
      enabled: false
    
    com.newrelic.instrumentation.http4s-blaze-client-2.12_0.22:
      enabled: false
    
    com.newrelic.instrumentation.http4s-blaze-client-2.13_0.21:
      enabled: false
    
    com.newrelic.instrumentation.http4s-blaze-client-2.13_0.22:
      enabled: false
    
    com.newrelic.instrumentation.http4s-blaze-server-2.12_0.21:
      enabled: false
    
    com.newrelic.instrumentation.http4s-blaze-server-2.13_0.21:
      enabled: false
    
4.  Save newrelic.yml


The changes will take effect on the next restart of the application
