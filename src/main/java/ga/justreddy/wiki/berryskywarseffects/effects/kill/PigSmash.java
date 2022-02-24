package ga.justreddy.wiki.berryskywarseffects.effects.kill;

import com.twitter.g25kubi.skywars.entity.GamePlayer;
import com.twitter.g25kubi.skywars.entity.cosmetics.KillEffects;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import ga.justreddy.wiki.berryskywarseffects.BerrySkywarsEffects;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Pig;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class PigSmash extends KillEffects {
    public PigSmash() {
        super("PigSmash", BerrySkywarsEffects.getEffects().getConfigManager().getFile("kill").getConfig().getInt("pigsmash.id"), BerrySkywarsEffects.getEffects().getConfigManager().getFile("kill").getConfig().getInt("pigsmash.cost"), "skywars.effects.kill.pigsmash");
    }

    @Override
    public void start(GamePlayer gamePlayer) {
        Pig pig = gamePlayer.getLocation().getWorld().spawn(gamePlayer.getLocation(), Pig.class);
        NBTEntity entity = new NBTEntity(pig);
        entity.setInteger("NoAI", 1);
        entity.setInteger("Invulnerable", 1);
        //pig.setSaddle(true);
        pig.setCustomName(BerrySkywarsEffects.getEffects().colorize("&aHoly Pig"));
        new BukkitRunnable() {
            @Override
            public void run() {
                if ((pig.getLocation().getY() - gamePlayer.getLocation().getY()) >= 3.5) {
                    Firework fw = (Firework) pig.getLocation().getWorld().spawnEntity(pig.getLocation(), EntityType.FIREWORK);

                    FireworkMeta fm = fw.getFireworkMeta();
                    fm.addEffect(FireworkEffect.builder()
                            .flicker(true)
                            .trail(true)
                            .with(FireworkEffect.Type.BURST)
                            .withColor(Color.YELLOW, Color.BLUE, Color.RED)
                            .build());
                    fm.setPower(0);
                    fw.setFireworkMeta(fm);

                    Bukkit.getScheduler().runTaskLater(BerrySkywarsEffects.getEffects(), fw::detonate, 3L);

                    pig.remove();
                    cancel();
                } else {
                    pig.teleport(pig.getLocation().add(0, 0.25, 0));
                }
            }
        }.runTaskTimer(BerrySkywarsEffects.getEffects(), 0, 1);
    }
}
