package com.example.web;

import org.junit.platform.engine.discovery.DiscoverySelectors;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

import java.io.PrintWriter;

/**
 * @author jy
 */
public class ManualExecuteTestApplication {

    public static void main(String[] args) {
        LauncherDiscoveryRequest request = LauncherDiscoveryRequestBuilder
            .request()
            .selectors(DiscoverySelectors.selectClass(JUnitApiTest.class))
            .build();

        SummaryGeneratingListener listener = new SummaryGeneratingListener();

        Launcher launcher = LauncherFactory.create();
        launcher.registerTestExecutionListeners(listener);
        launcher.execute(request);

        listener.getSummary().printTo(new PrintWriter(System.out));
    }
}
