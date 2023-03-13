package M0odiEnderChest.M0odiEnderChest.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data @AllArgsConstructor @NonNull @Builder
public class M0odiCustomEnderChest {

    private String owner; // Никнейм владельца Эндер Сундука.

    private int rows; // Количество строчек Эндер Сундука,
                      // которыми владеет игрок.

    private List<M0odiItem> items; // Вещи, лежащие в Эндер Сундуке.

}
