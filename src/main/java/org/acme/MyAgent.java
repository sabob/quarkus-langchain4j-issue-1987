package org.acme;

import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.ToolBox;

@RegisterAiService(
        toolProviderSupplier = ProgrammaticToolsProviderSupplier.class
)
public interface MyAgent {

    // Basic chat method
    @SystemMessage("You are a simple agent that can call Tools.")

    @ToolBox({MyTools.class})
    Result<String> chat(String message);

}
