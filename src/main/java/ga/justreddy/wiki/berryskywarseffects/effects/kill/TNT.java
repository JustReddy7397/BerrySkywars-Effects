package ga.justreddy.wiki.berryskywarseffects.effects.kill;

import com.cryptomorin.xseries.XSound;
import com.twitter.g25kubi.skywars.entity.GamePlayer;
import com.twitter.g25kubi.skywars.entity.cosmetics.KillEffects;
import ga.justreddy.wiki.berryskywarseffects.BerrySkywarsEffects;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class TNT extends KillEffects {
    public TNT() {
        super("TNT",
                BerrySkywarsEffects.getEffects().getConfigManager().getFile("kill").getConfig().getInt("tnt.id"),
                BerrySkywarsEffects.getEffects().getConfigManager().getFile("kill").getConfig().getInt("tnt.cost"),
                "skywars.effects.kill.tnt");
    }

    @Override
    public void start(GamePlayer gamePlayer) {
        TNTPrimed tnt = gamePlayer.getLocation().getWorld().spawn(gamePlayer.getLocation().multiply(0.25), TNTPrimed.class);
        new BukkitRunnable(){
            @Override
            public void run() {
                tnt.getLocation().getWorld().playEffect(tnt.getLocation(), Effect.EXPLOSION_HUGE, "i am data");
                tnt.getLocation().getWorld().playSound(tnt.getLocation(), XSound.ENTITY_GENERIC_EXPLODE.parseSound(), 5, 5);
                tnt.remove();
            }
        }.runTaskLater(BerrySkywarsEffects.getEffects(), 20L);

    }



}
