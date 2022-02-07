package net.minecraftid.plotranking.commands;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.plot.world.PlotAreaManager;
import net.minecraftid.plotranking.Config;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Contest {

    Config configManager = new Config();
    PlotAreaManager plotAreaManager;
    YamlConfiguration publicConfig,contestConfig;
    String senderName;
    File fileContest;

    public void init(){
        plotAreaManager = PlotSquared.get().getPlotAreaManager();
        publicConfig = configManager.getConfig("public");
        contestConfig = configManager.getConfig("contest");
        senderName = publicConfig.getString("senderName");
        fileContest = configManager.getFileContest();
    }

    public void enabledContest(CommandSender sender){
        contestConfig.set("Contest.status","Active");
        saveConfig();
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',senderName+"&fMengaktifkan Contest"));
    }

    public void disabledContest(CommandSender sender){
        contestConfig.set("Contest.status","Non Active");
        saveConfig();
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',senderName+"&fMenonaktifkan Contest"));
    }

    public void registerContestant(CommandSender sender){
        if(contestConfig.get("Contest.status").equals("Non Active")){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',senderName+"&fYah sayang sekali, contest pendaftaran contest udah ga dibuka"));
            return;
        }
        Player player = (Player) sender;
        String playerName = player.getName();
        List<String> listContestant = contestConfig.getStringList("Contest.contestant");
        if(listContestant.contains(playerName)){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',senderName+"&eKamu sudah terdaftar dalam contest"));
            return;
        }

        listContestant.add(playerName);
        contestConfig.set("Contest.contestant",listContestant);
        saveConfig();

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',senderName+"&aYey "+playerName+" berhasil terdaftar ke contest "+ contestConfig.getString("Contest.name")));
        player.performCommand("/warp "+contestConfig.getString("warp"));
    }

    public void unregisterContestant(CommandSender sender, String nickname){
        List<String> listContestant = contestConfig.getStringList("Contest.contestant");
        if(listContestant.contains(nickname)){
            listContestant.remove(nickname);
            contestConfig.set("Contest.contestant",listContestant);
            saveConfig();
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',senderName+"&aPlayer "+nickname+" ter unregister"));
            return;
        }
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',senderName+"&e Nickname Player gak ada"));
    }

    public void listContestant(CommandSender sender){
        List<String> listContestant = contestConfig.getStringList("Contest.contestant");
        String contestant = String.join("\n&f&l-&r ", listContestant);
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&',senderName+"&f"+contestConfig.getString("Contest.name")+" Contestant :&r\n- "+contestant));
    }

    private void saveConfig(){
        try {
            contestConfig.save(fileContest);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
