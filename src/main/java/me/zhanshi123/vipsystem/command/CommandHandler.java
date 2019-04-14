package me.zhanshi123.vipsystem.command;

import me.zhanshi123.vipsystem.command.sub.GiveCommand;
import me.zhanshi123.vipsystem.command.sub.MeCommand;
import me.zhanshi123.vipsystem.command.sub.RemoveCommand;
import org.bukkit.Bukkit;

import java.util.Set;
import java.util.TreeSet;

public class CommandHandler {

    private Set<SubCommand> commands = new TreeSet<>();

    public CommandHandler(String name) {
        commands.add(new MeCommand());
        commands.add(new RemoveCommand());
        commands.add(new GiveCommand());
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
