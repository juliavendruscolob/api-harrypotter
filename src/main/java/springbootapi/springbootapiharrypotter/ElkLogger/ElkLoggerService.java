package springbootapi.springbootapiharrypotter.ElkLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import springbootapi.springbootapiharrypotter.ElkConfiguration.ElkLoggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ElkLoggerService {

    private static Logger LOG = LoggerFactory.getLogger(ElkLoggerService.class);

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
            LOG.error("Error indexing document: " + e.getMessage());
        }
        return Mono.empty();
    }
}