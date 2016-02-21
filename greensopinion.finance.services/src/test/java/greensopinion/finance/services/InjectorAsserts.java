package greensopinion.finance.services;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;

public class InjectorAsserts {

	public static <T> T assertSingletonBinding(Injector injector, Class<T> clazz) {
		Key<T> key = Key.get(clazz);
		Binding<T> binding = injector.getExistingBinding(key);
		assertNotNull(binding);
		T instance = injector.getInstance(key);
		assertSame(instance, injector.getInstance(key));
		return instance;
	}

	public static <T> T assertSingletonBinding(Injector injector, Class<T> binding, Class<? extends T> implementation) {
		T actual = assertSingletonBinding(injector, binding);
		assertTrue(implementation.isAssignableFrom(actual.getClass()));
		return actual;
	}

	private InjectorAsserts() {
		// prevent instantiation
	}
}
