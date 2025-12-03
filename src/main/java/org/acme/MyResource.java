package org.acme;

import dev.langchain4j.agent.tool.ReturnBehavior;
import dev.langchain4j.service.Result;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class MyResource {

    private MyAgent agent;

    public MyResource(MyAgent agent) {
        this.agent = agent;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        String userPrompt = "Call get_name_from_argument_list";
        Result<String> result = agent.chat(userPrompt);

        // Using @Toolbox, we can retrieve the result of the tool execution
        if (result.toolExecutions().isEmpty()) {
            System.out.println("*** ReturnBehavior -> " + ReturnBehavior.TO_LLM);
            return result.content();

        } else {
            System.out.println("*** ReturnBehavior -> " + ReturnBehavior.IMMEDIATE);
            String toolResponse = result.toolExecutions().get(0).result();
            return toolResponse;
        }
    }
}
