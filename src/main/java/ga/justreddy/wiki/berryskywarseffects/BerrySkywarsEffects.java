package ga.justreddy.wiki.berryskywarseffects;

import com.twitter.g25kubi.skywars.SkyWars;
import com.twitter.g25kubi.skywars.controllers.DancesController;
import com.twitter.g25kubi.skywars.controllers.KillEffectsController;
import com.twitter.g25kubi.skywars.entity.cosmetics.KillEffects;
import ga.justreddy.wiki.berryskywarseffects.effects.kill.PigSmash;
import ga.justreddy.wiki.berryskywarseffects.effects.kill.TNT;
import ga.justreddy.wiki.berryskywarseffects.effects.victory.ArmorStandDance;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import wiki.justreddy.ga.reddyutils.config.ConfigManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class BerrySkywarsEffects extends JavaPlugin {

    private static BerrySkywarsEffects effects;

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        effects = this;
        configManager = new ConfigManager();
        configManager.createFolder(this);
        configManager.registerFile(this, "kill", "killeffects");
        KillEffectsController.getController().getData().put("pigsmash", new PigSmash());
        KillEffectsController.getController().getData().put("TNT", new TNT());
        getCommand("test").setExecutor(this);
        File file = new File("plugins/" + getDescription().getName() + "/animations");
        if(file.exists()) file.mkdir();
        File animations = new File("plugins/" + getDescription().getName() + "/animations/armorstand.animc");
        if(!animations.exists()) {
            try {
                animations.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileWriter myWriter = new FileWriter("plugins/" + getDescription().getName() + "/animations/armorstand.animc");
            myWriter.write("interpolate\n" +
                    "length 24           \n" +
                    "frame 0             \n" +
                    "Armorstand_Position 0.0 0.0 0.0 0.0     \n" +
                    "Armorstand_Middle -0.5170020045 -7.784628729 3.811399554    \n" +
                    "Armorstand_Right_Leg 40 -0 0\n" +
                    "Armorstand_Left_Leg -40 0 0\n" +
                    "Armorstand_Left_Arm 40 -0 0\n" +
                    "Armorstand_Right_Arm -40 0 0\n" +
                    "Armorstand_Head 0 0 26.28187581\n" +
                    "frame 6\n" +
                    "Armorstand_Position 0.0 0.5 0.0 0.0\n" +
                    "Armorstand_Middle 0 -7.816849241e-011 1.952765158e-010\n" +
                    "Armorstand_Right_Leg 2.831220497e-006 -0 0\n" +
                    "Armorstand_Left_Leg 6.645742961e-006 0 0\n" +
                    "Armorstand_Left_Arm 2.831220497e-006 -0 0\n" +
                    "Armorstand_Right_Arm 6.645742961e-006 0 0\n" +
                    "Armorstand_Head 0 0 5.913184603\n" +
                    "frame 12\n" +
                    "Armorstand_Position 0.0 0.0 0.0 90.0\n" +
                    "Armorstand_Middle -0.5170020045 7.784628729 -3.811399554\n" +
                    "Armorstand_Right_Leg -40 -0 0\n" +
                    "Armorstand_Left_Leg 40 0 0\n" +
                    "Armorstand_Left_Arm -40 -0 0\n" +
                    "Armorstand_Right_Arm 40 0 0\n" +
                    "Armorstand_Head 0 0 -14.45551091\n" +
                    "frame 18\n" +
                    "Armorstand_Position 0.0 0.5 0.0 0.0\n" +
                    "Armorstand_Middle 0 -7.816849241e-011 1.952757206e-010\n" +
                    "Armorstand_Right_Leg 0 -0 0\n" +
                    "Armorstand_Left_Leg 0 0 0\n" +
                    "Armorstand_Left_Arm 0 -0 0\n" +
                    "Armorstand_Right_Arm 0 0 0\n" +
                    "Armorstand_Head 0 0 5.91318245\n" +
                    "frame 23\n" +
                    "Armorstand_Position 0.0 0.0 0.0 00.0\n" +
                    "Armorstand_Middle -0.5156556434 -7.764356245 3.801474025\n" +
                    "Armorstand_Right_Leg 39.89333309 -0 0\n" +
                    "Armorstand_Left_Leg -39.47916651 0 0\n" +
                    "Armorstand_Left_Arm 39.89333309 -0 0\n" +
                    "Armorstand_Right_Arm -39.47916651 0 0\n" +
                    "Armorstand_Head 0 0 26.22883227\n");
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DancesController.getController().getData().put("armorstand", new ArmorStandDance());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        final Firework fw = (Firework) ((Player)sender).getLocation().getWorld().spawnEntity( ((Player)sender).getLocation(), EntityType.FIREWORK);

        FireworkMeta fm = fw.getFireworkMeta();
        fm.addEffect(FireworkEffect.builder()
                .flicker(true)
                .trail(true)
                .with(FireworkEffect.Type.BURST)
                .withColor(Color.RED, Color.GREEN, Color.LIME)
                .build());
        fm.setPower(0);
        fw.setFireworkMeta(fm);

        new BukkitRunnable(){
            @Override
            public void run() {
                fw.detonate();
            }
        }.runTaskLater(this, 40L);
        return true;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public String colorize(String input){
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static BerrySkywarsEffects getEffects() {
        return effects;
    }
}
