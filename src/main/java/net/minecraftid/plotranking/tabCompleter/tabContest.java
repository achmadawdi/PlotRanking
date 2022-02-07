package net.minecraftid.plotranking.tabCompleter;

import net.minecraftid.plotranking.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;

import java.util.List;

public class tabContest implements TabCompleter {
    Config configManager = new Config();
    YamlConfiguration contestConfig = configManager.getConfig("contest");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length==1){
            List<String> theCommand = new ArrayList<>();
            theCommand.add("register");
            theCommand.add("list");
            if(sender.hasPermission("plotranking.admin")){
                theCommand.add("reload");
                theCommand.add("unregister");
            }
            return theCommand;
        }
        if(args[0]=="unregister" && args.length==2 && sender.hasPermission("plotranking.admin")){
            List<String> listContestant = contestConfig.getStringList("Contest.contestant");
            return listContestant;
        }
        return null;
    }
}
