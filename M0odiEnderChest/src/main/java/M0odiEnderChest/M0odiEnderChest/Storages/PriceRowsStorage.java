package M0odiEnderChest.M0odiEnderChest.Storages;

import M0odiEnderChest.M0odiEnderChest.M0odiEnderChest;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum PriceRowsStorage {

    //... Хранение стоимостей строчек...

    ROW_4 (4, M0odiEnderChest.getInstance().getConfig().getInt("prices.row-4")),
    ROW_5 (5, M0odiEnderChest.getInstance().getConfig().getInt("prices.row-5")),
    ROW_6 (6, M0odiEnderChest.getInstance().getConfig().getInt("prices.row-6"));

    private final int row;

    @Getter
    private final int count;

    public static int getCountRow(int row) {

        //... Перебираем все строчки, если найдена нужная - возвращаем ее цену...
        for (PriceRowsStorage value : PriceRowsStorage.values()) {
            if (value.row == row) return value.count;
        }

        //... Если строчка не найдена - возвращаем ноль...
        return 0;

    }

}
