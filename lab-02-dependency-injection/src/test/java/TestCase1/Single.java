package TestCase1;

import javax.inject.Singleton;

@Singleton
public class Single {
    public static final Single INSTANCE = new Single();
    Integer val = 5;

    private Single() {}
}