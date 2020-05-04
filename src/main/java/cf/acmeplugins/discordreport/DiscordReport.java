package cf.acmeplugins.discordreport;

import com.mrpowergamerbr.temmiewebhook.DiscordEmbed;
import com.mrpowergamerbr.temmiewebhook.DiscordMessage;
import com.mrpowergamerbr.temmiewebhook.TemmieWebhook;
import com.mrpowergamerbr.temmiewebhook.embed.FooterEmbed;
import com.mrpowergamerbr.temmiewebhook.embed.ThumbnailEmbed;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class DiscordReport extends JavaPlugin {
    FileConfiguration config = this.getConfig();
    // Initiate Variables
    final String playerReportWebhook = config.getString("playerReportWebhook");
    final String bugReportWebhook = config.getString("bugReportWebhook");
    final int pluginID = 7406;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Metrics metrics = new Metrics(this, pluginID);
        System.out.println("[DiscordReport] Starting DiscordReport...");
        System.out.println("[DiscordReport] by AcmePlugins, konsyr11");
        System.out.println("[DiscordReport] Reading configuration...");
        createConfig();
        if(playerReportWebhook.equals("") | bugReportWebhook.equals("")) {
            System.out.println("[DiscordReport] You have not set WebHook URLs in config.yml");
            System.out.println("[DiscordReport] FATAL! The plugin is not functional and will shut down!!");
            Bukkit.getPluginManager().disablePlugin(this);
        }
        System.out.println("[DiscordReport] The plugin has started!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("[DiscordReport] The plugin is shutting down...");
    }

    public void createConfig(){
        config.addDefault("playerReportWebhook", "");
        config.addDefault("bugReportWebhook", "");
        config.options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        String message = String.join(" ", args);
        String player = sender.getName();
        if(command.getName().equals("player")){
            if(message.equals("")){
                sender.sendMessage(ChatColor.RED + "Please supply a message!");
            }else{
                TemmieWebhook playerwebhook = new TemmieWebhook(playerReportWebhook);
                DiscordEmbed playerembed = DiscordEmbed.builder()
                        .title("Player Report - " + player) // Report type and reporter
                        .description(message) // Report Description
                        .footer(FooterEmbed.builder() // Credit Footer
                                .text("DiscordReport - by konsyr11") // Credit myself
                                .build()) // Build the footer
                        .thumbnail(ThumbnailEmbed.builder() // Thumbnail
                                .url("https://cdn.discordapp.com/attachments/634094893752516609/684644756508508162/user-4.png") // Thumbnail URL
                                .height(128) // Specify thumbnail height
                                .build()) // Build the thumbnail
                        .build(); // Build the embed

                DiscordMessage playermessage = DiscordMessage.builder()
                        .username("DiscordReport") // Name the Webhook
                        .content("@here") // Mass ping online staff
                        .avatarUrl("https://cdn.discordapp.com/attachments/634094893752516609/684644829652844550/warning.png") // Avatar url
                        .embeds(Arrays.asList(playerembed)) // Insert the embed
                        .build(); // Build the message
                playerwebhook.sendMessage(playermessage);
                sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "DiscordReport" + ChatColor.WHITE + "] " + ChatColor.GOLD + "The player has been reported to the staff team.");
            }
        }
        if(command.getName().equals("bug")){
            if(message.equals("")){
                sender.sendMessage(ChatColor.RED + "Please supply a message!");
            }else{
                TemmieWebhook bugwebhook = new TemmieWebhook(bugReportWebhook);
                DiscordEmbed bugembed = DiscordEmbed.builder()
                        .title("Bug Report - " + player) // Report type and reporter
                        .description(message) // Report Description
                        .footer(FooterEmbed.builder() // Credit Footer
                                .text("DiscordReport - by konsyr11") // Credit myself
                                .build()) // Build the footer
                        .thumbnail(ThumbnailEmbed.builder() // Thumbnail
                                .url("https://cdn.discordapp.com/attachments/634094893752516609/684645315256778755/error.png") // Thumbnail URL
                                .height(128) // Specify thumbnail height
                                .build()) // Build the thumbnail
                        .build(); // Build the embed

                DiscordMessage playermessage = DiscordMessage.builder()
                        .username("DiscordReport") // Name the Webhook
                        .content("@here") // Mass ping online staff
                        .avatarUrl("https://cdn.discordapp.com/attachments/634094893752516609/684644829652844550/warning.png") // Avatar url
                        .embeds(Arrays.asList(bugembed)) // Insert the embed
                        .build(); // Build the message
                bugwebhook.sendMessage(playermessage);
                sender.sendMessage(ChatColor.WHITE + "[" + ChatColor.RED + "DiscordReport" + ChatColor.WHITE + "] " + ChatColor.GOLD + "The bug has been reported to the staff team.");
            }
        }
        return false;
    }
}
