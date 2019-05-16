package me.zhanshi123.vipsystem.command.sub;

import me.zhanshi123.vipsystem.command.SubCommand;
import me.zhanshi123.vipsystem.command.type.PermissionCommand;
import me.zhanshi123.vipsystem.manager.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class LookCommand extends SubCommand implements PermissionCommand {
    public LookCommand() {
        super("look", MessageManager.getString("Command.look.usage"), MessageManager.getString("Command.look.desc"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return true;
    }
}
