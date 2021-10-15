public interface DependencyInjector {
    /**
     * Makes it possible to get instances of T using DependencyInjector.resolve(T.class).
     * The instances will be newly created if T is not a Singleton.
     */
    <T> void register(Class<T> type);

    /**
     * Let T be an interface of class V. (|type| is T.class and |subType| is V.class)
     * Matches interface T to proper implementation V - makes it possible to get
     * instances of V passing an interface T to "resolve"-method (resolve(T.class)).
     * Also registers class V (as if you have called Register(V.class)).
     * Should be called once for every needed interface T and before any call like
     * "register(M.class)", where T is a dependency of M.
     */
    <T> void register(Class<T> type, Class<? extends T> subType);

    /**
     * Returns an instance of T.
     * If T is marked with a @Singleton annotation, the instance will be always the same.
     * If T is a class with no @Singleton annotation, the instance will be newly created every time.
     * If T is an interface, returns an instance of proper implementing class V,
     * which should be previously determined within the call "register(T.class, V.class);".
     */
    <T> T resolve(Class<T> type);
}
