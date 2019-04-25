package me.zhanshi123.vipsystem.command.sub;

import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.VipSystemAPI;
import me.zhanshi123.vipsystem.api.storage.VipStorage;
import me.zhanshi123.vipsystem.command.SubCommand;
import me.zhanshi123.vipsystem.command.type.PermissionCommand;
import me.zhanshi123.vipsystem.manager.MessageManager;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Set;

public class ChangeVipCommand extends SubCommand implements PermissionCommand {

    public ChangeVipCommand() {
        super("changevip", MessageManager.getString("Command.changevip.usage"), MessageManager.getString("Command.changevip.desc"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(MessageManager.getString("consoleForbid"));
            return true;
        }
        Player player = (Player) sender;
        Set<VipStorage> data = VipSystemAPI.getInstance().getVipStorageManager().getVipStorage(player);
        if (data.isEmpty()) {
            player.sendMessage(MessageManager.getString("Command.changevip.noVipStored"));
            return true;
        }
        data.forEach(entry -> {
            TextComponent component = new TextComponent();
            String text = MessageFormat.format(MessageManager.getString("Command.changevip.tellraw.plain"), entry.getVip());
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{
                    new TextComponent(MessageFormat.format(MessageManager.getString("Command.changevip.tellraw.storeTime"), getTimeString(entry.getActivate())) + "\n"),
                    new TextComponent(MessageFormat.format(MessageManager.getString("Command.changevip.tellraw.expireTime"), getTimeString(entry.getActivate() + entry.getLeft())) + "\n"),
                    new TextComponent(MessageManager.getString("Command.changevip.tellraw.click"))
            }));
            component.setText(text);
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vipsys claim " + entry.getId()));
            player.spigot().sendMessage(component);
        });
        return true;
    }

    private String getTimeString(long time) {
        Date date = new Date(time);
        return Main.getConfigManager().getDateFormat().format(date);
    }
}
