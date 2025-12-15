package com.clownstore.gateway;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oidcLogin;

@SpringBootTest
@AutoConfigureMockMvc
public class GatewayApplicationTests {

    @Autowired
    private MockMvcTester mockMvcTester;

    private static WireMockServer wireMockServer;


    @BeforeEach
    void init(){
        wireMockServer = new WireMockServer(8081);
        wireMockServer.start();
        WireMock.configureFor("localhost", 8081);
    }

    @AfterAll
    static void destroy(){
        wireMockServer.stop();
    }

    @Test
    @DisplayName("Test unauthenticated access to an endpoint")
    public void test1() {
        assertThat(mockMvcTester.get().uri("/product/all"))
                .hasStatus(HttpStatus.UNAUTHORIZED);
    }


    @Test
    @DisplayName("Test authenticated access to an endpoint")
    @WithMockUser
    public void test2() {
        stubFor(get(urlEqualTo("/product/all"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withBody("[]")));

        assertThat(mockMvcTester.get().uri("/product/all")).hasStatusOk();
    }

}
