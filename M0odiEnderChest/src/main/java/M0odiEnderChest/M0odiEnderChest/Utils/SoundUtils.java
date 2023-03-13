package M0odiEnderChest.M0odiEnderChest.Utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class SoundUtils {

    public static void playOpenEnderChestSound(Player player) {

        //... Проигрываем звук открытия Эндер Сундука...
        player.playSound(player.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1f, 1f);

    }

    public static void playBuyRowSound(Player player) {

        //... Проигрываем звук покупки строчки...
        player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 1f, 1f);

    }

}
