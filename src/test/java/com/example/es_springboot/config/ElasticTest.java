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

    // nori를 다운받기 위해서 DockerImageName보 더 추상화된 GenericContainer를 사용합니다.
    private static final GenericContainer container;

    static{
        container = new GenericContainer(
                new ImageFromDockerfile().withDockerfileFromBuilder(
                        b -> {
                            b.from("docker.elastic.co/elasticsearch/elasticsearch:7.10.2") // 엘라스틱 이미지입니다.
                                    .run("bin/elasticsearch-plugin install analysis-nori") // 이미지에 nori를 다운 받습니다.
                                    .build();
                        })
        ).withExposedPorts(9200, 9300)
                .withFileSystemBind(
                        "es/settings/stop/english.txt",
                        "/usr/share/elasticsearch/config/settings/stop/english.txt", // 불용어 파일을 추가합니다.
                        BindMode.READ_ONLY)
                .withFileSystemBind(
                        "es/settings/synonym/english.txt",
                        "/usr/share/elasticsearch/config/settings/synonym/english.txt", // 동의어 파일을 추가합니다.
                        BindMode.READ_ONLY)
                .withEnv("discovery.type","single-node");
        container.start();
    }

    //엘라스틱서치를 사용하기 위해서는 high와 low level을 사용하는데, 주로 high level을 사용합니다.
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
