package M0odiEnderChest.M0odiEnderChest.Providers;

import M0odiEnderChest.M0odiEnderChest.M0odiEnderChest;
import M0odiEnderChest.M0odiEnderChest.Objects.M0odiCustomEnderChest;
import M0odiEnderChest.M0odiEnderChest.Utils.ItemsUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.configuration.ConfigurationSection;

import static java.util.UUID.randomUUID;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class EnderChestProvider {

    /**
     * Получить объект Эндер Сундука из файла ресурсов.
     *
     * @param nickname Никнейм игрока.
     * @return Эндер Сундук этого игрока.
     */
    public static M0odiCustomEnderChest getEnderChestByNickname(String nickname) {

        ConfigurationSection section =
            M0odiEnderChest.getEnderChests()
                    .getConfig().getConfigurationSection(nickname.toLowerCase());

        if (section == null) return createCustomEnderChest(nickname);

        return M0odiCustomEnderChest.builder()
                .owner(nickname.toLowerCase())
                .rows(section.getInt("rows"))
                .items(ItemsUtils.getItemsFromConfigurationSection(section.getConfigurationSection("items")))
                .build();
    }

    /**
     * Создать запись о Эндер Сундуке в файле ресурсов.
     *
     * @param nickname Никнейм игрока, для которого создается.
     * @return Созданный Эндер Сундук.
     */
    public static M0odiCustomEnderChest createCustomEnderChest(String nickname) {

        ConfigurationSection section =
                M0odiEnderChest.getEnderChests().getConfig().createSection(nickname.toLowerCase());

        section.set("rows", 3);

        M0odiEnderChest.getEnderChests().save();

        return getEnderChestByNickname(nickname);
    }

    /**
     * Обновляет запись о Эндер Сундуке в файле ресурсов.
     *
     * @param customEnderChest Эндер Сундук.
     */
    @SuppressWarnings("ConstantConditions")
    public static void updateCustomEnderChest(M0odiCustomEnderChest customEnderChest) {

        ConfigurationSection section =
                M0odiEnderChest.getEnderChests()
                        .getConfig().getConfigurationSection(customEnderChest.getOwner());

        section.set("rows", customEnderChest.getRows());
        section.set("items", null);

        customEnderChest.getItems().forEach(item -> {
            String randomUUID = randomUUID().toString();
            section.set("items." + randomUUID + ".slot", item.getSlot());
            section.set("items." + randomUUID + ".item", item.getItemStack());
        });

        M0odiEnderChest.getEnderChests().save();
    }

    /**
     * Удалить запись о Эндер Сундке из файла ресурсов.
     *
     * @param nickname Никнейм игрока
     */
    public static void deleteCustomEnderChest(String nickname) {
        M0odiEnderChest.getEnderChests().getConfig().set(nickname, null);
        M0odiEnderChest.getEnderChests().save();
    }

}
