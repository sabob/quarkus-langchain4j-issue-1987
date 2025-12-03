package org.acme;

import dev.langchain4j.agent.tool.ReturnBehavior;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class MyTool {

    // Works
    // A tool that should return immediately without LLM processing
    @Tool(
            name = "ping",
            returnBehavior = ReturnBehavior.IMMEDIATE
    )
    public String ping() {
        return "pong";
    }

    @Tool(
            name = "get_name_from_argument",
            value = "Provide a list of names in this pojo. Use any random names you want",
            returnBehavior = ReturnBehavior.IMMEDIATE
    )
    public String listOfPojos(List<Pojo> pojos) {

        StringBuilder sb = new StringBuilder();
        for(Pojo pojo : pojos) {
            sb.append(pojo);
        }
        return toString().toString();
    }
}

