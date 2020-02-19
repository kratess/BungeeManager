package me.kratess.bungeemanager.premiumlock;

import me.kratess.bungeemanager.utils.CheckUser;
import me.kratess.bungeemanager.utils.FilesManager;
import me.kratess.bungeemanager.utils.PremiumUUID;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Command extends net.md_5.bungee.api.plugin.Command {

    private String no_cmd;
    private String not_premium;
    private String already_in_list;
    private String added_to_list;

    public Command() {
        super(FilesManager.Config.getString("premium_lock.command"));

        no_cmd = FilesManager.Messages.getString("no_cmd").replace("&", "§");
        not_premium = FilesManager.Messages.getString("not_premium").replace("&", "§");
        already_in_list = FilesManager.Messages.getString("already_in_list").replace("&", "§");
        added_to_list = FilesManager.Messages.getString("added_to_list").replace("&", "§");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            String UUID = PremiumUUID.getPremiumUUID(sender.getName());
            if (UUID != null) {
                if (!CheckUser.checkUser(p.getName(), null)) {
                    FilesManager.PremiumUsers.set("users." + p.getName(), "0");
                    FilesManager.savePremiumUsers();
                    FilesManager.reloadPremiumUsers();

                    // must reload chace for premium_users.yml
                    new CheckUser();
                    p.disconnect(fromString(added_to_list));
                } else {
                    p.sendMessage(fromString(already_in_list));
                }
            } else {
                sender.sendMessage(fromString(not_premium));
            }
        } else {
            sender.sendMessage(fromString(no_cmd));
        }
    }

    private BaseComponent[] fromString(String d) {
        return new TextComponent().fromLegacyText(d);
    }

    private String stringToUUID(String d) {
        return d.substring(0,8) + "-" + d.substring(8, 12) + "-" + d.substring(12, 16) + "-" + d.substring(16, 20) + "-" + d.substring(20);
    }

}
