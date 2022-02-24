package ga.justreddy.wiki.berryskywarseffects.effects.victory;

import com.cryptomorin.xseries.XMaterial;
import com.twitter.g25kubi.skywars.entity.GamePlayer;
import com.twitter.g25kubi.skywars.entity.cosmetics.VictoryDance;
import de.tr7zw.changeme.nbtapi.NBTEntity;
import ga.justreddy.wiki.berryskywarseffects.BerrySkywarsEffects;
import ga.justreddy.wiki.berryskywarseffects.animator.ArmorStandAnimator;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class ArmorStandDance extends VictoryDance {
    public ArmorStandDance() {
        super("ArmorStand", 65, 1, "skywars.effects.dance.armorstand");
    }

    ArmorStandAnimator armorStandAnimator;
    private BukkitRunnable runnable;
    private  ArmorStand armorStand;
    @Override
    public void start(GamePlayer gamePlayer) {
        armorStand = gamePlayer.getLocation().getWorld().spawn(gamePlayer.getLocation(), ArmorStand.class);
        armorStand.setArms(true);
        armorStand.setBasePlate(false);
        ItemStack helmet = XMaterial.PLAYER_HEAD.parseItem();
        SkullMeta skullMeta = (SkullMeta) helmet.getItemMeta();
        skullMeta.setOwner(gamePlayer.getName());
        helmet.setItemMeta(skullMeta);
        armorStand.setHelmet(helmet);
        armorStand.setChestplate(gamePlayer.getPlayer().getInventory().getChestplate());
        armorStand.setLeggings(gamePlayer.getPlayer().getInventory().getLeggings());
        armorStand.setBoots(gamePlayer.getPlayer().getInventory().getBoots());

        NBTEntity entity = new NBTEntity(armorStand);
        entity.setInteger("NoAI", 1);
        entity.setInteger("Invulnerable", 1);
        armorStandAnimator = new ArmorStandAnimator(new File("plugins/" + BerrySkywarsEffects.getEffects().getDescription().getName() + "/animations/armorstand.animc"), armorStand);
        runnable = (BukkitRunnable) new BukkitRunnable() {
            @Override
            public void run() {
                armorStandAnimator.update();
            }
        }.runTaskTimer(BerrySkywarsEffects.getEffects(), 0, 1);
    }

    @Override
    public void stop(GamePlayer gamePlayer) {
        armorStandAnimator.stop();
        runnable.cancel();
        armorStand.remove();
    }
}
