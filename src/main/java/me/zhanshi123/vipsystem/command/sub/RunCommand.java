package me.zhanshi123.vipsystem.command.sub;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.command.SubCommand;
import me.zhanshi123.vipsystem.command.type.AdminCommand;
import me.zhanshi123.vipsystem.custom.CustomArg;
import me.zhanshi123.vipsystem.custom.CustomFunction;
import me.zhanshi123.vipsystem.manager.MessageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class RunCommand extends SubCommand implements AdminCommand {
    public RunCommand() {
        super("run");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length <= 2) {
            sender.sendMessage(MessageManager.getString("Command.run.usage"));
            return true;
        }
        String name = args[1];
        CustomFunction customFunction = Main.getCustomManager().getCustomFunction(name);
        if (customFunction == null) {
            sender.sendMessage(MessageManager.getString("Command.run.notFound"));
            return true;
        }
        List<CustomArg> argList = new ArrayList<>();
        String[] functionArg = customFunction.getArgs();
        if (functionArg.length != args.length - 2) {
            sender.sendMessage(MessageManager.getString("Command.run.argNotMatch"));
            return true;
        }
        for (int i = 2; i < args.length; i++) {
            argList.add(new CustomArg(functionArg[i - 2], args[i]));
        }
        
        return true;
    }
}
