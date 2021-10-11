package TestCase1;

import javax.inject.Inject;

public class C {
    public D d1;
    public D d2;

    @Inject
    public C(D d1, D d2) {
        this.d1 = d1;
        this.d2 = d2;
    }
}
