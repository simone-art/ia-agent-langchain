package com.example.springboot;

import dev.langchain4j.service.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assistant")
public class AssistantController {

    private final AssistantAIService assistantAiService;

    // Construtor permite acesso ao metodo service
    public AssistantController(AssistantAIService assistantAiService) {
        this.assistantAiService = assistantAiService;
    }

    @PostMapping()
    public String askAssistant(@RequestBody String userMessage) {
        Result<String> result = assistantAiService.handleRequest(userMessage);
        return result.content();
    }
}
