package net.ashley.sourcecubed.core.common.helper;

import net.ashley.sourcecubed.Config;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;

public class ControlsScreenButtonHandler {
    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init.Post event) {
        if (!(event.getScreen() instanceof ControlsScreen screen)) {
            return;
        }

        int width = 310;
        int height = 20;

        int x = screen.width / 2 - 155;
        int y = screen.height / 6 + 36;

        Component label = Component.literal("Squake Movement: " + (Config.isEnabled() ? "ON" : "OFF"));

        Button toggle = Button.builder(label, button -> {
            Config.setEnabled(!Config.isEnabled());
            button.setMessage(Component.literal("Squake Movement: " + (Config.isEnabled() ? "ON" : "OFF")));
        }).bounds(x, y, width, height).build();

        event.addListener(toggle);
    }
}
