package me.zhanshi123.vipsystem.command;

import me.zhanshi123.vipsystem.command.sub.MeCommand;
import org.bukkit.Bukkit;

import java.util.HashSet;
import java.util.Set;

public class CommandHandler {

    private Set<SubCommand> commands = new HashSet<>();

    public CommandHandler(String name) {
        commands.add(new MeCommand());
        Bukkit.getPluginCommand(name).setExecutor(new CommandsExecutor());
    }

    public SubCommand getSubCommand(String cmd) {
        for (SubCommand command : commands) {
            if (command.getName().equalsIgnoreCase(cmd)) {
                return command;
            }
        }
        return null;
    }

    public Set<SubCommand> getCommands() {
        return commands;
    }
}
