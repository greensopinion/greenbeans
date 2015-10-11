package greensopinion.finance.services.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class EntityEventSupportTest {
	static class TestListener<T> extends EntityListener<T> {

		private final List<T> updated = new ArrayList<>();

		public TestListener(Class<T> type) {
			super(type);
		}

		@Override
		public void updated(T entity) {
			updated.add(entity);
		}
	}

	@Test
	public void udpated() {
		EntityEventSupport support = new EntityEventSupport();

		TestListener<Categories> listener1 = new TestListener<>(Categories.class);
		TestListener<Transactions> listener2 = new TestListener<>(Transactions.class);
		support.addListener(listener1);
		support.addListener(listener2);

		support.updated(new Object());
		assertTrue(listener1.updated.isEmpty());
		assertTrue(listener2.updated.isEmpty());

		Categories categories = new Categories();
		support.updated(categories);

		assertEquals(ImmutableList.of(categories), listener1.updated);
		assertTrue(listener2.updated.isEmpty());
	}
}
