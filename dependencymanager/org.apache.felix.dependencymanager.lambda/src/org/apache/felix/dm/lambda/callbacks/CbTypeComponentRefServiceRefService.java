package org.apache.felix.dm.lambda.callbacks;

import java.util.Objects;

import org.apache.felix.dm.Component;
import org.osgi.framework.ServiceReference;

/**
 * Represents a callback(Component, ServiceReference, Service, ServiceReference, Service) that is invoked on a Component implementation class. 
 * The type of the class on which the callback is invoked on is represented by the T generic parameter.
 * 
 * @author <a href="mailto:dev@felix.apache.org">Felix Project Team</a>
 */
@FunctionalInterface
public interface CbTypeComponentRefServiceRefService<T, S> extends SerializableLambda {
    /**
     * Handles the given arguments.
     * @param instance the Component implementation instance on which the callback is invoked on. 
     * @param c first callback param
     * @param oldRef second callback param
     * @param old third callback param
     * @param replaceRef fourth callback param
     * @param replace fifth callback param
     */
    void accept(T instance, Component c, ServiceReference<S> oldRef, S old, ServiceReference<S> replaceRef, S replace);

    default CbTypeComponentRefServiceRefService<T, S> andThen(CbTypeComponentRefServiceRefService<? super T, S> after) {
        Objects.requireNonNull(after);
        return (T instance, Component c, ServiceReference<S> oldRef, S old, ServiceReference<S> replaceRef,
            S replace) -> {
            accept(instance, c, oldRef, old, replaceRef, replace);
            after.accept(instance, c, oldRef, old, replaceRef, replace);
        };
    }
}
