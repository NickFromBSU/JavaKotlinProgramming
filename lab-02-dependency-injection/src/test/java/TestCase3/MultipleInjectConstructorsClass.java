package TestCase3;

import TestCase1.Base;

import javax.inject.Inject;

public class MultipleInjectConstructorsClass {
    @Inject
    public MultipleInjectConstructorsClass() {}

    @Inject
    public MultipleInjectConstructorsClass(Base b) {}

    @Inject
    public MultipleInjectConstructorsClass(Base b, Base b1) {}

    public MultipleInjectConstructorsClass(Base b, Base b1, Base b2) {}
}
