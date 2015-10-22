package greensopinion.finance.services.bridge;

public class ConsoleBridge {

	public void log(String text) {
		System.err.println(text);
	}

	public void info(String text) {
		log(text);
	}

	public void warn(String text) {
		log(text);
	}

	public void error(String text) {
		log(text);
	}
}
