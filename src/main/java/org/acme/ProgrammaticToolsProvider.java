package org.acme;

import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.model.chat.request.json.JsonArraySchema;
import dev.langchain4j.model.chat.request.json.JsonObjectSchema;
import dev.langchain4j.model.chat.request.json.JsonSchemaElement;
import dev.langchain4j.model.chat.request.json.JsonStringSchema;
import dev.langchain4j.service.tool.DefaultToolExecutor;
import dev.langchain4j.service.tool.ToolProvider;
import dev.langchain4j.service.tool.ToolProviderRequest;
import dev.langchain4j.service.tool.ToolProviderResult;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class ProgrammaticToolsProvider implements ToolProvider {

    @ConfigProperty(name = "app.programmatic.tools.enabled")
    private boolean enabled = false;

    MyTools myTools;

    public ProgrammaticToolsProvider(MyTools myTools) {
        this.myTools = myTools;
    }

    @Override
    public ToolProviderResult provideTools(ToolProviderRequest toolProviderRequest) {

        if (!enabled) return ToolProviderResult.builder().build();

        try {
            // Get methods from MyTools
            Method pingMethod = MyTools.class.getMethod("ping");
            Method listOfPojosMethod = MyTools.class.getMethod("printListOfPojos", List.class);

            // Tool 1: ping() - no parameters
            ToolSpecification pingSpec = ToolSpecification.builder()
                    .name("ping")
                    .description("ping tool")
                    .parameters(null)
                    .build();

            // TODO:

            // 1) Need a ToolExecutor for programmatic tooling. Easiest is to use is DefaultToolExecutor. However, this likely
            // means we bypass Quarkus-Langchain4j capabilities.

            //  2) How to set ReturnBehavior? Langchain4j has an API to provide a Set of tool names that should return immediately

            DefaultToolExecutor pingExecutor = new DefaultToolExecutor(myTools, pingMethod);

            // Tool 2: get_name_from_argument_list - takes List<Pojo>
            ToolSpecification listOfPojosSpec = createListOfToolSpecs();

            DefaultToolExecutor listExecutor = new DefaultToolExecutor(myTools, listOfPojosMethod);

            return ToolProviderResult.builder()
                    .add(pingSpec, pingExecutor)
                    .add(listOfPojosSpec, listExecutor)
                    .build();

        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Failed to find tool methods", e);
        }
    }

    private ToolSpecification createListOfToolSpecs() {
        // Define Pojo object schema
        Map<String, JsonSchemaElement> pojoProperties = new LinkedHashMap<>();
        pojoProperties.put("name", JsonStringSchema.builder()
                .description("The name property")
                .build());

        JsonObjectSchema pojoSchema = JsonObjectSchema.builder()
                .addProperties(pojoProperties)
                .required(List.of("name"))
                .build();

        // Define array of Pojos
        JsonArraySchema pojosArraySchema = JsonArraySchema.builder()
                .items(pojoSchema)
                .description("List of POJO objects with name property")
                .build();

        // Define parameters
        Map<String, JsonSchemaElement> parameters = new LinkedHashMap<>();
        parameters.put("pojos", pojosArraySchema);

        JsonObjectSchema parametersSchema = JsonObjectSchema.builder()
                .addProperties(parameters)
                .required(List.of("pojos"))
                .build();

        return ToolSpecification.builder()
                .name("get_name_from_argument_list")
                .description("Provide a Wrapper pojo containing a list of POJOs with a name property. Use any random names you want")
                .parameters(parametersSchema)
                .build();
    }
}
