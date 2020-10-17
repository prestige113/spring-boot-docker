package ru.aldar.demo.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.http.HttpHeaders;

@Configuration
public class EsConfig extends AbstractElasticsearchConfiguration {

  @Value("${elasticsearch.host}")
  private String elasticHost;


  @Override
  @Bean
  public RestHighLevelClient elasticsearchClient() {
    HttpHeaders httpHeaders= new HttpHeaders();
    httpHeaders.add("cluster_name", "docker-cluster");
    ClientConfiguration clientConfiguration
      = ClientConfiguration.builder()
      .connectedTo(elasticHost).withDefaultHeaders(httpHeaders)
      .build();
    return RestClients.create(clientConfiguration).rest();
  }

  @Bean
  public ElasticsearchOperations elasticsearchTemplate() {
    return new ElasticsearchRestTemplate(elasticsearchClient());
  }

}
