package net.minecraftid.plotranking;

import net.minecraftid.plotranking.commands.Contest;
import net.minecraftid.plotranking.commands.Ranking;
import net.minecraftid.plotranking.tabCompleter.tabContest;
import org.bukkit.ChatColor;
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
    Contest contest = new Contest();
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this,this);
        initConfiguration();
        System.out.print("[PlotRanking] Is enabled");
    }

    @Override
    public void onDisable() {
        System.out.print("[PlotRanking] Is disabled");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        String senderName = publicConfig.getString("senderName");
        switch(label) {
            case "pr":
            case "plotranking":
                if(args.length <= 0) return true;
                if(args[0].equalsIgnoreCase("info")) {
                    ranking.getRating(sender);
                    return true;
                }
                if(args[0].equalsIgnoreCase("reload")){
                    initConfiguration();
                    return true;
                }
                else{
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', senderName+"Commandnya gak ada :'("));
                    return true;
                }
            case "contest":
                if(args.length <= 0){
                    contest.sendToContest(sender);
                    return true;
                }
                if(args[0].equalsIgnoreCase("register")) {
                    if(sender.hasPermission("plotranking.contest.register")){
                        contest.registerContestant(sender);
                        return true;
                    };
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&',senderName+"&fRank minimum harus &2Advanced&f atau di atasnya, buat ikutan contest ini, yuk rankup dulu yuk!"));
                    return true;
                }
                if(args[0].equalsIgnoreCase("list")){
                    contest.listContestant(sender);
                    return true;
                }
                if(sender.hasPermission("plotranking.admin")){
                    if(args[0].equalsIgnoreCase("enable")) {
                        contest.enabledContest(sender);
                        return true;
                    }
                    if(args[0].equalsIgnoreCase("disable")) {
                        contest.disabledContest(sender);
                        return true;
                    }
                    if(args[0].equalsIgnoreCase("reload")){
                        initConfiguration();
                        return true;
                    }
                    if(args.length <= 1) return true;
                    if(args[0].equalsIgnoreCase("unregister")) {
                        contest.unregisterContestant(sender,args[1]);
                        return true;
                    }
                }
            default:
                return true;
        }
    }

    public void initConfiguration(){
        publicConfig = configManager.getConfig("public");
        ranking.init();
        contest.init();
        tabCompleter();
    }

    private void tabCompleter(){
        try {
            this.getCommand("contest").setTabCompleter( new tabContest());
        }catch (NullPointerException e){
            getLogger().info(e.getMessage());
        }
    }

}
