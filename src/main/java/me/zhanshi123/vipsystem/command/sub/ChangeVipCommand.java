package me.zhanshi123.vipsystem.command.sub;

import me.zhanshi123.vipsystem.command.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ChangeVipCommand extends SubCommand{
    public ChangeVipCommand() {
        super("changevip", "", "");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        return false;
    }
}
