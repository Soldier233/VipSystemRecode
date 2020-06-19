package me.zhanshi123.vipsystem.command;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.command.tab.TabCompletable;
import me.zhanshi123.vipsystem.command.type.AdminCommand;
import me.zhanshi123.vipsystem.command.type.PermissionCommand;
import me.zhanshi123.vipsystem.manager.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CommandsExecutor implements CommandExecutor, TabCompleter {
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
                    return handle(commandSender, command, label, args, subCommand);
                } else {
                    commandSender.sendMessage(MessageManager.getString("permissionDeny"));
                    return true;
                }
            } else if (subCommand instanceof PermissionCommand) {
                if (Main.getPermission().has(commandSender, "vipsystem." + subCommand.getName())) {
                    return handle(commandSender, command, label, args, subCommand);
                } else {
                    commandSender.sendMessage(MessageManager.getString("permissionDeny"));
                    return true;
                }

            } else {
                return handle(commandSender, command, label, args, subCommand);
            }
        }
    }

    private boolean handle(CommandSender commandSender, Command command, String label, String[] args, SubCommand subCommand) {
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

    private void sendHelp(CommandSender sender) {
        Main.getCommandHandler().getCommands().stream()
                .filter(subCommand -> !(subCommand instanceof AdminCommand))
                .filter(subCommand -> subCommand.getDescription() != null)
                .forEach(subCommand -> sender.sendMessage(MessageFormat.format(MessageManager.getString("Command.format"), subCommand.getUsage(), subCommand.getDescription())));
        if (sender.isOp()) {
            Main.getCommandHandler().getCommands().stream()
                    .filter(subCommand -> subCommand instanceof AdminCommand)
                    .filter(subCommand -> subCommand.getDescription() != null)
                    .forEach(subCommand -> sender.sendMessage(MessageFormat.format(MessageManager.getString("Command.format"), subCommand.getUsage(), subCommand.getDescription())));
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (args.length == 1) {
            return Main.getCommandHandler().getCommands().stream()
                    .map(SubCommand::getName)
                    .collect(Collectors.toList());
        } else if (args.length >= 2) {
            String commandName = args[0];
            SubCommand subCommand = Main.getCommandHandler().getSubCommand(commandName);
            if (commandName == null) {
                return new ArrayList<>();
            }
            if (!(subCommand instanceof TabCompletable)) {
                return new ArrayList<>();
            }
            TabCompletable tabCompletable = (TabCompletable) subCommand;
            if (args.length - 2 >= tabCompletable.getArguments().size()) {
                return new ArrayList<>();
            }
            return tabCompletable.getArguments().get(args.length - 2).run();
        }
        return new ArrayList<>();
    }
}
