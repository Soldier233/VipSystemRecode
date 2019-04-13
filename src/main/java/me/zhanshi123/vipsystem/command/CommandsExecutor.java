package me.zhanshi123.vipsystem.command;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.command.type.AdminCommand;
import me.zhanshi123.vipsystem.command.type.PermissionCommand;
import me.zhanshi123.vipsystem.manager.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.text.MessageFormat;

public class CommandsExecutor implements CommandExecutor {
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(commandSender);
            return true;
        } else {
            String arg = args[0];
            SubCommand subCommand = Main.getCommandHandler().getSubCommand(arg);
            if (subCommand == null) {
                subCommand = Main.getCommandHandler().getSubCommand("*");
            }
            if (subCommand == null) {
                return true;
            }
            if (subCommand instanceof AdminCommand) {
                if (commandSender.isOp()) {
                    if (subCommand.isNeedArg()) {
                        int argLength = subCommand.getArgLength();
                        if (argLength != args.length) {
                            subCommand.sendHelp(commandSender);
                            return true;
                        } else {
                            return subCommand.onCommand(commandSender, command, label, args);
                        }
                    } else {
                        return subCommand.onCommand(commandSender, command, label, args);
                    }
                } else {
                    commandSender.sendMessage(MessageManager.getString("permissionDeny"));
                    return true;
                }
            } else if (subCommand instanceof PermissionCommand) {
                if (Main.getPermission().has(commandSender, "vipsystem." + subCommand.getName())) {
                    if (subCommand.isNeedArg()) {
                        int argLength = subCommand.getArgLength();
                        if (argLength != args.length) {
                            subCommand.sendHelp(commandSender);
                            return true;
                        } else {
                            return subCommand.onCommand(commandSender, command, label, args);
                        }
                    } else {
                        return subCommand.onCommand(commandSender, command, label, args);
                    }
                } else {
                    commandSender.sendMessage(MessageManager.getString("permissionDeny"));
                    return true;
                }

            } else {
                if (subCommand.isNeedArg()) {
                    int argLength = subCommand.getArgLength();
                    if (argLength != args.length) {
                        subCommand.sendHelp(commandSender);
                        return true;
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
                .forEach(subCommand -> sender.sendMessage(MessageFormat.format(MessageManager.getString("Command.format"), subCommand.getUsage(), subCommand.getDescription())));
        if (sender.isOp()) {
            Main.getCommandHandler().getCommands().stream()
                    .filter(subCommand -> subCommand instanceof AdminCommand)
                    .forEach(subCommand -> sender.sendMessage(MessageFormat.format(MessageManager.getString("Command.format"), subCommand.getUsage(), subCommand.getDescription())));
        }
    }
}
