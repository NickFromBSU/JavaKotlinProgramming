import java.lang.reflect.InvocationTargetException;

public interface DependencyInjector {
    void register(Class<?> type);
    <T> void register(Class<T> type, Class<? extends T> subType);
    <T> T resolve(Class<T> type);
}
