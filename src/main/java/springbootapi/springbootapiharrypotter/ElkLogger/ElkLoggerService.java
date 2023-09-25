package springbootapi.springbootapiharrypotter.ElkLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import springbootapi.springbootapiharrypotter.ElkConfiguration.ElkLoggerConfig;

@Service
public class ElkLoggerService {

    @Autowired
    private final WebClient webClient;

    @Autowired
    private ElkLoggerConfig elkLoggerConfig;

    @Autowired
    public ElkLoggerService(WebClient webClient, ElkLoggerConfig elkLoggerConfig) {
        this.webClient = webClient;
        this.elkLoggerConfig = elkLoggerConfig;
    }

    public <T> Mono <T> ElkLogger(T document) {
        String authToken = elkLoggerConfig.getAuthToken();
        String elasticSearchIndex = elkLoggerConfig.getElasticSearchIndex();

        return webClient
                .post()
                .uri(elasticSearchIndex)
                .header("Authorization", "Basic " + authToken)
                .body(BodyInserters.fromValue(document))
                .retrieve()
                .bodyToMono((Class<T>) document.getClass())
                .onErrorResume(e -> {
                    if (e instanceof WebClientResponseException) {
                        WebClientResponseException responseException = (WebClientResponseException) e;
                        System.err.println("Document indexed successfully - Status: " + responseException.getStatusCode());
                    } else {
                        System.err.println("Error indexing document " + e.getMessage());
                    }
                    return Mono.empty();
                });

    }
}


