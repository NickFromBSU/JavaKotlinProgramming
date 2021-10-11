import TestCase1.Base;
import TestCase1.D;
import TestCase1.Single;

import static org.junit.jupiter.api.Assertions.*;


public class Test {
    @org.junit.jupiter.api.Test
    public void TestCase1() {
        DependencyInjector di = new DependencyInjectorImpl();
        di.register(Base.class);
        Base base1 = di.resolve(Base.class);
        Base base2 = di.resolve(Base.class);
        assertNotSame(base1, base2);
        assertNotSame(base1.a, base2.a);
        assertNotSame(base1.a.d, base2.a.d);
        assertNotSame(base1.a.c1, base2.a.c1);
        assertNotSame(base1.a.c2, base2.a.c2);
        assertNotSame(base1.a.c1.d1, base1.a.c2.d2);
        assertSame(base1.a.d.single, base2.a.d.single);

        Single single = di.resolve(Single.class);
        assertSame(single, base1.a.d.single);

        D d = di.resolve(D.class);
        assertSame(d.single, single);
        assertNotSame(d, base1.a.d);
    }
}
