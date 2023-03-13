package M0odiEnderChest.M0odiEnderChest.Commands;

import M0odiEnderChest.M0odiEnderChest.M0odiEnderChest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractCommand implements CommandExecutor {

    protected AbstractCommand(String commandName) {
        PluginCommand command = M0odiEnderChest.getInstance().getCommand(commandName);
        if (command == null) return;
        command.setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (feasibilityCheck(sender, args)) executeCommand(sender, args);
        return true;
    }

    protected abstract void executeCommand(CommandSender sender, String[] args);

    protected abstract boolean feasibilityCheck(CommandSender sender, String[] args);

}
