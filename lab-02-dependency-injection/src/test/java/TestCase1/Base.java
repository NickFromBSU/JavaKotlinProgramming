package TestCase1;

import javax.inject.Inject;

public class Base {
    public A a;

    @Inject
    public Base(A a) {
        this.a = a;
    }
}
