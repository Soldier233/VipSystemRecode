package me.zhanshi123.vipsystem.command.sub;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.command.SubCommand;
import me.zhanshi123.vipsystem.command.type.AdminCommand;
import me.zhanshi123.vipsystem.manager.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CustomsCommand extends SubCommand implements AdminCommand {
    public CustomsCommand() {
        super("customs",MessageManager.getString("Command.customs.usage"), MessageManager.getString("Command.customs.desc"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage(MessageManager.getString("Command.customs.list"));
        Main.getCustomManager().getFunctionMap().forEach((name, function) -> {
            sender.sendMessage("§7- " + name + ":");
            sender.sendMessage("§7/vip run " + name + " " + function.getFormattedArgs());
            sender.sendMessage("§e" + function.getDescription());
        });
        return true;
    }
}
