package net.ashley.sourcecubed.core.common.helper;

import net.ashley.sourcecubed.Config;
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
        if(!Config.velocityFallDamageEnabled) return;
        if (!(event.getEntity() instanceof ServerPlayer sp)) return;
        if (sp.level().isClientSide) return;
        
        Vec3 velocity = sp.getDeltaMovement();
        lastYSpeed.put(sp.getUUID(), velocity.y);
    }

    @SubscribeEvent
    public static void onFall(LivingFallEvent event) {
        if(!Config.velocityFallDamageEnabled) return;
        if (!(event.getEntity() instanceof Player sp)) return;
        event.setCanceled(true);

        double lastSpeedY = lastYSpeed.getOrDefault(sp.getUUID(), 0.0);

        double distanceThreshold = Config.velocityDistanceThreshold;
        double damageThreshold = Config.velocityDamageThreshold;
        if (-lastSpeedY <= distanceThreshold) return;

        double excessSpeed = -lastSpeedY - damageThreshold;
        sp.hurt(sp.damageSources().fall(), (float)(1 + Math.pow(excessSpeed, Config.velocityDamageExponent) * Config.velocityDamageMultiplier));
    }
}