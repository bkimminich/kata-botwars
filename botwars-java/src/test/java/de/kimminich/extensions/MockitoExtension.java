package de.kimminich.extensions;

import org.junit.gen5.api.extension.ExtensionContext;
import org.junit.gen5.api.extension.ExtensionContext.Namespace;
import org.junit.gen5.api.extension.ExtensionContext.Store;
import org.junit.gen5.api.extension.ParameterContext;
import org.junit.gen5.api.extension.ParameterResolver;
import org.junit.gen5.api.extension.TestInstancePostProcessor;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;

/**
 * @link https://github.com/junit-team/junit5-samples/blob/master/junit5-mockito-extension
 */
public class MockitoExtension implements TestInstancePostProcessor, ParameterResolver {

    private static final Namespace NAMESPACE = Namespace.of(MockitoExtension.class);

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) {
        MockitoAnnotations.initMocks(testInstance);
    }

    @Override
    public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return parameterContext.getParameter().isAnnotationPresent(InjectMock.class);
    }

    @Override
    public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext) {
        Store mocks = extensionContext.getStore(NAMESPACE);
        return getMock(parameterContext.getParameter().getType(), mocks);
    }

    private Object getMock(Class<?> mockType, Store mocks) {
        return mocks.getOrComputeIfAbsent(mockType, type -> mock(mockType));
    }

}
