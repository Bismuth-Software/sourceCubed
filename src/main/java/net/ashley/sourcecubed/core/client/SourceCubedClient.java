package net.ashley.sourcecubed.core.client;

import net.ashley.sourcecubed.SourceCubed;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = SourceCubed.ID, dist = Dist.CLIENT) @EventBusSubscriber(modid = SourceCubed.ID, value = Dist.CLIENT)
public class SourceCubedClient {
    public SourceCubedClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {
        SourceCubed.LOGGER.info("HELLO FROM CLIENT SETUP");
        SourceCubed.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}
