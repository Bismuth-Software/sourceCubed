package net.ashley.sourcecubed.core.common.helper;


import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@EventBusSubscriber
public class SourceFallDamageHelper {
    private static final Map<UUID, Double> lastYSpeed = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        if (!(event.getEntity() instanceof ServerPlayer sp)) return;
        if (sp.level().isClientSide) return;
        
        Vec3 velocity = sp.getDeltaMovement();
        lastYSpeed.put(sp.getUUID(), velocity.y);
    }

    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        if (!(event.getEntity() instanceof Player sp)) return;
        event.setCanceled(true);

        double lastSpeedY = lastYSpeed.getOrDefault(sp.getUUID(), 0.0);

        double threshold = 0.75;
        double threshold2 = 0.65;
        if (-lastSpeedY <= threshold) return;

        double excessSpeed = -lastSpeedY - threshold2;
        sp.hurt(sp.damageSources().fall(), (float)(1 + Math.pow(excessSpeed, 3) * 75F));
    }
}