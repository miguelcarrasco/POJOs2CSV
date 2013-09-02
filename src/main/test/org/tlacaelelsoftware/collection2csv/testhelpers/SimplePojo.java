package org.tlacaelelsoftware.collection2csv.testhelpers;

/**
 * This class was created because we want to test different behaviors for
 * different annotations fields in the same class, but since there is no way
 * to override fields or annotations to this fields in java, we need to create
 * completely different classes with the same field structure but using different annotations
 * and since we want to test that classes using the same field values we were forced to
 * implement this interface with a "fake constructor".
 *
 */
public interface SimplePojo {
    void fakeConstructor(String user, String email, Long userId, String phone);
}
