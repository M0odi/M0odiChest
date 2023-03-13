package M0odiEnderChest.M0odiEnderChest.Utils;

import M0odiEnderChest.M0odiEnderChest.Objects.M0odiItem;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class ItemsUtils {

    public static List<M0odiItem> getItemsFromConfigurationSection(ConfigurationSection section) {

        //... Если секции не существует, возвращаем пустой Эндер Сундук...
        if (section == null) {
            return new ArrayList<>(Collections.singleton(new M0odiItem(0, new ItemStack(Material.AIR))));
        }

        //... Пробегаемся по всей секции и заполняем список вещами...
        List<M0odiItem> items = new ArrayList<>();
        section.getKeys(false).forEach(uuidItem -> {
            M0odiItem m0odiItem = new M0odiItem
                    (section.getInt(uuidItem + ".slot"),
                     section.getItemStack(uuidItem + ".item"));
            items.add(m0odiItem);
        });

        //... Возвращаем полученный список вещей...
        return items;

    }

}
