package ufu.sd.mobilePlatform.exception;

public class UnavailableServiceException extends Exception {
	public UnavailableServiceException(String name) {
		super("Unavailable service "+name);
	}
}
