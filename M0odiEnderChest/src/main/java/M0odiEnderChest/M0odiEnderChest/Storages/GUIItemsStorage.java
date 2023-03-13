package M0odiEnderChest.M0odiEnderChest.Storages;

import M0odiEnderChest.M0odiEnderChest.M0odiEnderChest;
import M0odiEnderChest.M0odiEnderChest.Objects.M0odiGUIItem;
import M0odiEnderChest.M0odiEnderChest.Utils.ChatUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@AllArgsConstructor @SuppressWarnings("ConstantConditions")
public enum GUIItemsStorage {

    //... Берем информацию из конфига и превращаем ее в объект M0odiGUIItem
    // для последующего отображения в GUI Эндер Сундука...

    ROW_4 (new M0odiGUIItem
            (ChatUtils.convertColorCodes(M0odiEnderChest.getInstance().getConfig().getString("items.row-4-item.display-name")),
            ChatUtils.convertColorCodes(M0odiEnderChest.getInstance().getConfig().getStringList("items.row-4-item.lore")),
            new ItemStack(Material.valueOf(M0odiEnderChest.getInstance().getConfig().getString("items.row-4-item.material").toUpperCase()),
                          M0odiEnderChest.getInstance().getConfig().getInt("items.row-4-item.amount")))),
    ROW_5 (new M0odiGUIItem
            (ChatUtils.convertColorCodes(M0odiEnderChest.getInstance().getConfig().getString("items.row-5-item.display-name")),
                    ChatUtils.convertColorCodes(M0odiEnderChest.getInstance().getConfig().getStringList("items.row-5-item.lore")),
                    new ItemStack(Material.valueOf(M0odiEnderChest.getInstance().getConfig().getString("items.row-5-item.material").toUpperCase()),
                            M0odiEnderChest.getInstance().getConfig().getInt("items.row-5-item.amount")))),
    ROW_6 (new M0odiGUIItem
            (ChatUtils.convertColorCodes(M0odiEnderChest.getInstance().getConfig().getString("items.row-6-item.display-name")),
                    ChatUtils.convertColorCodes(M0odiEnderChest.getInstance().getConfig().getStringList("items.row-6-item.lore")),
                    new ItemStack(Material.valueOf(M0odiEnderChest.getInstance().getConfig().getString("items.row-6-item.material").toUpperCase()),
                            M0odiEnderChest.getInstance().getConfig().getInt("items.row-6-item.amount")))),
    CONFIRM_ITEM (new M0odiGUIItem
            (ChatUtils.convertColorCodes(M0odiEnderChest.getInstance().getConfig().getString("items.confirm-item.display-name")),
                    ChatUtils.convertColorCodes(M0odiEnderChest.getInstance().getConfig().getStringList("items.confirm-item.lore")),
                    new ItemStack(Material.valueOf(M0odiEnderChest.getInstance().getConfig().getString("items.confirm-item.material").toUpperCase()),
                            M0odiEnderChest.getInstance().getConfig().getInt("items.confirm-item.amount"))));

    @Getter
    private final M0odiGUIItem item;


}
