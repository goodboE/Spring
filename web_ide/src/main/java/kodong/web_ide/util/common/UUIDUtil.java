package kodong.web_ide.util.common;

import java.util.UUID;

public class UUIDUtil {
    public static String createUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
