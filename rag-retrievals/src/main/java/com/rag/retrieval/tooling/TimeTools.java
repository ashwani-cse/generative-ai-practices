package com.rag.retrieval.tooling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.ZoneId;

/**
 * @author Ashwani Kumar
 * Created on 23/01/26.
 */
@Component
public class TimeTools {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimeTools.class);

    @Tool(name = "getCurrentLocalTime", description = "Get the current local time in the user's timezone")
    public String getCurrentLocalTime() {
        LOGGER.info("Returning current local time in user's timezone");
        return LocalTime.now().toString();
    }

    @Tool(name = "getCurrentTimeInZone", description = "Get the current time in the giving zone")
    public String getCurrentTimeInZone(@ToolParam(description = "Value representing the time zone") String timeZone) {
        LOGGER.info("Returning current time in timeZone: {}", timeZone);
        return LocalTime.now(ZoneId.of(timeZone)).toString();
    }
}
