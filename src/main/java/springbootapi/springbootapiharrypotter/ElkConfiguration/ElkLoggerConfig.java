package springbootapi.springbootapiharrypotter.ElkConfiguration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@PropertySource("classpath:application.properties")
public class ElkLoggerConfig {

    @Value("${elasticsearch.index}")
    private List<String> elasticSearchIndex;

    @Value("${elasticsearch.authToken}")
    private List<String> authToken;

    public List<String> getAuthToken() {
        return new ArrayList<>(authToken);
    }

    public List<String> getElasticSearchIndex() {
        return new ArrayList<>(elasticSearchIndex);
    }
}