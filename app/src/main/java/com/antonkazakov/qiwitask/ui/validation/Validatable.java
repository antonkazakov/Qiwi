package com.antonkazakov.qiwitask.ui.validation;

/**
 * All validators must implements this interface
 *
 * @author Anton Kazakov
 * @date 26.07.17.
 */

public interface Validatable<T> {

    boolean isValid(T t);

    /**
     * Error message to show in UI
     * @return
     */
    String getMessage();

}