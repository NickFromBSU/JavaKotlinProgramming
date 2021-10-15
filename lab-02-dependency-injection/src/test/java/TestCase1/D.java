package TestCase1;

import javax.inject.Inject;

public class D {
    public Single single;

    @Inject
    public D(Single singleton) {
        single = singleton;
    }
}
