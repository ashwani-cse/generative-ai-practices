package com.mcp_server.remote.config;

import com.mcp_server.remote.tools.HelpDeskTools;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author Ashwani Kumar
 * Created on 27/01/26.
 */
@Configuration
public class MCPServerConfig {

    @Bean
    List<ToolCallback> toolCallbacks(HelpDeskTools helpDeskTools) {
        return List.of(ToolCallbacks.from(helpDeskTools));
    }
}
