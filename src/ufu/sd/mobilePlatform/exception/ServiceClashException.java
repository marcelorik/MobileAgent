package ufu.sd.mobilePlatform.exception;

public class ServiceClashException extends Exception {
	public ServiceClashException(String name) {
		super("The Agency already have a service with the name "+name);
	}
}
