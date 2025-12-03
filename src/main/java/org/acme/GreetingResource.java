package org.acme;

import dev.langchain4j.service.Result;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    private MyAgent agent;

    public GreetingResource(MyAgent agent) {
        this.agent = agent;
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String ping() {
        Result<String> result = agent.chat("Call the tool called get_name_from_argument_list");
        String content = result.toolExecutions().get(0).result();
        return content;
    }
}
