var helper = Java.type("me.zhanshi123.vipsystem.script.ScriptHelper");

var doAnnoucements = function (player, group) {
    helper.sendMessage("&6Announcement &7>>> &a" + player + "&6 has bought &a" + group);
    helper.sendMessage(player, "&eCongratulations! You are now " + group);
};