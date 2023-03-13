package M0odiEnderChest.M0odiEnderChest.Utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class ParticleUtils {

    public static void spawnOpenEnderChestParticles(Player player, Block block) {

        //... Получаем локацию блока и прибавляем к высоте
        // единицу для отображения частиц над блоком...
        Location location = block.getLocation().add(0, 1, 0);

        //... Создаем частицы...
        player.spawnParticle(Particle.CLOUD, location, 1000);

    }

    public static void spawnBuyRowParticles(Player player) {

        //... Создаем частицы...
        player.spawnParticle(Particle.FIREWORKS_SPARK, player.getLocation(), 1000);

    }

}
