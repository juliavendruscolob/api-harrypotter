package springbootapi.springbootapiharrypotter.ElkConfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:application.properties")
public class ElkLoggerConfig {

    @Value("${elasticsearch.authToken}")
    private String authToken;

    @Value("${elasticsearch.index}")
    private String elasticSearchIndex;

    public String getAuthToken() {
        return authToken;
    }

    public String getElasticSearchIndex() {
        return elasticSearchIndex;
    }
}