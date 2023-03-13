package M0odiEnderChest.M0odiEnderChest.Builders;

import M0odiEnderChest.M0odiEnderChest.M0odiEnderChest;
import M0odiEnderChest.M0odiEnderChest.Objects.M0odiCustomEnderChest;
import M0odiEnderChest.M0odiEnderChest.Providers.EnderChestProvider;
import M0odiEnderChest.M0odiEnderChest.Storages.GUIItemsStorage;
import M0odiEnderChest.M0odiEnderChest.Utils.ChatUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class InventoryBuilder {

    public static Inventory getEnderChestInventory(Player sender, String nicknamePlayer) {

        //... Получаем объект нужного для открытия Эндер сундука...
        M0odiCustomEnderChest customEnderChest
                = EnderChestProvider.getEnderChestByNickname(nicknamePlayer);


        //... Создаем кастомный Эндер Сундук...
        Inventory inventory =
                Bukkit.createInventory(sender, 6 * 9,
                        Component.newline().content
                                (ChatUtils.convertColorCodes(M0odiEnderChest.getInstance()
                                        .getConfig().getString("title-chest", "§0Эндер Сундук"))));

        //... Помещаем в него вещи...
        customEnderChest.getItems().forEach(item ->
                inventory.setItem(item.getSlot(), item.getItemStack()));

        //... Заполняем невыкупленные поля...
        for (int i = customEnderChest.getRows() * 9; i < 54; i++) {
            //... Заполняем четвертый ряд...
            if (i >= 27 && i < 36) inventory.setItem(i, GUIItemsStorage.ROW_4.getItem().convertToItemStack());
            //... Заполняем пятый ряд...
            if (i >= 36 && i < 45) inventory.setItem(i, GUIItemsStorage.ROW_5.getItem().convertToItemStack());
            //... Заполняем шестой ряд...
            if (i >= 45) inventory.setItem(i, GUIItemsStorage.ROW_6.getItem().convertToItemStack());
        }

        return inventory;
    }

}
