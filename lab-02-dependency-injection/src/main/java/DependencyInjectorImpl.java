import javax.inject.Inject;
import javax.inject.Singleton;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class DependencyInjectorImpl implements DependencyInjector {
    private interface Node<T> {
        T getInstance();
    }

    static private class NonSingletonNode<T> implements Node<T> {
        private final Constructor<T> constructor;
        private final ArrayList<Node<?>> argumentsOfConstructor;

        NonSingletonNode(Constructor<?> constructor, ArrayList<Node<?>> args) {
            this.constructor = (Constructor<T>) constructor;
            argumentsOfConstructor = args;
        }

        public T getInstance() {
            Object[] instantiatedArguments = new Object[argumentsOfConstructor.size()];
            for (int i = 0; i < argumentsOfConstructor.size(); i++) {
                instantiatedArguments[i] = argumentsOfConstructor.get(i).getInstance();
            }
            try {
                return constructor.newInstance(instantiatedArguments);
            } catch (InstantiationException | IllegalAccessException
                    | InvocationTargetException e) {
                throw new RuntimeException("Unknown Error");
            }
        }
    }

    static private class SingletonNode<T> implements Node<T> {
        private final T instance;

        SingletonNode(T instance) {
            this.instance = instance;
        }

        @Override
        public T getInstance() {
            return instance;
        }
    }


    private final HashMap<Class<?>, Node<?>> mClassToNode = new HashMap<>();
    private final HashSet<Class<?>> mVisited = new HashSet<>();
    private final HashMap<Class<?>, Class<?>> mInterfaceToImpl = new HashMap<>();

    private Constructor<?> findProperConstructor(Class<?> type) {
        ArrayList<Constructor<?>> properConstructors = new ArrayList<>();
        for (var constructor : type.getConstructors()) {
            if (constructor.isAnnotationPresent(Inject.class)) {
                properConstructors.add(constructor);
            }
        }
        if (properConstructors.isEmpty()) {
            throw new RuntimeException(type.getName() + " has no public Inject constructors.");
        }
        if (properConstructors.size() > 1) {
            throw new RuntimeException(type.getName() +
                    " has more than one public Inject constructors.");
        }
        return properConstructors.get(0);
    }

    private void addSingleton(Class<?> type) {
        try {
            Object singleInstance = type.getDeclaredField("INSTANCE").get(null);
            mClassToNode.put(type, new SingletonNode<>(singleInstance));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("No public static field INSTANCE in singleton-class " +
                    type.getName());
        }
    }

    private void addNonSingleton(Class<?> type) {
        Constructor<?> constructor = findProperConstructor(type);
        Class<?>[] params = constructor.getParameterTypes();
        ArrayList<Node<?>> nodesForParamsOfConstructor = new ArrayList<>();
        for (Class<?> parameterType : params) {
            learnToInstantiate(parameterType);
            nodesForParamsOfConstructor.add(mClassToNode.get(parameterType));
        }
        mClassToNode.put(type, new NonSingletonNode<>(constructor, nodesForParamsOfConstructor));
    }

    private void learnToInstantiate(Class<?> type) {
        if (type.isInterface()) {
            Class<?> impl = mInterfaceToImpl.get(type);
            if (impl == null) {
                throw new RuntimeException("Interface " + type.getName() +
                        " has no chosen implementation.");
            }
            return;
        }
        if (mClassToNode.containsKey(type)) {
            return;
        }
        if (mVisited.contains(type)) {
            throw new RuntimeException(type.getName() + " is its own dependency.");
        }
        mVisited.add(type);

        if (type.isAnnotationPresent(Singleton.class)) {
            addSingleton(type);
            return;
        }

        addNonSingleton(type);
    }

    @Override
    public void register(Class<?> type) {
        mVisited.clear();
        learnToInstantiate(type);
    }

    @Override
    public <T> void register(Class<T> type, Class<? extends T> subType) {
        if (!type.isInterface() || subType.isInterface()) {
            throw new RuntimeException(type.getName() + " is not an interface or " +
                    subType.getName() + " is not a class");
        }
        mInterfaceToImpl.put(type, subType);
        register(subType);
    }

    @Override
    public <T> T resolve(Class<T> type) {
        Class<?> impl = mInterfaceToImpl.get(type);
        if (impl == null) {
            impl = type;
        }
        Node<T> node = (Node<T>)mClassToNode.get(impl);
        if (node == null) {
            throw new RuntimeException(impl.getName() + " is not registered.");
        }
        return node.getInstance();
    }
}