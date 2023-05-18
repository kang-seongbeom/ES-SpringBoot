package com.example.es_springboot.config;


import com.example.es_springboot.repository.es.UserSearchRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;


@TestConfiguration
@EnableElasticsearchRepositories(basePackageClasses = UserSearchRepository.class)
public class ElasticTest extends AbstractElasticsearchConfiguration {

    private static final GenericContainer container;

    static{
        container = new GenericContainer(
                new ImageFromDockerfile().withDockerfileFromBuilder(
                        b -> {
                            b.from("docker.elastic.co/elasticsearch/elasticsearch:7.10.2")
                                    .run("bin/elasticsearch-plugin install analysis-nori")
                                    .build();
                        })
        ).withExposedPorts(9200, 9300)
                .withFileSystemBind(
                        "es/settings/stop/english.txt",
                        "/usr/share/elasticsearch/config/settings/stop/english.txt",
                        BindMode.READ_ONLY)
                .withFileSystemBind(
                        "es/settings/synonym/english.txt",
                        "/usr/share/elasticsearch/config/settings/synonym/english.txt",
                        BindMode.READ_ONLY)
                .withEnv("discovery.type","single-node");
        container.start();
    }

    @Override
    public RestHighLevelClient elasticsearchClient() {
        String hostAddress = new StringBuilder()
                .append(container.getHost())
                .append(":")
                .append(container.getMappedPort(9200))
                .toString();

        ClientConfiguration configuration = ClientConfiguration.builder()
                .connectedTo(hostAddress)
                .build();
        return RestClients.create(configuration).rest();
    }
}
