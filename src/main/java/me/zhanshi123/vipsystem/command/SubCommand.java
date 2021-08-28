package me.zhanshi123.vipsystem.command;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.command.type.AdminCommand;
import me.zhanshi123.vipsystem.command.type.PermissionCommand;
import me.zhanshi123.vipsystem.manager.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.MessageFormat;

public abstract class SubCommand {
    private String name;
    private String usage, description = null;

    private int argLength = -1;

    public SubCommand(String name) {
        this.name = name;
    }

    public SubCommand(String name, String usage, String description) {
        this.name = name;
        this.usage = usage;
        this.description = description;
        argLength = usage.split(" ").length - 1;
    }

    public abstract boolean onCommand(CommandSender sender, Command cmd, String label, String[] args);

    public String getName() {
        return name;
    }

    public String getUsage() {
        return usage;
    }

    public String getDescription() {
        return description;
    }

    public int getArgLength() {
        return argLength;
    }

    public void sendHelp(CommandSender sender) {
        if (usage != null && description != null) {
            sender.sendMessage(MessageFormat.format(MessageManager.getString("Command.format"), usage, description));
        }
    }

    public boolean hasPermission(CommandSender player) {
        if (this instanceof PermissionCommand) {
            return Main.getPermission().has(player, "vipsys." + name);
        }
        if (this instanceof AdminCommand) {
            return player.isOp();
        }
        return true;
    }

    public boolean isNeedArg() {
        boolean result = true;
        if (argLength == -1) {
            result = false;
        }
        if (argLength == 1) {
            result = false;
        }
        return result;
    }
}
