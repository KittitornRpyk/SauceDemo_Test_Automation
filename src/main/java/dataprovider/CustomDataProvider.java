package dataprovider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dataclass.LoginData;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CustomDataProvider {

    private CustomDataProvider() {

    }

    @DataProvider(name = "loginData")
    public static Object[][] getLoginData() {
        String validPassword = "secret_sauce";
        return new Object[][]{
                {new LoginData("standard_user", validPassword)},
                {new LoginData("locked_out_user", validPassword)},
                {new LoginData("problem_user", validPassword)},
                {new LoginData("performance_glitch_user", validPassword)},
                {new LoginData("error_user", validPassword)},
                {new LoginData("visual_user", validPassword)},
                {new LoginData("standard_user", "wrong_password")},
                {new LoginData("wrong_user", validPassword)},
                {new LoginData("wrong_user", "wrong_password")}
        };
    }

    @DataProvider(name = "dynamicJson")
    public static Object[][] provideJsonData(Method method) throws IOException {
        String testClassPath = method.getDeclaringClass().getName().replace(".", "/");
        String testDataPath = String.format("src/test/resources/%s.json", testClassPath);

        File file = Paths.get(testDataPath).toFile();
        if (!file.exists()) {
            throw new IOException("Test data file not found: " + file.getAbsolutePath());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(file);
        JsonNode testDataNode = rootNode.get(method.getName());

        if (testDataNode == null) {
            throw new IllegalArgumentException("No test data found for method: " + method.getName());
        }

        List<Object> parameters = new ArrayList<>();
        Iterator<String> fieldNames = testDataNode.fieldNames();
        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode fieldNode = testDataNode.get(fieldName);
            Class<?> paramType = getParameterType(method, fieldName);
            if (paramType != null) {
                parameters.add(objectMapper.treeToValue(fieldNode, paramType));
            }
        }

        return new Object[][]{parameters.toArray()};
    }

    private static Class<?> getParameterType(Method method, String fieldName) {
        for (Class<?> paramType : method.getParameterTypes()) {
            if (paramType.getSimpleName().equalsIgnoreCase(fieldName)) {
                return paramType;
            }
        }
        return null;
    }
}
