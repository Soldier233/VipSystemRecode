package me.zhanshi123.vipsystem.api.vip;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zhanshi123.vipsystem.Main;
import me.zhanshi123.vipsystem.api.VipSystemAPI;
import me.zhanshi123.vipsystem.api.event.VipActivateEvent;
import me.zhanshi123.vipsystem.api.event.VipExpireEvent;
import me.zhanshi123.vipsystem.api.event.VipRenewEvent;
import me.zhanshi123.vipsystem.customcommand.CustomCommand;
import me.zhanshi123.vipsystem.task.CheckVipTask;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class VipManager {
    /**
     * Get player's VipData.<br/>
     * Return null if not exists.
     *
     * @param player Player
     * @return vip data
     */
    public VipData getVipData(Player player) {
        String name = VipSystemAPI.getInstance().getPlayerName(player);
        return Main.getCache().getVipData(name);
    }

    /**
     * Check the player has vip
     * @param player Player
     * @return whether player have vip
     */
    public boolean hasVip(Player player) {
        String name = VipSystemAPI.getInstance().getPlayerName(player);
        return Main.getCache().getVipData(name) != null;
    }

    public void addVipWithoutCommands(Player player, VipData vipData) {
        String name = VipSystemAPI.getInstance().getPlayerName(player);
        VipActivateEvent activateEvent = new VipActivateEvent(player, vipData);
        Bukkit.getPluginManager().callEvent(activateEvent);
        vipData = activateEvent.getVipData();
        Main.getCache().addVipData(name, vipData);
        String group = vipData.getVip();
        if (Main.getConfigManager().isDisableVault()) {
            if (Main.getConfigManager().isGlobal()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), MessageFormat.format(Main.getConfigManager().getAddGroupCommand(), player.getName(), group));
            } else {
                Main.getConfigManager().getWorlds().forEach(worldName -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), MessageFormat.format(Main.getConfigManager().getAddWorldGroupCommand(), player.getName(), group, worldName)));
            }
        } else {
            if (Main.getConfigManager().isGlobal()) {
                Main.getPermission().playerAddGroup(player, group);
            } else {
                Main.getConfigManager().getWorlds().forEach(worldName -> Main.getPermission().playerAddGroup(worldName, player, group));
            }
        }
        new CheckVipTask(player).runTask(Main.getInstance());
    }

    public void addVip(Player player, VipData vipData) {
        addVipWithoutCommands(player, vipData);
        CustomCommand customCommand = Main.getCustomCommandManager().get(vipData.getVip());
        if (customCommand == null) {
            return;
        }
        customCommand.getActivate().forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formatVipCustomCommand(cmd, player, vipData)));
    }

    private String formatVipCustomCommand(String command, Player player, VipData vipData) {
        command = command.replace("{0}", player.getName())
                .replace("{1}", vipData.getPrevious());
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            command = PlaceholderAPI.setPlaceholders((OfflinePlayer) player, command);
        }
        return command;
    }

    public void renewVip(Player player, long duration) {
        String name = VipSystemAPI.getInstance().getPlayerName(player);
        VipData vipData = getVipData(player);
        if (vipData == null) {
            return;
        }
        vipData.setDuration(vipData.getDuration() + duration);
        VipRenewEvent renewEvent = new VipRenewEvent(player, vipData);
        Bukkit.getPluginManager().callEvent(renewEvent);
        Main.getCache().addVipData(name, vipData);
        new CheckVipTask(player).runTask(Main.getInstance());
    }

    public VipData removeVipWithoutCommandsInternal(Player player, boolean natural) {
        String name = VipSystemAPI.getInstance().getPlayerName(player);
        VipData vipData = getVipData(player);
        if (vipData == null) {
            return null;
        }
        VipExpireEvent expireEvent = new VipExpireEvent(player, vipData, natural);
        Bukkit.getPluginManager().callEvent(expireEvent);
        Main.getCache().removePlayer(name);
        Main.getDataBase().deletePlayer(name);
        if (Main.getConfigManager().isDisableVault()) {
            if (Main.getConfigManager().isGlobal()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), MessageFormat.format(Main.getConfigManager().getRemoveGroupCommand(), player.getName(), vipData.getVip()));
                if (Main.getConfigManager().isPreviousGroup()) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), MessageFormat.format(Main.getConfigManager().getAddGroupCommand(), player.getName(), vipData.getPrevious()));
                } else if (Main.getConfigManager().getDefaultGroup() != null) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), MessageFormat.format(Main.getConfigManager().getAddGroupCommand(), player.getName(), Main.getConfigManager().getDefaultGroup()));
                }
            } else {
                Main.getConfigManager().getWorlds().forEach(worldName -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), MessageFormat.format(Main.getConfigManager().getRemoveWorldGroupCommand(), player.getName(), vipData.getVip(), worldName)));
                if (Main.getConfigManager().isPreviousGroup()) {
                    Main.getConfigManager().getWorlds().forEach(worldName -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), MessageFormat.format(Main.getConfigManager().getAddWorldGroupCommand(), player.getName(), vipData.getPrevious(), worldName)));
                } else if (Main.getConfigManager().getDefaultGroup() != null) {
                    Main.getConfigManager().getWorlds().forEach(worldName -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), MessageFormat.format(Main.getConfigManager().getAddWorldGroupCommand(), player.getName(), Main.getConfigManager().getDefaultGroup(), worldName)));
                }
            }
        } else {
            if (Main.getConfigManager().isGlobal()) {
                Main.getPermission().playerRemoveGroup(player, vipData.getVip());
                if (Main.getConfigManager().isPreviousGroup()) {
                    Main.getPermission().playerAddGroup(player, vipData.getPrevious());
                } else if (Main.getConfigManager().getDefaultGroup() != null) {
                    Main.getPermission().playerAddGroup(player, Main.getConfigManager().getDefaultGroup());
                }
            } else {
                Main.getConfigManager().getWorlds().forEach(worldName -> Main.getPermission().playerRemoveGroup(worldName, player, vipData.getVip()));
                if (Main.getConfigManager().isPreviousGroup()) {
                    Main.getConfigManager().getWorlds().forEach(worldName -> Main.getPermission().playerAddGroup(worldName, player, vipData.getPrevious()));
                } else if (Main.getConfigManager().getDefaultGroup() != null) {
                    Main.getConfigManager().getWorlds().forEach(worldName -> Main.getPermission().playerAddGroup(worldName, player, Main.getConfigManager().getDefaultGroup()));
                }
            }
        }
        return vipData;
    }

    public VipData removeVipWithoutCommands(Player player) {
        return removeVipWithoutCommandsInternal(player, true);
    }

    public void removeVip(Player player) {
        VipData vipData = removeVipWithoutCommands(player);
        CustomCommand customCommand = Main.getCustomCommandManager().get(vipData.getVip());
        if (customCommand == null) {
            return;
        }
        customCommand.getExpire().forEach(cmd -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), formatVipCustomCommand(cmd, player, vipData)));
    }

    public Set<VipData> getVipDatum() {
        Set<VipData> vipDataSet = Main.getDataBase().getVipDatum();
        Map<String, VipData> cache = Main.getCache().getCachedData();
        Set<VipData> result = new HashSet<>(vipDataSet);
        result.addAll(cache.values());
        vipDataSet.forEach(vipData -> {
            if (cache.containsKey(vipData.getPlayer())) {
                result.remove(vipData);
                result.add(cache.get(vipData.getPlayer()));
            }
        });
        return result;
    }
}
