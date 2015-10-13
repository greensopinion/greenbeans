package greensopinion.finance.services.demo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;

import greensopinion.finance.services.application.Main;
import greensopinion.finance.services.domain.Transaction;
import greensopinion.finance.services.domain.Transactions;
import greensopinion.finance.services.domain.TransactionsService;
import greensopinion.finance.services.encryption.EncryptorService;

public class Demo {

	private static final long MORTGAGE_AMOUNT = -140000L;

	private static final long PAY_AMOUNT = 80 * 1000 * 100 / (2 * 12L);

	static class Payee {
		private final int weight;
		private final String name;
		private final long amount;

		public Payee(int weight, String name, long amount) {
			this.weight = weight;
			this.name = name;
			this.amount = amount;
		}
	}

	private final List<Payee> payees = ImmutableList.of(//
			new Payee(1, "SAFEWAY #234", -12349L), //
			new Payee(1, "BUY LOW FOODS INC.", -5423L), //
			new Payee(1, "LOGAN FINE MEATS", -2301L), //
			new Payee(10, "STARBUCKS-234", -486L), //
			new Payee(1, "LONDON DRUGS      234", -4359L), //
			new Payee(1, "AMAZON.CO 523423", -2199L), //
			new Payee(1, "ESSO #2352", -6543L), //
			new Payee(1, "SHELL OIL 23", -6543L), //
			new Payee(1, "BC LIQUOR #110 SURREY", -1496L), //
			new Payee(1, "PYT FRM: 111223423112", -8878L), //
			new Payee(1, "AMAZON MKTPLACE PMTS AMZN.CO", -3754L), //
			new Payee(1, "SAFEWAY #4939 SURREY", -2257L), //
			new Payee(1, "AMAZON MKTPLACE PMTS AMZN.CO", -1035L), //
			new Payee(1, "SAFEWAY #4939 SURREY", -968L), //
			new Payee(1, "APL* ITUNES.COM/BILL 800-676", -599L), //
			new Payee(1, "SAFEWAY #4939 SURREY", -11470L), //
			new Payee(1, "WAL-MART SUPERCENTER#5853SURREY", -9320L), //
			new Payee(1, "AMAZON MKTPLACE PMTS AMZN.CO", -7064L), //
			new Payee(1, "CASBAH DAY SPA SURREY", -6804L), //
			new Payee(1, "CHAN'S FARM MARKET SURREY", -4623L), //
			new Payee(1, "SECURTEK MONITORING--SK YORKTON", -2835L), //
			new Payee(1, "Amazon Services-Kindle 866-321", -513L), //
			new Payee(1, "AMAZON MKTPLACE PMTS AMZN.CO", -251L), //
			new Payee(1, "NON-TD ATM W/D", -6300L), //
			new Payee(1, "SAFEWAY #4939 SURREY", -4400L));

	private static final String DEMO_PASSWORD = "demo";
	private static final String SYSTEM_PROPERTY_DEMO_MODE = "demoMode";

	public static boolean isEnabled() {
		return Boolean.getBoolean(SYSTEM_PROPERTY_DEMO_MODE);
	}

	private final Random random = new Random();
	private Injector injector;

	public void setup() {
		EncryptorService encryptorService = injector().getInstance(EncryptorService.class);
		if (encryptorService.isConfigured()) {
			encryptorService.initialize(DEMO_PASSWORD);
		} else {
			encryptorService.configure(DEMO_PASSWORD);
		}

		createTransactions();
	}

	private void createTransactions() {
		List<Transaction> transactions = new ArrayList<>();

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		for (int x = 0; x < 6; ++x) {
			transactions.addAll(createTransactions(calendar.getTime()));
			calendar.add(Calendar.MONTH, -1);
		}

		TransactionsService transactionsService = injector().getInstance(TransactionsService.class);
		transactionsService.update(new Transactions(transactions));
	}

	private List<Transaction> createTransactions(Date date) {
		List<Transaction> transactions = new ArrayList<>();

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		final int month = calendar.get(Calendar.MONTH);
		while (calendar.get(Calendar.MONTH) == month) {
			calendar.add(Calendar.DAY_OF_MONTH, 1);

			if (isPayday(calendar)) {
				transactions.add(new Transaction(calendar.getTime(), "ACME INC. PAY", PAY_AMOUNT, null, null));
				transactions
						.add(new Transaction(calendar.getTime(), "BIG BANK MORT. 123052", MORTGAGE_AMOUNT, null, null));
			}
			long dayTotal = 0;
			for (int x = 0; x < 6; ++x) {
				Transaction purchase = purchase(calendar.getTime());
				dayTotal += purchase.getAmount();
				if (Math.abs(dayTotal) > perDayMaximumPurchases()) {
					break;
				}
				transactions.add(purchase);
			}
		}
		return transactions;
	}

	private long perDayMaximumPurchases() {
		long amount = PAY_AMOUNT * 2;
		amount += MORTGAGE_AMOUNT * 2;
		return amount / 30;
	}

	private Transaction purchase(Date time) {
		int totalWeight = 0;
		for (Payee payee : payees) {
			totalWeight += payee.weight;
		}
		int index = Math.abs(random.nextInt()) % totalWeight;
		for (Payee payee : payees) {
			index -= payee.weight;
			if (index <= 0) {
				return new Transaction(time, payee.name, payee.amount, null, null);
			}
		}
		throw new IllegalStateException();
	}

	private boolean isPayday(GregorianCalendar calendar) {
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		return day == 15 || day == 30;
	}

	private Injector injector() {
		if (injector == null) {
			injector = Guice.createInjector(Main.applicationModules());
		}
		return injector;
	}
}
