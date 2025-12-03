package org.acme;

import dev.langchain4j.agent.tool.ReturnBehavior;
import dev.langchain4j.agent.tool.Tool;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.pojo.Pojo;

import java.util.LinkedHashMap;
import java.util.List;

@ApplicationScoped
public class MyTools {

    // A ping tool that should return immediately without LLM processing.
    // This works when using @ToolBox
    @Tool(
            name = "ping",
            value = "ping tool",
            returnBehavior = ReturnBehavior.IMMEDIATE
    )
    public String ping() {
        System.out.println("Tool called: ping");
        return "pong";
    }

    @Tool(
            name = "get_name_from_argument_list",
            value = "Provide a Wrapper pojo containing a list of POJOs with a name property. Use any random names you want",
            returnBehavior = ReturnBehavior.IMMEDIATE
    )
    // TODO:
    //  When using Quarkus with @ToolBox, the list of pojos are provided as a List of LinkedHashMaps.
    // When using programmatic tools and Langchain4j' DefaultToolExecutor, it correctly provides the arguments as a list of Pojos.
    public String printListOfPojos(List<Pojo> pojos) {
        System.out.println("Tool called: printListOfPojos");

        Object obj = pojos.get(0);
        System.out.println("Argument is a list of: " + obj.getClass().getSimpleName());


        StringBuilder sb = new StringBuilder();
        for (Object pojo : pojos) {
            sb.append(pojo);
        }
        return sb.toString();
    }
}

