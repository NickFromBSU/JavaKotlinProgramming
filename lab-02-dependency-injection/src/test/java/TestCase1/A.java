package TestCase1;

import javax.inject.Inject;

public class A {
    public C c1;
    public D d;
    public C c2;

    @Inject
    public A(C c1, D d, C c2) {
        this.c1 = c1;
        this.d = d;
        this.c2 = c2;
    }
}
