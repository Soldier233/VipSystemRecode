package me.zhanshi123.vipsystem.command.sub;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.command.SubCommand;
import me.zhanshi123.vipsystem.command.type.PermissionCommand;
import me.zhanshi123.vipsystem.manager.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends SubCommand implements PermissionCommand {
    public ReloadCommand() {
        super("reload", MessageManager.getString("Command.reload.usage"), MessageManager.getString("Command.reload.desc"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Main.getInstance().reload();
        sender.sendMessage(MessageManager.getString("Command.reload.success"));
        return true;
    }
}
