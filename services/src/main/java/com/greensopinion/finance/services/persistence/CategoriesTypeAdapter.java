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
package com.greensopinion.finance.services.persistence;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.greensopinion.finance.services.domain.Categories;
import com.greensopinion.finance.services.domain.Category;
import com.greensopinion.finance.services.encryption.EncryptorProviderService;

public class CategoriesTypeAdapter extends TypeAdapter<Categories> {
	private static final String NAME_CATEGORIES = "categories";

	public static TypeAdapterFactory factory(EncryptorProviderService encryptorProviderService) {
		return new TypeAdapterFactory() {

			@SuppressWarnings("unchecked")
			@Override
			public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
				if (Categories.class.isAssignableFrom(type.getRawType())) {
					return (TypeAdapter<T>) new CategoriesTypeAdapter(gson, encryptorProviderService);
				}
				return null;
			}
		};
	}

	private final Gson gson;
	private final EncryptorProviderService encryptorProvider;

	public CategoriesTypeAdapter(Gson gson, EncryptorProviderService encryptorProvider) {
		this.gson = checkNotNull(gson);
		this.encryptorProvider = checkNotNull(encryptorProvider);
	}

	@Override
	public void write(JsonWriter writer, Categories categories) throws IOException {
		checkNotNull(categories);

		writer.beginObject();
		writer.name(NAME_CATEGORIES);
		writer.beginArray();
		for (Category category : categories.getCategories()) {
			writer.value(toEncryptedString(category));
		}
		writer.endArray();
		writer.endObject();
	}

	private String toEncryptedString(Category category) {
		String json = gson.toJson(category);
		return encryptorProvider.getEncryptor().encrypt(json);
	}

	@Override
	public Categories read(JsonReader reader) throws IOException {
		reader.beginObject();
		checkState(NAME_CATEGORIES.equals(reader.nextName()));
		reader.beginArray();

		ImmutableList.Builder<Category> elements = ImmutableList.<Category> builder();
		while (reader.hasNext()) {
			if (reader.peek() == JsonToken.BEGIN_OBJECT) {
				elements.add(gson.getAdapter(Category.class).read(reader));
			} else {
				elements.add(readCategory(reader.nextString()));
			}
		}
		reader.endArray();
		reader.endObject();

		return new Categories(elements.build());
	}

	private Category readCategory(String encryptedCategory) {
		String json = encryptorProvider.getEncryptor().decrypt(encryptedCategory);
		return gson.fromJson(json, Category.class);
	}

}
