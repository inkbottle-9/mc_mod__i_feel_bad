package org.x4.mc_mod__i_feel_bad.fabric.handler;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.x4.mc_mod__i_feel_bad.data_structure.IFBPlayerDeathState;
import org.x4.mc_mod__i_feel_bad.utils.IFBUtils;

import static org.x4.mc_mod__i_feel_bad.fabric.attachment.IFBPlayerDeathStateAttachment.player_death_state;

public class IFBHandler {


    // 回调函数 当玩家重生
    public static void on_player_respawn(ServerPlayer _old_player, ServerPlayer _new_player, boolean alive) {
        if (IFBUtils.check_player_is_server_and_survival_and_not_client_side(_old_player)) {
            // 获取模组附件并传入
            org.x4.mc_mod__i_feel_bad.handler.IFBHandler.process_player_respawn(
                _new_player, _old_player.getAttached(player_death_state)
            );
        }
    }

    // 回调函数 当玩家死亡
    public static void on_player_death(LivingEntity _entity, DamageSource source) {
        // 获取玩家
        Player player = IFBUtils.get_player(_entity);
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
        _player.setAttached(
            player_death_state,          // 附件类型
            _data                        // 实际数据
        );
    }


    // 回调函数 当实体最终生成时 (通过生成检查后, 初始化前)
    public static void on_mob_finalize(Entity _entity, ServerLevel _world) {
        org.x4.mc_mod__i_feel_bad.handler.IFBHandler.modify_entity_attributes(_entity);
    }


}
