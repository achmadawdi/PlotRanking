package net.minecraftid.plotranking.commands;

import com.plotsquared.bukkit.util.BukkitUtil;
import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.Rating;
import com.plotsquared.core.plot.world.PlotAreaManager;
import net.minecraftid.plotranking.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

public class Ranking {

    Config configManager = new Config();
    PlotAreaManager plotAreaManager;
    YamlConfiguration publicConfig;

    public void  init(){
        plotAreaManager = PlotSquared.get().getPlotAreaManager();
        publicConfig = configManager.getConfig("public");
    }

    public Plot getPlot(Player player){
        PlotPlayer<?> pp = BukkitUtil.adapt(player);
        Plot currentPlot = pp.getCurrentPlot();
        return currentPlot;
    }

    public void getRating(CommandSender sender){
        String senderName = publicConfig.getString("senderName");
        Player player = (Player) sender;
        Plot cp = getPlot(player);
        if(cp != null){
            HashMap<UUID, Rating> raters = cp.getRatings();
            if(!raters.isEmpty()){
                List<String> allRater = new ArrayList<>();
                for (Map.Entry<UUID, Rating> entry : raters.entrySet()) {
                    String ratersName = Bukkit.getPlayer(entry.getKey()).getName();
                    int value = entry.getValue().getAggregate();
                    allRater.add(ratersName+" ("+value+"/10)");
                }
                sender.sendMessage("List Raters : \n"+String.join("\n", allRater));
                return;
            };
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',senderName+"&fBelum ada yang memberi rate"));
            return;
        };
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',senderName+"&fKamu sedang tidak dalam plot"));
    }

}
