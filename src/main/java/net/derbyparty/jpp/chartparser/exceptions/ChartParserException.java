package net.derbyparty.jpp.chartparser.exceptions;

/**
 * Superclass of all checked exceptions for this parser
 */
public class ChartParserException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChartParserException() {
    }

    public ChartParserException(String message) {
        super(message);
    }

    public ChartParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
