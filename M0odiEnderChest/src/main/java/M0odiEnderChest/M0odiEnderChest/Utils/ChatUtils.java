package M0odiEnderChest.M0odiEnderChest.Utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class ChatUtils {

    public static String convertColorCodes(String message) {
        return message.replaceAll("&", "§");
    }

    public static List<String> convertColorCodes(List<String> message) {
        List<String> convertMessage = new ArrayList<>();

        message.forEach(string -> convertMessage.add(string.replaceAll("&", "§")));

        return convertMessage;
    }

}
