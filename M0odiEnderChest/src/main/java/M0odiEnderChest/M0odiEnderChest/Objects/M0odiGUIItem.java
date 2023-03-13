package M0odiEnderChest.M0odiEnderChest.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

@Data @AllArgsConstructor @NonNull
public class M0odiGUIItem {

    String displayName; // Отображаемое имя

    List<String> lore; // Описание

    ItemStack itemStack; // Айтем, который представляет элемент

    @SuppressWarnings("deprecation")
    public ItemStack convertToItemStack() {

        //... Берем айтем мету...
        ItemMeta meta = itemStack.getItemMeta();

        //... Устанавливаем отображаемое имя и описание...
        meta.setDisplayName(displayName);
        meta.setLore(lore);

        //... Устанавливаем айтем мету айтему...
        itemStack.setItemMeta(meta);

        //... Возвращаем айтем с установленным именем и описанием...
        return itemStack;

    }

}
