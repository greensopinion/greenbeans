/*******************************************************************************
 * Copyright (c) 2015, 2016 David Green.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package greensopinion.finance.services.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import greensopinion.finance.services.domain.Categories;
import greensopinion.finance.services.domain.CategoriesService;
import greensopinion.finance.services.domain.Category;
import greensopinion.finance.services.web.model.CategoryModel;

public class CategoryWebServiceTest {

	private final CategoriesService categoriesService = mock(CategoriesService.class);
	private final CategoryWebService service = new CategoryWebService(categoriesService);
	private final Categories categories = createCategories();

	@Before
	public void before() {
		doReturn(categories).when(categoriesService).retrieve();
	}

	@Test
	public void list() {
		List<CategoryModel> list = service.list();
		assertNotNull(list);
		assertEquals(3, list.size());
		assertEquals("a", list.get(0).getName());
		assertEquals("B", list.get(1).getName());
		assertEquals("c", list.get(2).getName());
	}

	private Categories createCategories() {
		List<Category> values = new ArrayList<>();
		values.add(new Category("a"));
		values.add(new Category("c"));
		values.add(new Category("B"));
		return new Categories(values);
	}
}
