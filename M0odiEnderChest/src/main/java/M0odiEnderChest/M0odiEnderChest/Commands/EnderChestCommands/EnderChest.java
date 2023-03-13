package M0odiEnderChest.M0odiEnderChest.Commands.EnderChestCommands;

import M0odiEnderChest.M0odiEnderChest.Builders.InventoryBuilder;
import M0odiEnderChest.M0odiEnderChest.Commands.AbstractCommand;
import M0odiEnderChest.M0odiEnderChest.Events.InventoryInteracting;
import M0odiEnderChest.M0odiEnderChest.Objects.M0odiCustomEnderChest;
import M0odiEnderChest.M0odiEnderChest.Providers.EnderChestProvider;
import M0odiEnderChest.M0odiEnderChest.Storages.MessageStorage;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class EnderChest extends AbstractCommand {

    public EnderChest(String nameCommand) {
        super(nameCommand);
    }

    @Override
    protected void executeCommand(CommandSender sender, String[] args) {

        //... Получаем нужные объекты для работы...
        M0odiCustomEnderChest customEnderChest;
        Player player = (Player) sender;

        //... В случае, если аргументов 0, отправитель хочет открыть
        // свой Эндер Сундук...
        if (args.length == 0) {

            customEnderChest = EnderChestProvider.getEnderChestByNickname(sender.getName());

        //... В случае, если аргументы есть, отправитель хочет
        // открыть чужой Эндер Сундук...
        } else {

            customEnderChest = EnderChestProvider.getEnderChestByNickname(args[0]);
            InventoryInteracting.HOLDERS_NOT_OWNER.put(player, customEnderChest);

        }

        //... Открываем Эндер Сундук...
        Inventory inventory = InventoryBuilder.getEnderChestInventory(player, customEnderChest.getOwner());
        player.openInventory(inventory);
        InventoryInteracting.HOLDERS.put(player, inventory);

    }

    @Override
    protected boolean feasibilityCheck(CommandSender sender, String[] args) {

        //... Если отправитель - консоль, отменяем исполнение команды...
        if (sender instanceof ConsoleCommandSender) {
            System.out.println("Команда только для игроков");
            return false;
        }

        //... Если у исполнителя нет прав на использование команды,
        // отменяем исполнение команды...
        if (!sender.hasPermission("m0odi-chest.enderchest")) {
            sender.sendMessage(MessageStorage.NOT_PERMS.getMessage());
            return false;
        }

        //... Если игрок хочет проверить чужой Эндер Сундук, но у него нет
        // права на это, отменяем исполнение команды...
        if (args.length >= 1 && !sender.hasPermission("m0odi-chest.enderchest.other")) {
            sender.sendMessage(MessageStorage.NOT_PERMS.getMessage());
            return false;
        }

        //... Все проверки выполнены, продолжаем исполнение команды...
        return true;

    }
}
