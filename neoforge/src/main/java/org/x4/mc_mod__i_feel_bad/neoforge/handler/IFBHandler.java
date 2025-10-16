package org.x4.mc_mod__i_feel_bad.neoforge.handler;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.FinalizeSpawnEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;
import org.x4.mc_mod__i_feel_bad.data_structure.IFBPlayerDeathState;
import org.x4.mc_mod__i_feel_bad.utils.IFBUtils;

import static org.x4.mc_mod__i_feel_bad.neoforge.attachment.IFBPlayerDeathStateAttachment.player_death_state;

public class IFBHandler {

    // 回调函数 当玩家重生
    @SubscribeEvent
    public static void on_player_respawn(@NotNull PlayerEvent.PlayerRespawnEvent _event) {
        // 获取玩家实体
        Player player = _event.getEntity();

        if (IFBUtils.check_player_is_server_and_survival_and_not_client_side(player)) {
            // 获取模组附件并传入
            org.x4.mc_mod__i_feel_bad.handler.IFBHandler.process_player_respawn(
                player, player.getData(player_death_state.get())
            );
        }
    }

    // 回调函数 当玩家死亡
    @SubscribeEvent
    public static void on_player_death(@NotNull LivingDeathEvent _event) {
        // 获取玩家
        Player player = IFBUtils.get_player(_event.getEntity());
        // 检查
        if (player != null) {
            // 获取死亡数据
            var data__death = org.x4.mc_mod__i_feel_bad.handler.IFBHandler.get_player_death_data(player);
            // 存储数据
            set_player_data(player, data__death);
        }
    }

    // 设置玩家信息
    public static void set_player_data(@NotNull Player _player, IFBPlayerDeathState _data) {
        // 保存数据到附件
        _player.setData(
            player_death_state.get(),          // 附件类型
            _data                                 // 实际数据
        );
    }


    // 回调函数 当实体最终生成时 (通过生成检查后, 初始化前)
    @SubscribeEvent
    public static void on_mob_finalize(@NotNull FinalizeSpawnEvent _event) {
        org.x4.mc_mod__i_feel_bad.handler.IFBHandler.modify_entity_attributes(_event.getEntity());
    }
    
    // 回调函数 玩家登入
//    @SubscribeEvent
//    public static void on_player_log_in(PlayerEvent.PlayerLoggedInEvent event) {
//
//    }

    // 回调函数 玩家登出
//    @SubscribeEvent
//    public static void on_player_log_out(PlayerEvent.PlayerLoggedInEvent event) {
//
//    }

    // 回调函数 当实体加入关卡
//    @SubscribeEvent
//    public static void on_entity_join_level(@NotNull EntityJoinLevelEvent _event) {
//
//    }

}
