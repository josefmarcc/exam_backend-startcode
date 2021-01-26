/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package errorhandling;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
public class DuplicateException extends Exception {

    public DuplicateException(String message) {
        super(message);
    }
}
