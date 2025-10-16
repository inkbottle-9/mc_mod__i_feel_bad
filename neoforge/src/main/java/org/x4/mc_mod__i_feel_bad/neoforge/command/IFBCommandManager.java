package org.x4.mc_mod__i_feel_bad.neoforge.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.jetbrains.annotations.NotNull;
import org.x4.mc_mod__i_feel_bad.neoforge.config.IFBConfigManager;

public class IFBCommandManager {

    // 注册命令
    public static void register(@NotNull CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("i_feel_bad")
                .requires(src -> src.hasPermission(2))   // 需要 OP 2 级
                .then(Commands.literal("reload")
                    .executes(ctx -> {
                        IFBConfigManager.reload();
                        ctx.getSource().sendSuccess(
                            () -> Component.literal("i_feel_bad config reloaded"), true);
                        return 1;
                    })
                )
        );
    }



    public static void on_register_commands(@NotNull RegisterCommandsEvent event) {
        IFBCommandManager.register(event.getDispatcher());
    }




}
