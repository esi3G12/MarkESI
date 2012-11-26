/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.exception;

/**
 *
 * @author g30917
 */
public class IntervalOverlapException extends Exception {

    /**
     * Creates a new instance of
     * <code>IntervalOverlapException</code> without detail message.
     */
    public IntervalOverlapException() {
    }

    /**
     * Constructs an instance of
     * <code>IntervalOverlapException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public IntervalOverlapException(String msg) {
        super(msg);
    }
}
