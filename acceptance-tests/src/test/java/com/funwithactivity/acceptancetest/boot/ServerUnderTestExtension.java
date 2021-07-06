package com.funwithactivity.acceptancetest.boot;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.Extension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.util.SocketUtils;

@Slf4j
public class ServerUnderTestExtension implements Extension, BeforeAllCallback {
    private static final int FREE_PORT = SocketUtils.findAvailableTcpPort();

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        LOG.info("Running tests against localhost:{}", FREE_PORT);
        System.setProperty("server.port", String.valueOf(FREE_PORT));
        System.setProperty("management.server.port", String.valueOf(FREE_PORT));
        System.setProperty("mock.server.port", String.valueOf(MockWebServer.port()));
    }
}
