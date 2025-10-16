package org.x4.mc_mod__i_feel_bad.neoforge.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import org.jetbrains.annotations.NotNull;
import org.x4.mc_mod__i_feel_bad.IFeelBadCommon;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = IFeelBadCommon.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = IFeelBadCommon.MOD_ID, value = Dist.CLIENT)
public class IFeelBadClientNeoforge {
    public IFeelBadClientNeoforge(@NotNull ModContainer _container) {
        // Allows NeoForge to create a config screen for this mod's configs.
        // The config screen is accessed by going to the Mods screen > clicking on your mod > clicking on config.
        // Do not forget to add translations for your config options to the en_us.json file.
        _container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    private static void on_client_setup(FMLClientSetupEvent _event) {
        // Some client setup code
//        IFeelBad.LOGGER.info("HELLO FROM CLIENT SETUP");
//        IFeelBad.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    }
}
