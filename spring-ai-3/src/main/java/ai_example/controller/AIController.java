package ai_example.controller;



import java.util.List;

import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import reactor.core.publisher.Flux;

@Controller
@RequestMapping("/ai")
public class AIController {

    private final OllamaChatModel client;

    public AIController(OllamaChatModel client){
        this.client = client;
    }

    @GetMapping("/chat")
    public String chatPage() {
        return "ai-chat";
    }

    @GetMapping("/prompt")
    public String promptResponse(@RequestParam("prompt") String prompt, Model model){
        Flux<String> responseStream = client.stream(prompt); // Assuming client.stream() returns a Flux<String>
        
        // Collect all elements from the response stream into a list
        List<String> responseList = responseStream.collectList().block();
        
        // Join the list elements into a single string
        String response = String.join("", responseList);
        
        model.addAttribute("prompt", prompt);
        
        // Add the response to the model
        model.addAttribute("response", response);
        
        //System.out.println(response);
        
        return "ai-chat";
    }
}

