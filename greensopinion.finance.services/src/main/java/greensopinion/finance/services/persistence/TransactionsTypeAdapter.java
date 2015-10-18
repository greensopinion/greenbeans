package greensopinion.finance.services.persistence;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.io.IOException;
import java.util.UUID;

import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import greensopinion.finance.services.domain.Transaction;
import greensopinion.finance.services.domain.Transactions;
import greensopinion.finance.services.encryption.EncryptorProviderService;

class TransactionsTypeAdapter extends TypeAdapter<Transactions> {

	private static final String NAME_TRANSACTIONS = "transactions";

	public static TypeAdapterFactory factory(EncryptorProviderService encryptorProviderService) {
		return new TypeAdapterFactory() {

			@SuppressWarnings("unchecked")
			@Override
			public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
				if (Transactions.class.isAssignableFrom(type.getRawType())) {
					return (TypeAdapter<T>) new TransactionsTypeAdapter(gson, encryptorProviderService);
				}
				return null;
			}
		};
	}

	private final EncryptorProviderService encryptorProviderService;
	private final Gson gson;

	TransactionsTypeAdapter(Gson gson, EncryptorProviderService encryptorProviderService) {
		this.gson = checkNotNull(gson);
		this.encryptorProviderService = checkNotNull(encryptorProviderService);
	}

	@Override
	public void write(JsonWriter writer, Transactions value) throws IOException {
		checkNotNull(value);

		writer.beginObject();
		writer.name(NAME_TRANSACTIONS);
		writer.beginArray();
		for (Transaction txn : value.getTransactions()) {
			writer.value(toEntryptedString(txn));
		}
		writer.endArray();
		writer.endObject();
	}

	private String toEntryptedString(Transaction txn) {
		String json = gson.toJson(txn);
		return encryptorProviderService.getEncryptor().encrypt(json);
	}

	@Override
	public Transactions read(JsonReader reader) throws IOException {
		reader.beginObject();
		checkState(reader.nextName().equals(NAME_TRANSACTIONS));
		reader.beginArray();

		ImmutableList.Builder<Transaction> elements = ImmutableList.builder();
		while (reader.hasNext()) {
			elements.add(readTransaction(reader.nextString()));
		}

		reader.endArray();
		reader.endObject();
		return new Transactions(elements.build());
	}

	private Transaction readTransaction(String string) {
		String json = encryptorProviderService.getEncryptor().decrypt(string);
		Transaction transaction = checkNotNull(gson.fromJson(json, Transaction.class));
		if (transaction.getId() == null) {
			transaction = transaction.withId(UUID.randomUUID().toString());
		}
		return transaction;
	}
}
