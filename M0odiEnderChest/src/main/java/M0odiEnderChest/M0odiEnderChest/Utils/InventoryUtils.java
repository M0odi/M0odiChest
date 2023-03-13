package M0odiEnderChest.M0odiEnderChest.Utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class InventoryUtils {

    public static int getRowBySlot(int slot) {

        //... Для первой строчки...
        if (slot >= 0 && slot <= 8) return 1;

        //... Для второй строчки...
        if (slot > 8 && slot <= 17) return 2;

        //... Для третьей строчки...
        if (slot > 17 && slot <= 26) return 3;

        //... Для четвёртой строчки...
        if (slot > 26 && slot <= 35) return 4;

        //... Для пятой строчки...
        if (slot > 35 && slot <= 44) return 5;

        //... Для шестой строчки...
        if (slot > 44) return 6;

        //... Никогда не произойдет...
        return 0;
    }

}
