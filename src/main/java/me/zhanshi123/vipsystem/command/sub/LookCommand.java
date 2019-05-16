package me.zhanshi123.vipsystem.command.sub;

import me.zhanshi123.vipsystem.command.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class LookCommand extends SubCommand {
    public LookCommand(String name, String usage, String description) {
        super(name, usage, description);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return false;
    }
}
