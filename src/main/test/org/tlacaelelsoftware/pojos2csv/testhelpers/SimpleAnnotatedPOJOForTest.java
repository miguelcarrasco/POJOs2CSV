package org.tlacaelelsoftware.pojos2csv.testhelpers;

import org.tlacaelelsoftware.pojos2csv.CSVField;

/**
 * This is the SimplePOJOForTest but with different annotations, it was created
 * because in java it's impossible to override field annotations using inheritance.
 */
public class SimpleAnnotatedPOJOForTest implements SimplePojo {
    @CSVField(name = "User")
    private String user;

    @CSVField(name = "Email")
    private String email;

    @CSVField(ignore = true)
    private Long userId;

    @CSVField(name="Phone")
    private String phone;

    // This fake constructor is used because we want to test the same values for different
    // @CSVField annotations on the SimplePOJOForTest class, but this is not possible using inheritance in java
    // since is not possible to override fields or fields annotations.
    @Override
    public void fakeConstructor(String user, String email, Long userId, String phone) {
        this.user = user;
        this.email = email;
        this.userId = userId;
        this.phone = phone;

    }
}
