package net.ashley.sourcecubed.core.common.helper;

import net.ashley.sourcecubed.Config;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;

@EventBusSubscriber
public class FallDamageHandler {
    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if(!(event.getEntity() instanceof Player))
            return;

        if(Config.increasedFallDistance != 0.0D) {
            event.setDistance(event.getDistance() - 1);
        }
    }
}