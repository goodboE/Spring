package kodong.web_ide.builder;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class CompileBuilderTest {

    @Autowired
    CompileBuilder compileBuilder;

    @Test
    void compile() throws Exception {
        String code = "public class DynamicClass { public void printMessage() { System.out.println(\"Hello, Dynamic Class!\"); }}";

        try {
            Object result = compileBuilder.compileCode(code);

            if (result != null) {
                log.info("result: {}", result);
                result.getClass().getMethod("printMessage").invoke(result);
            } else {
                System.out.println("compile failed. ERROR : " + result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}