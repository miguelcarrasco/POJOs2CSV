package org.tlacaelelsoftware.collection2csv.testhelpers;

/**
 * A Simple Plain Old Java Object (POJO) to test
 * csv conversion
 */
public class SimplePOJOForTest implements SimplePojo {
    private String user;
    private String email;
    private Long userId;
    private String phone;

    // This fake constructor is used because we want to test the same values for different
    // @CSVField annotations on this class, but this is not possible using inheritance in java
    // since is not possible to override fields or fields annotations.
    @Override
    public void fakeConstructor(String user, String email, Long userId, String phone) {
        this.user = user;
        this.email = email;
        this.userId = userId;
        this.phone = phone;

    }
}
