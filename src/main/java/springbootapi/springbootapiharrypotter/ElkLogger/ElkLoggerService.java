package springbootapi.springbootapiharrypotter.ElkLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import springbootapi.springbootapiharrypotter.ElkConfiguration.BeanConfiguration;
import springbootapi.springbootapiharrypotter.ElkConfiguration.ElkLoggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class ElkLoggerService {

    private static Logger LOG = LoggerFactory.getLogger(ElkLoggerService.class);

    @Autowired
    private final WebClient webClient;

    @Autowired
    private ElkLoggerConfig elkLoggerConfig;

    @Autowired
    private BeanConfiguration beanConfiguration;

    public ElkLoggerService(WebClient webClient, ElkLoggerConfig elkLoggerConfig, BeanConfiguration beanConfiguration) {
        this.webClient = webClient;
        this.elkLoggerConfig = elkLoggerConfig;
        this.beanConfiguration = beanConfiguration;
    }

    public <T> Mono<T> ElkLogger(T document) {

        List<String> authToken = elkLoggerConfig.getAuthToken();
        List<String> elasticSearchIndex = elkLoggerConfig.getElasticSearchIndex();

        try {
            if (authToken.size() != elasticSearchIndex.size()) {
                LOG.error("The number of indices does not correspond to the number of tokens.");
            }

            for (int i = 0; i < elasticSearchIndex.size(); i++) {
                String index = elasticSearchIndex.get(i);
                String token = authToken.get(i);

                webClient
                        .post()
                        .uri(index)
                        .header("Authorization", "Basic " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(document))//define o corpo de requisição
                        .retrieve()//executa
                        .bodyToMono(Void.class)//converte o corpo da resposta para mono
                        .block();//bloqueia a execução até que a solicitação seja feita
            }
                } catch(Exception e){
                LOG.error("Error indexing document: " + e.getMessage());
            }
            return Mono.empty();
        }
    }