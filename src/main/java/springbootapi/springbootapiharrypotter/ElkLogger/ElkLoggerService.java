package springbootapi.springbootapiharrypotter.ElkLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.Exceptions;
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

    public <T> Mono<T> ElkLogger(T document) {
        String authToken = elkLoggerConfig.getAuthToken();
        String elasticSearchIndex = elkLoggerConfig.getElasticSearchIndex();

        try {
            webClient
                    .post()
                    .uri(elasticSearchIndex)
                    .header("Authorization", "Basic " + authToken)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(document))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (Exception e) {
            System.err.println("Error indexing document: " + e.getMessage());
        }
        return Mono.empty();
    }
}