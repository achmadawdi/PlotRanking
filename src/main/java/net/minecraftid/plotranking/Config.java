package net.minecraftid.plotranking;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
    public YamlConfiguration loadPublicConfig(Path path){
        File folder = path.toFile();
        File file = new File(folder,"config.yml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }

        if(!file.exists()){
            try (InputStream input = getClass().getResourceAsStream("/public/" + file.getName())) {
                if (input != null) {
                    Files.copy(input, file.toPath());
                } else {
                    file.createNewFile();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
                return null;
            }
        }

        return YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration loadMainConfig(Path path){
        File folder = path.toFile();
        File file = new File(folder,"/PlotRanking/config.yml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }

        if(!file.exists()){
            try (InputStream input = getClass().getResourceAsStream("/public/" + file.getName())) {
                if (input != null) {
                    Files.copy(input, file.toPath());
                } else {
                    file.createNewFile();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
                return null;
            }
        }

        return YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getConfig(String file){
        Path CommunityConfig = Paths.get("./plugins/MinecraftID");
        return loadConfig(CommunityConfig, file);
    }

    public YamlConfiguration loadConfig(Path path, String file){
        YamlConfiguration config = null;
        if(file.equals("public")){
            config = loadPublicConfig(path);
        }
        if(file.equals("main")){
            config = loadMainConfig(path);
        }
        return config;
    }
}
