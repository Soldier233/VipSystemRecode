package me.zhanshi123.vipsystem.command.command;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.command.command.type.AdminCommand;
import me.zhanshi123.vipsystem.manager.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandsExecutor implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(commandSender);
            return false;
        } else {
            String arg = args[0];
            SubCommand subCommand = Main.getCommandHandler().getSubCommand(arg);
            if (subCommand == null) {
                subCommand = Main.getCommandHandler().getSubCommand("*");
            }
            if (subCommand == null) {
                return false;
            }
            if (subCommand instanceof AdminCommand) {
                if (commandSender.isOp()) {
                    if (subCommand.isNeedArg()) {
                        int argLength = subCommand.getArgLength();
                        if (argLength != args.length) {
                            subCommand.sendHelp(commandSender);
                            return false;
                        } else {
                            return subCommand.onCommand(commandSender, command, label, args);
                        }
                    } else {
                        return subCommand.onCommand(commandSender, command, label, args);
                    }
                } else {
                    commandSender.sendMessage(MessageManager.getString(""));
                    return false;
                }
            } else {
                if (subCommand.isNeedArg()) {
                    int argLength = subCommand.getArgLength();
                    if (argLength != args.length) {
                        subCommand.sendHelp(commandSender);
                        return false;
                    } else {
                        return subCommand.onCommand(commandSender, command, label, args);
                    }
                } else {
                    return subCommand.onCommand(commandSender, command, label, args);
                }
            }
        }
    }

    private void sendHelp(CommandSender sender) {
        Main.getCommandHandler().getCommands().stream()
                .filter(subCommand -> !(subCommand instanceof AdminCommand))
                .forEach(subCommand -> sender.sendMessage("§7" + subCommand.getUsage() + " §e" + subCommand.getDescription()));
        if (sender.isOp()) {
            Main.getCommandHandler().getCommands().stream()
                    .filter(subCommand -> subCommand instanceof AdminCommand)
                    .forEach(subCommand -> sender.sendMessage("§7" + subCommand.getUsage() + " §e" + subCommand.getDescription()));
        }
    }
}
