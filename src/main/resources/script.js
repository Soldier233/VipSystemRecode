var doAnnouncements = function (player, group) {
    helper.sendMessage("&6Announcement &7>>> &a" + player + "&6 has bought &a" + group);
    helper.sendMessage(player, "&eCongratulations! You are now " + group);
};