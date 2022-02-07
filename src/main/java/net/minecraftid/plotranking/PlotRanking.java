package net.minecraftid.plotranking;

import net.minecraftid.plotranking.commands.Ranking;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class PlotRanking extends JavaPlugin implements Listener {

    Config configManager = new Config();
    YamlConfiguration publicConfig;
    Ranking ranking = new Ranking();
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this,this);
        publicConfig = configManager.getConfig("public");
        ranking.init();
        System.out.print("[PlotRanking] Is enabled");
    }

    @Override
    public void onDisable() {
        System.out.print("[PlotRanking] Is disabled");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        String senderName = publicConfig.getString("senderName");
        if(args.length < 0) return true;
        switch(label) {
            case "pr":
            case "plotranking":
                if(args[0].equalsIgnoreCase("info")) {
                    ranking.getRating(sender);
                    return true;
                }
                else{
                    sender.sendMessage(senderName+"Unknown Commands");
                }
            default:
                return true;
        }
    }


}
