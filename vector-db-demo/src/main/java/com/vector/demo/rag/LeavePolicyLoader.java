package com.vector.demo.rag;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Ashwani Kumar
 * Created on 18/01/26.
 */
@Component
public class LeavePolicyLoader {

    private final VectorStore vectorStore;

    @Value("classpath:/HR_Policies.pdf")
    private Resource HR_POLICIES_PDF;

    public LeavePolicyLoader(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadPdf() {
        TikaDocumentReader pdfDocumentReader = new TikaDocumentReader(HR_POLICIES_PDF);
        List<Document> documents = pdfDocumentReader.get();
       // vectorStore.add(documents); // not splitting into chunks for simplicity

        TextSplitter textSplitter = TokenTextSplitter.builder().withChunkSize(100).withMaxNumChunks(400).build();
        List<Document> documentChunks = textSplitter.split(documents);
        vectorStore.add(documentChunks);
    }
}
