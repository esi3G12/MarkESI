/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package markesi.exceptions;

/**
 *
 * @author G35309
 */
public class MarkESIException extends Exception {

    /**
     * Creates a new instance of
     * <code>MarkESIException</code> without detail message.
     */
    public MarkESIException() {
    }

    /**
     * Constructs an instance of
     * <code>MarkESIException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public MarkESIException(String msg) {
        super(msg);
    }
}
