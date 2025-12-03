# quarkus-langchain4j-issue-1987

A Quarkus-Langchain4j project to highlight 2 limitations:

* ReturnBehavior.IMMEDIATE is not possible when using Programmatic/Dynamic tooling as documented here: https://docs.langchain4j.dev/tutorials/tools/#specifying-tools-programmatically 
Langchain5j supports ReturnBehavior.IMMEDIATE by providing a Set of tool names: see last section here: https://docs.langchain4j.dev/tutorials/tools/#specifying-tools-programmatically
* Langchain4j allows creating a DefaultToolExecutor() to use in Programmatic tool selection.This ToolExecutor deserialize json to List of Pojos here: https://github.com/langchain4j/langchain4j/blob/2b90e168f884d0b426d54902df54dee5709fbf73/langchain4j/src/main/java/dev/langchain4j/service/tool/DefaultToolExecutor.java#L294 
this feature is not supported in QuarkusToolExecutor. Instead, a LinkedHashMap is passed as an argument. See MyTool#listOfPojos.  

QuarkusToolExecutor throws:
```
2025-12-03 13:49:42,733 ERROR [io.qua.lan.run.too.QuarkusToolExecutor] (executor-thread-1) Error while executing tool 
'class org.acme.MyTool_ClientProxy': java.lang.ClassCastException: class java.util.LinkedHashMap cannot be cast to 
class org.acme.Pojo (java.util.LinkedHashMap is in module java.base of loader 'bootstrap'; org.acme.Pojo 
is in unnamed module of loader io.quarkus.bootstrap.classloading.QuarkusClassLoader @2f5a72e2)
	at org.acme.MyTool.getName(MyTool.java:30)
```

Workaround is to wrap the List in another Pojo.
