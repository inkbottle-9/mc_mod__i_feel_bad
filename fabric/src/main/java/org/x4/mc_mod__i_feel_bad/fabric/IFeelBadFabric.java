package org.x4.mc_mod__i_feel_bad.fabric;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import org.x4.mc_mod__i_feel_bad.IFeelBadCommon;
import org.x4.mc_mod__i_feel_bad.fabric.config.IFBClothConfigManager;
import org.x4.mc_mod__i_feel_bad.fabric.handler.IFBHandler;


public final class IFeelBadFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        IFeelBadCommon.init();

        ServerLivingEntityEvents.AFTER_DEATH.register(IFBHandler::on_player_death);
        ServerPlayerEvents.AFTER_RESPAWN.register(IFBHandler::on_player_respawn);

        // ENTITY_LOAD 无法精准匹配实体生成事件, 加载存档时的生成也生效
        ServerEntityEvents.ENTITY_LOAD.register(IFBHandler::on_mob_finalize);

//        AutoConfig.register(IFBClothConfigManager.class, GsonConfigSerializer::new);

//        {
//            Gson gson = new GsonBuilder()
//                .setPrettyPrinting()
//                .disableHtmlEscaping() // ← 关键
//                .create();
//
//            Config config = IFBClothConfigManager.class.getAnnotation(Config.class);
//
//            GsonConfigSerializer<IFBClothConfigManager> serializer
//                = new GsonConfigSerializer<>(config, IFBClothConfigManager.class, gson);
//
//            AutoConfig.register(IFBClothConfigManager.class, (a, b) -> serializer);
//        }

        // 三种文件格式选一种
        AutoConfig.register(IFBClothConfigManager.class, Toml4jConfigSerializer::new);
//        AutoConfig.register(IFBClothConfigManager.class, GsonConfigSerializer::new);
//        AutoConfig.register(IFBClothConfigManager.class, JanksonConfigSerializer::new);


    }


}
