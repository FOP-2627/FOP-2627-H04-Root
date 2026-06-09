package h04;

import org.tudalgo.algoutils.tutor.general.SpoonUtils;
import spoon.reflect.code.CtConditional;
import spoon.reflect.code.CtInvocation;
import spoon.reflect.code.CtReturn;
import spoon.reflect.code.CtStatement;
import spoon.reflect.declaration.CtMethod;
import spoon.reflect.declaration.CtType;
import spoon.reflect.factory.Factory;
import spoon.reflect.reference.CtTypeReference;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public final class TestUtils {

    private static Factory spoonFactory;
    private static final Map<String, CtType<?>> SPOON_TYPE_CACHE = new HashMap<>();
    private static final Map<Class<?>, CtTypeReference<?>> SPOON_TYPE_REF_CACHE = new HashMap<>();

    private TestUtils() {
    }

    public static Class<?> requireClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            fail("Required class %s does not exist".formatted(className));
            throw new AssertionError(e);
        }
    }

    public static Constructor<?> requireConstructor(Class<?> clazz, Class<?>... parameterTypes) {
        try {
            return clazz.getConstructor(parameterTypes);
        } catch (NoSuchMethodException e) {
            fail("%s must declare a public constructor with parameter types %s"
                .formatted(clazz.getName(), Arrays.toString(parameterTypes)));
            throw new AssertionError(e);
        }
    }

    public static Method requireMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            return clazz.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            fail("%s must declare or inherit method %s%s"
                .formatted(clazz.getName(), methodName, Arrays.toString(parameterTypes)));
            throw new AssertionError(e);
        }
    }

    public static Method requireDeclaredMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            fail("%s must declare method %s%s"
                .formatted(clazz.getName(), methodName, Arrays.toString(parameterTypes)));
            throw new AssertionError(e);
        }
    }

    public static Object newInstance(Constructor<?> constructor, Object... args) {
        try {
            return constructor.newInstance(args);
        } catch (ReflectiveOperationException e) {
            fail("Constructor invocation failed: " + e);
            throw new AssertionError(e);
        }
    }

    public static Object invoke(Method method, Object target, Object... args) {
        try {
            return method.invoke(target, args);
        } catch (ReflectiveOperationException e) {
            fail("Method invocation failed: " + e);
            throw new AssertionError(e);
        }
    }

    public static void assertPublicClassExtends(String className, Class<?> superClass) {
        Class<?> clazz = requireClass(className);
        assertTrue(Modifier.isPublic(clazz.getModifiers()), className + " must be public");
        assertEquals(superClass, clazz.getSuperclass(), className + " must directly extend " + superClass.getName());
    }

    public static CtType<?> getSpoonType(String className) {
        return SPOON_TYPE_CACHE.computeIfAbsent(className, key -> {
            CtType<?> type = SpoonUtils.getType(key);
            if (spoonFactory == null) {
                spoonFactory = type.getFactory();
            }
            return type;
        });
    }

    public static CtMethod<?> getSpoonMethod(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        CtType<?> ctType = getSpoonType(clazz.getName());
        CtTypeReference<?>[] parameterTypeRefs = Arrays.stream(parameterTypes)
            .map(TestUtils::getSpoonTypeRef)
            .toArray(CtTypeReference[]::new);
        CtMethod<?> method = ctType.getMethod(methodName, parameterTypeRefs);
        assertNotNull(method, "%s.%s%s must exist".formatted(clazz.getSimpleName(), methodName, Arrays.toString(parameterTypes)));
        return method;
    }

    public static void assertSingleConditionalReturn(Class<?> clazz, String methodName, Class<?>... parameterTypes) {
        CtMethod<?> method = getSpoonMethod(clazz, methodName, parameterTypes);
        assertEquals(1, method.getBody().getStatements().size(),
            "%s.%s must consist of exactly one statement".formatted(clazz.getSimpleName(), methodName));
        assertInstanceOf(CtReturn.class, method.getBody().getStatements().get(0),
            "%s.%s must consist of a return statement".formatted(clazz.getSimpleName(), methodName));
        CtReturn<?> returnStatement = (CtReturn<?>) method.getBody().getStatements().get(0);
        assertInstanceOf(CtConditional.class, returnStatement.getReturnedExpression(),
            "%s.%s must use the conditional operator".formatted(clazz.getSimpleName(), methodName));
        assertEquals(returnStatement.getPosition().getLine(), returnStatement.getPosition().getEndLine(),
            "%s.%s must consist of a single physical return line".formatted(clazz.getSimpleName(), methodName));
    }

    public static void assertCallsSuperSpawn(Class<?> clazz) {
        CtMethod<?> method = getSpoonMethod(clazz, "spawn");
        assertFalse(method.getBody().getStatements().isEmpty(),
            clazz.getSimpleName() + ".spawn() must call super.spawn() as its first statement");
        CtStatement firstStatement = method.getBody().getStatements().get(0);
        assertInstanceOf(CtInvocation.class, firstStatement,
            clazz.getSimpleName() + ".spawn() must call super.spawn() as its first statement");
        CtInvocation<?> invocation = (CtInvocation<?>) firstStatement;
        assertEquals("spawn", invocation.getExecutable().getSimpleName(),
            clazz.getSimpleName() + ".spawn() must call super.spawn() as its first statement");
        assertNotNull(invocation.getTarget(),
            clazz.getSimpleName() + ".spawn() must call super.spawn() as its first statement");
        assertEquals("super", invocation.getTarget().toString(),
            clazz.getSimpleName() + ".spawn() must call super.spawn() as its first statement");
    }

    public static void assertBooleanMatrixEquals(boolean[][] expected, boolean[][] actual, String message) {
        assertEquals(expected.length, actual.length, message);
        for (int y = 0; y < expected.length; y++) {
            assertEquals(expected[y].length, actual[y].length, message);
            for (int x = 0; x < expected[y].length; x++) {
                assertEquals(expected[y][x], actual[y][x], message + " at [%d][%d]".formatted(y, x));
            }
        }
    }

    private static CtTypeReference<?> getSpoonTypeRef(Class<?> clazz) {
        return SPOON_TYPE_REF_CACHE.computeIfAbsent(clazz, cls -> {
            if (cls.getName().startsWith("h04.")) {
                return getSpoonType(cls.getName()).getReference();
            }
            return spoonFactory.createCtTypeReference(cls);
        });
    }
}
