package itmo.soa.barsservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.net.ssl.SSLContext;
import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

        @Bean
        public ObjectMapper objectMapper() {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                return mapper;
        }

        @Bean
        public RestTemplate restTemplate(ObjectMapper objectMapper) throws Exception {
                SSLContext sslContext = SSLContextBuilder.create()
                                .loadTrustMaterial(TrustAllStrategy.INSTANCE)
                                .build();

                HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                                .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
                                                .setSslContext(sslContext)
                                                .build())
                                .build();

                CloseableHttpClient httpClient = HttpClients.custom()
                                .setConnectionManager(connectionManager)
                                .build();

                HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
                                httpClient);

                RestTemplate restTemplate = new RestTemplate(requestFactory);
                MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
                converter.setObjectMapper(objectMapper);
                converter.setSupportedMediaTypes(List.of(
                                MediaType.APPLICATION_JSON,
                                MediaType.valueOf("application/*+json")));
                restTemplate.getMessageConverters()
                                .removeIf(existing -> existing instanceof MappingJackson2HttpMessageConverter);
                restTemplate.getMessageConverters().add(0, converter);
                return restTemplate;
        }

        @Override
        public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                                .allowedOrigins("*")
                                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                                .allowedHeaders("*");
        }
}
