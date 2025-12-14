package net.ashley.sourcecubed;

import com.mojang.logging.LogUtils;
import net.ashley.sourcecubed.core.common.helper.ControlsScreenButtonHandler;
import net.ashley.sourcecubed.core.common.helper.FallDamageHandler;
import net.ashley.sourcecubed.core.common.helper.SourceFallDamageHelper;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(SourceCubed.ID)
public class SourceCubed {
    public static final String ID = "sourcecubed";
    public static final Logger LOGGER = LogUtils.getLogger();

    public SourceCubed(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.addListener(ControlsScreenButtonHandler::onScreenInit);
        NeoForge.EVENT_BUS.addListener(FallDamageHandler::onLivingFall);
        NeoForge.EVENT_BUS.addListener(SourceFallDamageHelper::onFall);
        NeoForge.EVENT_BUS.addListener(SourceFallDamageHelper::onPlayerTick);
        NeoForge.EVENT_BUS.register(this);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }
}