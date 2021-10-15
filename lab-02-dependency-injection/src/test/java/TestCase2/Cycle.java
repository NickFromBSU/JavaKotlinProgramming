package TestCase2;

import javax.inject.Inject;

public class Cycle {
    @Inject
    public Cycle(First f, Second s) {}
}
