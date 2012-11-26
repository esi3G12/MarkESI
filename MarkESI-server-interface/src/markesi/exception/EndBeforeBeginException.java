/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.exception;

/**
 *
 * @author g30917
 */
public class EndBeforeBeginException extends Exception {

    /**
     * Creates a new instance of
     * <code>EndBeforeBeginException</code> without detail message.
     */
    public EndBeforeBeginException() {
    }

    /**
     * Constructs an instance of
     * <code>EndBeforeBeginException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public EndBeforeBeginException(String msg) {
        super(msg);
    }
}
