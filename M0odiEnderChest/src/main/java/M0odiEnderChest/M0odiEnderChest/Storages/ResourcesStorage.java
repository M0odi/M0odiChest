package M0odiEnderChest.M0odiEnderChest.Storages;

import M0odiEnderChest.M0odiEnderChest.M0odiEnderChest;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ResourcesStorage {

    private final File file;

    @Getter
    private FileConfiguration config;

    @SneakyThrows
    public ResourcesStorage(String nameFile) {

        //... Создаем объект файла, в качестве директории служит папка плагина...
        file = new File(M0odiEnderChest.getInstance().getDataFolder(), nameFile);

        //... Если файла не существует - создаем его.
        // В случае неудачи при создании - отключаем плагин...
        if (!file.exists() && !file.createNewFile()) {
            Bukkit.getLogger().info("Не удалось создать файл ресурсов " + nameFile + ", отключение...");
            Bukkit.getPluginManager().disablePlugin(M0odiEnderChest.getInstance());
            return;
        }

        //... Загружаем YAML Конфигурацию...
        config = YamlConfiguration.loadConfiguration(file);

    }

    @SneakyThrows
    public void save() {
        //... Сохраняем файл конфигурации...
        config.save(file);
    }

}
