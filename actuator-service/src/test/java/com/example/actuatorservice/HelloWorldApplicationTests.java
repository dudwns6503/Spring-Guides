package com.example.actuatorservice;

import org.assertj.core.api.BDDAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalManagementPort;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestPropertySource(properties = { "management.port=0" })
public class HelloWorldApplicationTests {

    @LocalServerPort
    private int port;


    /**
     * 런타임시 할당된 HTTP 관리 포트를 주입하는 필드
     *
     * https://docs.spring.io/spring-boot/docs/2.3.2.RELEASE/api/org/springframework/boot/actuate/autoconfigure/web/server/LocalManagementPort.html
     */
//    @Value("${local.management.port}")
    @LocalManagementPort
    private int mgt;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void shouldReturn200WhenSendingRequestToController() throws Exception {
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity("http://localhost:" + this.port + "/hello-world", Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn200WhenSendingRequestToManagementEndPoint() throws Exception {
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity("http://localhost:" + this.mgt + "/actuator", Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
