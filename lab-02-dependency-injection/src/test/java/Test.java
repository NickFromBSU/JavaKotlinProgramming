import TestCase1.*;
import TestCase2.Cycle;
import TestCase2.First;
import TestCase2.FirstInterface;
import TestCase2.Third;
import TestCase3.BadClass;
import TestCase3.MultipleInjectConstructorsClass;
import TestCase3.NoInjectConstructorClass;

import static org.junit.jupiter.api.Assertions.*;


public class Test {
    @org.junit.jupiter.api.Test
    public void TestCase1() {
        DependencyInjector di = new DependencyInjectorImpl();
        assertThrows(RuntimeException.class, () -> di.resolve(Base.class));
        assertThrows(RuntimeException.class, () -> di.resolve(A.class));
        assertThrows(RuntimeException.class, () -> di.resolve(C.class));
        assertThrows(RuntimeException.class, () -> di.resolve(D.class));
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

    @org.junit.jupiter.api.Test
    public void TestCase2() {
        DependencyInjector di = new DependencyInjectorImpl();

        // Because FirstInterface has no chosen implementation.
        assertThrows(RuntimeException.class, () -> di.register(Third.class));
        di.register(FirstInterface.class, First.class);
        di.register(Third.class);
        assertNotSame(di.resolve(Third.class), di.resolve(Third.class));
        // Because FirstInterface already has chosen implementation.
        assertThrows(RuntimeException.class, () -> di.register(FirstInterface.class, First.class));

        assertThrows(RuntimeException.class, () -> di.register(Cycle.class));
    }

    @org.junit.jupiter.api.Test
    public void TestCase3() {
        DependencyInjector di = new DependencyInjectorImpl();
        assertThrows(RuntimeException.class, () -> di.register(BadClass.class));
        assertThrows(RuntimeException.class, () -> di.register(NoInjectConstructorClass.class));
        assertThrows(RuntimeException.class,
                () -> di.register(MultipleInjectConstructorsClass.class));
    }
}
