package org.acme;

import dev.langchain4j.service.Result;
import dev.langchain4j.service.SystemMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.ToolBox;

@RegisterAiService
public interface MyAgent {

    // Basic chat method
    @SystemMessage("You are a simple test agent.")
    @ToolBox({MyTool.class})
    Result<String> chat(String message);

}
