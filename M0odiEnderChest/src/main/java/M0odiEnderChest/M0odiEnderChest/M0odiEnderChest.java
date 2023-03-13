package M0odiEnderChest.M0odiEnderChest;

import M0odiEnderChest.M0odiEnderChest.Commands.EnderChestCommands.EnderChest;
import M0odiEnderChest.M0odiEnderChest.Events.InventoryInteracting;
import M0odiEnderChest.M0odiEnderChest.Storages.ResourcesStorage;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class M0odiEnderChest extends JavaPlugin {

    @Getter
    private static M0odiEnderChest instance;

    @Getter
    private static ResourcesStorage enderChests;

    @Getter
    private static Economy economy;

    @Override
    public void onEnable() {
        instance = this;

        if (!setupEconomy()) {
            Bukkit.getLogger().info("Не найден Vault. Отключение...");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        new EnderChest("enderchest");
        new EnderChest("ec");

        saveDefaultConfig();
        enderChests = new ResourcesStorage("ender-chests.yml");

        Bukkit.getPluginManager().registerEvents(new InventoryInteracting(), this);
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) return false;

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) return false;

        economy = rsp.getProvider();
        return true;
    }

    @Override
    public void onDisable() {

        //... В случае отключения плагина закрываем у всех
        // игроков кастомные Эндер Сундуки для избежания багов...
        InventoryInteracting.HOLDERS.forEach((player, itemStacks) ->
                player.closeInventory());

        InventoryInteracting.HOLDERS_NOT_OWNER.forEach(((player, customEnderChest) ->
                player.closeInventory()));

    }

}
