package com.funwithactivity.acceptancetest.boot;

import static com.funwithactivity.acceptancetest.common.JsonSupport.asJsonString;

import java.nio.charset.StandardCharsets;

import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.MatchType;
import org.mockserver.model.Body;
import org.mockserver.model.JsonBody;
import org.mockserver.socket.PortFactory;

public final class MockWebServer {
    private static final ClientAndServer SERVER = ClientAndServer.startClientAndServer(PortFactory.findFreePort());

    public static void reset() {
        SERVER.reset();
    }

    public static int port() {
        return SERVER.getPort();
    }

    public static MockServerClient mockServer() {
        return SERVER;
    }

    public static Body asJsonBody(Object obj) {
        return new JsonBody(asJsonString(obj), StandardCharsets.UTF_8, MatchType.STRICT);
    }
}
