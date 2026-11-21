package h04;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StructureSerializer {

    private static final List<String> CLASSES = List.of(
        "h04.swarms.I",
        "h04.swarms.L",
        "h04.swarms.O",
        "h04.swarms.S",
        "h04.swarms.Swarm",
        "h04.swarms.T",
        "h04.swarms.TestSwarm",
        "h04.GameLoop",
        "h04.Main",
        "h04.ProjectionMatrix",
        "h04.TetrisWorld",
        "h04.Tetrobot",
        "h04.Tetromino",
        "h04.Utils"
    );

    @Test
    public void run() {
        ArrayNode root = JsonNodeFactory.instance.arrayNode();
        CLASSES.stream().map(StructureSerializer::parseClass).forEach(root::add);
        System.out.println(root.toPrettyString());
    }

    private static ObjectNode parseClass(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        ObjectNode root = JsonNodeFactory.instance.objectNode();
        root.put("type", getClassType(clazz));
        root.put("typeName", className);
        ArrayNode constructors = root.putArray("declaredConstructors");
        Arrays.stream(clazz.getDeclaredConstructors())
            .filter(constructor -> (constructor.getModifiers() & Modifier.PRIVATE) == 0)
            .map(StructureSerializer::parseConstructor)
            .forEach(constructors::add);
        ArrayNode methods = root.putArray("declaredMethods");
        Arrays.stream(clazz.getDeclaredMethods())
            .filter(method -> (method.getModifiers() & Modifier.PRIVATE) == 0)
            .map(StructureSerializer::parseMethod)
            .forEach(methods::add);
        return root;
    }

    private static ObjectNode parseConstructor(Constructor<?> constructor) {
        return JsonNodeFactory.instance.objectNode()
            .put("name", constructor.getDeclaringClass().getTypeName())
            .put("parameters", Arrays.stream(constructor.getParameterTypes()).map(Class::getTypeName).collect(Collectors.joining(", ")));
    }

    private static ObjectNode parseMethod(Method method) {
        return JsonNodeFactory.instance.objectNode()
            .put("name", method.getName())
            .put("parameters", Arrays.stream(method.getParameterTypes()).map(Class::getTypeName).collect(Collectors.joining(", ")));
    }

    // Utils

    private static String getClassType(Class<?> clazz) {
        if (clazz.isInterface())
            return "interface";
        else if (clazz.isEnum())
            return "enum";
        else if (clazz.isRecord())
            return "record";
        else if (clazz.isAnnotation())
            return "annotation";
        else
            return "class";
    }
}
