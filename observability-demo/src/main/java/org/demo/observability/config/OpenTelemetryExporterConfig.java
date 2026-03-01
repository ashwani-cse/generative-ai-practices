package org.demo.observability.config;

import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Ashwani Kumar
 * Created on 01/03/26.
 */
@Configuration
public class OpenTelemetryExporterConfig {

    @Value("${opentelemetry.exporter.otlp.endpoint}") // configure this endpoint to point to your OpenTelemetry Collector or backend
    private String otlpEndpoint;

    /**
     * Configure the OtlpGrpcSpanExporter to send spans to the specified OTLP endpoint.
     * This exporter will be used by the OpenTelemetry SDK to export trace data.
     */
    @Bean
    public OtlpGrpcSpanExporter otelGrpcSpanExporter() {
        return OtlpGrpcSpanExporter.builder()
                .setEndpoint(otlpEndpoint)
                .build();
    }
}
