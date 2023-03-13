package M0odiEnderChest.M0odiEnderChest.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.bukkit.inventory.ItemStack;

@Data @AllArgsConstructor @NonNull
public class M0odiItem {

    private int slot; // Слот, на котором будет находиться

    private ItemStack itemStack; // Айтем, который будет находиться

}
