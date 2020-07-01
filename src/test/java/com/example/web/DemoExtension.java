package com.example.web;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author jy
 */
@Slf4j
public class DemoExtension implements TestInstancePostProcessor, ExecutionCondition, ParameterResolver, TestExecutionExceptionHandler,
    BeforeAllCallback,
    BeforeEachCallback,
    BeforeTestExecutionCallback,
    AfterTestExecutionCallback,
    AfterEachCallback,
    AfterAllCallback {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        log.info("test instance methods: {}", Arrays.stream(testInstance.getClass().getDeclaredMethods())
            .map(Method::getName)
            .collect(Collectors.joining(",")));
    }

    @Override
    public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {
        return ConditionEvaluationResult.enabled("Enabled on MacOS X");
    }

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        log.info("after all callback");
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        log.info("after each callback");
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        log.info("after test execution");
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        log.info("before all callback");
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        log.info("before each callback");
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        log.info("before test execution");
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.isAnnotated(RandomInt.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return ThreadLocalRandom.current().nextInt();
    }

    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        log.warn("ERROR: ", throwable);
    }
}
