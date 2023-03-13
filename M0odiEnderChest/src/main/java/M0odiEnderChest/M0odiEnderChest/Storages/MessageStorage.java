package M0odiEnderChest.M0odiEnderChest.Storages;

import M0odiEnderChest.M0odiEnderChest.M0odiEnderChest;
import M0odiEnderChest.M0odiEnderChest.Utils.ChatUtils;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MessageStorage {

    //... Хранение сообщений...

    NOT_PERMS (M0odiEnderChest.getInstance().getConfig().getString("messages.not-perms")),

    NOT_PERM_ROW_4 (M0odiEnderChest.getInstance().getConfig().getString("messages.not-perm-row-4")),
    NOT_PERM_ROW_5 (M0odiEnderChest.getInstance().getConfig().getString("messages.not-perm-row-5")),
    NOT_PERM_ROW_6 (M0odiEnderChest.getInstance().getConfig().getString("messages.not-perm-row-6")),

    NOT_ENOUGH_MONEY (M0odiEnderChest.getInstance().getConfig().getString("messages.not-enough-money")),

    NOT_HAVE_PREVIOUS (M0odiEnderChest.getInstance().getConfig().getString("messages.not-have-previous")),

    BOUGHT_ROW (M0odiEnderChest.getInstance().getConfig().getString("messages.bought-row"));

    private final String message;

    public String getMessage() {
        return ChatUtils.convertColorCodes(message);
    }
}
