package M0odiEnderChest.M0odiEnderChest.Events;

import M0odiEnderChest.M0odiEnderChest.Builders.InventoryBuilder;
import M0odiEnderChest.M0odiEnderChest.M0odiEnderChest;
import M0odiEnderChest.M0odiEnderChest.Objects.M0odiCustomEnderChest;
import M0odiEnderChest.M0odiEnderChest.Objects.M0odiItem;
import M0odiEnderChest.M0odiEnderChest.Providers.EnderChestProvider;
import M0odiEnderChest.M0odiEnderChest.Storages.GUIItemsStorage;
import M0odiEnderChest.M0odiEnderChest.Storages.MessageStorage;
import M0odiEnderChest.M0odiEnderChest.Storages.PriceRowsStorage;
import M0odiEnderChest.M0odiEnderChest.Utils.InventoryUtils;
import M0odiEnderChest.M0odiEnderChest.Utils.ParticleUtils;
import M0odiEnderChest.M0odiEnderChest.Utils.SoundUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InventoryInteracting implements Listener {

    //... Мапа для хранения всех игроков, открывающих Эндер Сундуки...
    public static final HashMap<Player, Inventory> HOLDERS = new HashMap<>();

    //... Мапа для игроков, открывающих не свои Эндер Сундуки...
    public static final HashMap<Player, M0odiCustomEnderChest> HOLDERS_NOT_OWNER = new HashMap<>();



    /**
     * Обрабатывает открытие кастомного Эндер Сундука.
     */
    @EventHandler @SuppressWarnings("ConstantConditions")
    public void onEnderChestOpen(PlayerInteractEvent event) {

        //... Отменяем открытие, если игрок нажал на Эндер Сундук на шифте...
        if (event.getPlayer().isSneaking()) return;

        //... Проверка на то, происходит ли открытие именно Эндер Сундука...
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (!event.getClickedBlock().getType().equals(Material.ENDER_CHEST)) return;

        //... В случае, если происходит открытие именно Эндер Сундука,
        // отменяем событие для того, чтобы исключить
        // открытие дефолтного Эндер Сундука...
        event.setCancelled(true);

        //... Получаем объект игрока...
        Player player = event.getPlayer();

        //... Для вида создаем анимацию над открывающимся
        // Эндер Сундуком и проигрываем звук открытия...
        ParticleUtils.spawnOpenEnderChestParticles(player, event.getClickedBlock());
        SoundUtils.playOpenEnderChestSound(player);

        //... Открываем кастомный Эндер Сундук...
        Inventory customEnderChestInventory = InventoryBuilder.getEnderChestInventory(player, player.getName());
        player.openInventory(customEnderChestInventory);

        //... Помещаем игрока и открытый инвентарь в HOLDERS
        // для дальнейшего отслеживания кликов по этому инвентарю и т.д...
        HOLDERS.put(player, customEnderChestInventory);

    }

    /**
     * Обрабатывает закрытие кастомного Эндер Сундука.
     * Здесь же - обновление содержимого Эндер Сундука.
     */
    @EventHandler
    public void onEnderChestClose(InventoryCloseEvent event) {

        //... Получение объекта игрока...
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getPlayer();

        //... Если закрыт не кастомный Эндер Сундук - отменяем исполнение...
        if (!checkClickInventory(player, inventory)) return;

        //... Получаем кастомный Эндер Сундук игрока...
        M0odiCustomEnderChest customEnderChest;

        //... Если игрок открывает не свой Эндер Сундук...
        if (HOLDERS_NOT_OWNER.containsKey(player)) {
            customEnderChest = HOLDERS_NOT_OWNER.get(player);
        } else {
        //... Если игрок открывает свой Эндер Сундук...
            customEnderChest = EnderChestProvider.getEnderChestByNickname(player.getName());
        }

        //... Удаляем игрока из HOLDERS, чтобы не забивать его...
        HOLDERS.remove(player);
        HOLDERS_NOT_OWNER.remove(player);

        //... Предполагается, что в Эндер Сундуке произошли
        // какие-либо изменения. Обновляем запись в файле...
        List<M0odiItem> items = new ArrayList<>();
        for (int i = 0; i < customEnderChest.getRows() * 9; i++) {

            //... В цикле проходимся по всем купленным слотам.
            // Проверяем предмет на существование и добавляем его в лист...
            ItemStack item = inventory.getItem(i);
            if (item == null) continue;
            items.add(new M0odiItem(i, item));

        }

        //... Устанавливаем новое содержимое кастомного Эндер Сундука...
        customEnderChest.setItems(items);

        //... Обновляем запись о кастомном Эндер Сундуке...
        EnderChestProvider.updateCustomEnderChest(customEnderChest);

    }

    /**
     * Обрабатывает нажатия по кастомному Эндер Сундуку
     * (покупка, подтверждение и т.д)
     */
    @EventHandler @SuppressWarnings("ConstantConditions")
    public void onEnderChestInteracting(InventoryClickEvent event) {

        //... Получаем необходимые объекты для работы...
        Inventory inventory = event.getInventory();
        Player player = (Player) event.getWhoClicked();

        //... Если клик не по кастомному Эндер Сундуку - отменяем исполнение...
        if (!checkClickInventory(player, inventory)) return;

        //... Получаем необходимые данные для работы...
        int clickedSlot = event.getSlot();
        int clickedRow = InventoryUtils.getRowBySlot(clickedSlot);

        //... Если игрок взаимодействует с не своим Эндер Сундуком...
        if (HOLDERS_NOT_OWNER.containsKey(player)) {

            //... У игрока нет прав на взаимодействие. Отменяем...
            if (!player.hasPermission("m0odi-chest.enderchest.other.admin")) {
                event.setCancelled(true);
                return;
            }

            //... Получаем Эндер Сундук, с которым взаимодействует игрок...
            M0odiCustomEnderChest customEnderChest = HOLDERS_NOT_OWNER.get(player);

            //... Если клик произошел на неприобретенный слот отменяем событие...
            if (clickedSlot > ((customEnderChest.getRows() * 9) - 1)) event.setCancelled(true);

            return;
        }

        //... Получаем кастомный Эндер Сундук игрока...
        M0odiCustomEnderChest customEnderChest =
                EnderChestProvider.getEnderChestByNickname(player.getName());

        //... Если клик произошел по подтверждающему элементу (т.е игрок подтвердил покупку)...
        if (event.getCurrentItem() == null) return;
        if (event.getCurrentItem().equals(GUIItemsStorage.CONFIRM_ITEM.getItem().convertToItemStack())) {

            //... Отмена события...
            event.setCancelled(true);

            //... Проверяем, есть ли у игрока права на покупку данной строчки,
            // если нет - отменяем...
            if (!checkPermsForBuyRow(clickedRow, player)) return;

            //... Проверяем куплены ли все предыдущие строки, если нет - отменяем...
            if (!checkPreviousRows(clickedRow, customEnderChest)) {
                player.sendMessage(MessageStorage.NOT_HAVE_PREVIOUS.getMessage());
                return;
            }

            //... Проверяем баланс. Если монет достаточно - покупаем...
            if (M0odiEnderChest.getEconomy().getBalance(player) >
                    PriceRowsStorage.getCountRow(clickedRow)) {

                //... Закрываем Эндер Сундук у игрока...
                player.closeInventory();

                //... Снимаем деньги со счёта игрока...
                M0odiEnderChest.getEconomy().withdrawPlayer(player,
                        PriceRowsStorage.getCountRow(clickedRow));

                //... Обновляем запись о Эндер Сундуке...
                customEnderChest.setRows(customEnderChest.getRows() + 1);
                EnderChestProvider.updateCustomEnderChest(customEnderChest);

                //... Спавним частицы, проигрываем звук...
                ParticleUtils.spawnBuyRowParticles(player);
                SoundUtils.playBuyRowSound(player);

            }

            //... У игрока не хватает денег. Отменяем...
            else {
                player.sendMessage(MessageStorage.NOT_ENOUGH_MONEY.getMessage());
                return;
            }

            return;
        }

        //... Если клик проходит по области, которая не куплена игроком
        // отменяем событие и предлагаем купить данную строку
        // (при этом клик не является подтверждением покупки)...
        if (clickedSlot > (customEnderChest.getRows() * 9) - 1) {

            //... Отмена события...
            event.setCancelled(true);

            //... Берем начальный айтем (чтобы вернуть его после истечения времени)
            ItemStack startItem = inventory.getItem(clickedSlot);

            //... Заменяем айтем (для подтверждения)
            inventory.setItem(clickedSlot, GUIItemsStorage.CONFIRM_ITEM.getItem().convertToItemStack());

            //... Возвращаем замененный айтем через некоторое время
            // на место (время подтверждения истекло)...
            new BukkitRunnable() {
                @Override
                public void run() {
                    inventory.setItem(clickedSlot, startItem);
                }
            }.runTaskLater(M0odiEnderChest.getInstance(), 60);

        }

    }



    //... Если клик осуществлен по кастомному Эндер Сундуку вернёт true...
    private boolean checkClickInventory(Player player, Inventory inventory) {

        //... Если игрока нет в мапе или клик совершен не по
        // кастомному Эндер Сундуку - возвращаем false...
        if (!HOLDERS.containsKey(player)) return false;
        if (!HOLDERS.get(player).equals(inventory)) return false;

        //... Клик по кастомному Эндер Сундуку, возвращаем true...
        return true;

    }

    //... Вернет false, если у игрока нет прав на покупку данной строки.
    // Так же выведет сообщение об этом...
    private boolean checkPermsForBuyRow(int row, Player player) {

        //... Если у игрока нет прав на покупку четвертой строчки...
        if (row == 4 && !player.hasPermission("m0odi-chest.row-4")) {
            player.sendMessage(MessageStorage.NOT_PERM_ROW_4.getMessage());
            return false;
        }

        //... Если у игрока нет прав на покупку пятой строчки...
        if (row == 5 && !player.hasPermission("m0odi-chest.row-5")) {
            player.sendMessage(MessageStorage.NOT_PERM_ROW_5.getMessage());
            return false;
        }

        //... Если у игрока нет прав на покупку шестой строчки...
        if (row == 6 && !player.hasPermission("m0odi-chest.row-6")) {
            player.sendMessage(MessageStorage.NOT_PERM_ROW_6.getMessage());
            return false;
        }

        return true;
    }

    // Вернет false, если предыдущая строка не куплена...
    private boolean checkPreviousRows(int row, M0odiCustomEnderChest customEnderChest) {
        if ((customEnderChest.getRows() + 1) < row) return false;
        return true;
    }

}
