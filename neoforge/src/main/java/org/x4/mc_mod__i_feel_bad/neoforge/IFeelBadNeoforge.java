package org.x4.mc_mod__i_feel_bad.neoforge;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import org.x4.mc_mod__i_feel_bad.IFeelBadCommon;
import org.x4.mc_mod__i_feel_bad.neoforge.attachment.IFBPlayerDeathStateAttachment;
import org.x4.mc_mod__i_feel_bad.neoforge.config.IFBConfigManager;
import org.x4.mc_mod__i_feel_bad.neoforge.handler.IFBHandler;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(IFeelBadCommon.MOD_ID)
public class IFeelBadNeoforge {
    // Define mod id in a common place for everything to reference
//    public static final String MOD_ID = "i_feel_bad";

    // Directly reference a slf4j logger
//    public static final Logger LOGGER = LogUtils.getLogger();

    // Create a Deferred Register to hold Blocks which will all be registered under the "i_feel_bad" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(IFeelBadCommon.MOD_ID);

    // Create a Deferred Register to hold Items which will all be registered under the "i_feel_bad" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(IFeelBadCommon.MOD_ID);

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "i_feel_bad" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS
        = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, IFeelBadCommon.MOD_ID);

    // Creates a new Block with the id "i_feel_bad:example_block", combining the namespace and path
//    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));

    // Creates a new BlockItem with the id "i_feel_bad:example_block", combining the namespace and path
//    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    // Creates a new food item with the id "i_feel_bad:example_id", nutrition 1 and saturation 2
//    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
//            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));

    // Creates a creative tab with the id "i_feel_bad:example_tab" for the example item, that is placed after the combat tab
//    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder()
//            .title(Component.translatable("itemGroup.i_feel_bad")) //The language key for the title of your CreativeModeTab
//            .withTabsBefore(CreativeModeTabs.COMBAT)
//            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
//            .displayItems((parameters, output) -> {
//                output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
//            }).build());

    // 事件介绍:
    // https://docs.neoforged.net/docs/1.21.1/concepts/events

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    // 模组主类的构造函数是模组加载时第一段执行的代码
    public IFeelBadNeoforge(IEventBus _event_bus__mod_local, @NotNull ModContainer _container__mod) {
        // 通用的初始化
        IFeelBadCommon.init();

        // 游戏事件总线
        final IEventBus event_bus__game = net.neoforged.neoforge.common.NeoForge.EVENT_BUS;

        // Register the Deferred Register to the mod event bus so blocks get registered
//        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
//        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
//        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (IFeelBad) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        // 实例注册方式, 适用于非静态成员 (需要 @SubscribeEvent 注解)
//        event_bus__game.register(this);

        // 注册附件
        IFBPlayerDeathStateAttachment.ATTACHMENT_TYPES.register(_event_bus__mod_local);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        // 注册模组配置
        _container__mod.registerConfig(ModConfig.Type.COMMON, IFBConfigManager.SPEC);

        // 将类 (中的回调) 注册到事件总线
        // 类注册方式, 适用于静态成员 (需要 @SubscribeEvent 注解)
        event_bus__game.register(IFBHandler.class);
        // 服务器开始事件
        event_bus__game.addListener(IFeelBadNeoforge::on_server_starting);
        // /reload 事件
        event_bus__game.addListener(IFBConfigManager::on_add_game_reload_listeners);

        // 注册到模组事件总线
        // 特定成员注册方式, 适用于静态成员; 手动注册函数无需注解
        _event_bus__mod_local.addListener(IFBConfigManager::on_local_config_loaded);
        _event_bus__mod_local.addListener(IFBConfigManager::on_local_config_reloaded);

        _event_bus__mod_local.addListener(IFeelBadNeoforge::common_setup);
        // Register the item to a creative tab
//        _event_bus__mod_local.addListener(IFeelBadNeoforge::addCreative);
    }


    private static void common_setup(FMLCommonSetupEvent event) {
        // 注意该阶段中不能初始化配置, 配置似乎没有读取

//        EntityType
    }


    // Add the example block item to the building blocks tab
//    private static void addCreative(BuildCreativeModeTabContentsEvent event) {
//        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
//            event.accept(EXAMPLE_BLOCK_ITEM);
//        }
//    }

    // You can use @SubscribeEvent and let the Event Bus discover methods to call
    // 服务器启动事件
    private static void on_server_starting(ServerStartingEvent event) {
        // Do something when the server starts
    }
}
