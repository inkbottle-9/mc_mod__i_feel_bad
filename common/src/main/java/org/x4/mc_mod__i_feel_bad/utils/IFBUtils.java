package org.x4.mc_mod__i_feel_bad.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.x4.mc_mod__i_feel_bad.mixin.IIFBMixinMob;
import org.x4.mc_mod__i_feel_bad.mixin.IIFBMixinNearestAttackableTargetGoal;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IFBUtils {

    // 正则 用于匹配 ID 的正则
    public static final String regex__id_matching = "\\s*(\\w+)\\s*";

    // 预编译的状态机
    public static final Pattern pattern__id_matching = Pattern.compile(regex__id_matching);

    // 检查 ID 是否合法 (\w+) (字母数字下划线)
    public static boolean check_id(String _id) {
        return pattern__id_matching.matcher(_id).find();
    }

    // 匹配 ID 并返回
    @Nullable
    public static String match_id(@NotNull String _id) {
        Matcher matcher = pattern__id_matching.matcher(_id);
        if (matcher.find())
            return matcher.group(1);
        return null;
    }

    @Nullable
    public static EntityType<?> get_entity_type(@NotNull String _id) {
        var level = Minecraft.getInstance().level;
        if (level != null) {
            return get_entity_type(_id, level.registryAccess());
        }
        return null;
    }

    // 获取
    @Nullable
    public static EntityType<?> get_entity_type(@NotNull String _id, @NotNull RegistryAccess _access__registry) {
        ResourceLocation _location = ResourceLocation.parse(_id);
        return _access__registry.registryOrThrow(Registries.ENTITY_TYPE).get(_location);
    }


    @Nullable
    public static TagKey<EntityType<?>> get_entity_tag(@NotNull String _id, @NotNull Entity _entity) {
        return get_entity_tag(_id, _entity.registryAccess());
    }

    @Nullable
    public static TagKey<EntityType<?>> get_entity_tag(@NotNull String _id) {
        var level = Minecraft.getInstance().level;
        if (level != null) {
            return get_entity_tag(_id, level.registryAccess());
        }
        return null;
    }

    @Nullable
    public static TagKey<EntityType<?>> get_entity_tag(@NotNull String _id, @NotNull Level _level) {
        return get_entity_tag(_id, _level.registryAccess());
    }

    // 根据 ID 获取实体标签对象
    @Nullable
    public static TagKey<EntityType<?>> get_entity_tag(
        @NotNull String _id, @NotNull RegistryAccess _access__registry
    ) {
        ResourceLocation location = ResourceLocation.tryParse(_id);

        if (location == null)
            return null;

        TagKey<EntityType<?>> key = TagKey.create(Registries.ENTITY_TYPE, location);

        Registry<EntityType<?>> registry = _access__registry.registry(Registries.ENTITY_TYPE).orElseThrow();

        return registry.getTag(key).isPresent() ? key : null;
    }

    // 直接创建, 不在注册表中检查
    @Nullable
    public static TagKey<EntityType<?>> get_entity_tag_without_checking(@NotNull String _id) {
        ResourceLocation location = ResourceLocation.tryParse(_id);

        if (location == null)
            return null;

        return TagKey.create(Registries.ENTITY_TYPE, location);
    }


    // 检查实体是否具有标签
    public static boolean check_weather_entity_has_tag(@NotNull LivingEntity _entity, TagKey<EntityType<?>> _tag) {
        return _entity.getType().is(_tag);
    }


    // 使用实体的 FOLLOW_RANGE 信息更新
    public static void fix_follow_range_attribute_update_error(@NotNull Mob _mob) {
        // 获取距离
        double distance = _mob.getAttributeValue(Attributes.FOLLOW_RANGE);
        fix_follow_range_attribute_update_error(_mob, distance);
    }

    // 修复 follow_range 属性更新错误 (即设置后不生效)
    // MC-145656 (https://bugs.mojang.com/browse/MC-145656) 的解决方案
    // 从 MC 官网页面上看, 1.21.1 开始 (含) 这个 BUG 已经修复, 但经测试 1.21.1 仍然出错
    // 参考: https://github.com/Sindarin27/farsighted-mobs
    public static void fix_follow_range_attribute_update_error(@NotNull Mob _mob, double _distance) {
        for (
            WrappedGoal item : ((IIFBMixinMob) _mob).getTargetSelector().getAvailableGoals()
            // 该字段已经开放, 无需 mixin
//            WrappedGoal item : _mob.targetSelector.getAvailableGoals()
        ) {
            // 获取目标
            Goal goal = item.getGoal();
            // 更新索敌条件中的范围参数
            if (goal instanceof NearestAttackableTargetGoal<?> goal__nat) {
                ((IIFBMixinNearestAttackableTargetGoal) goal__nat)
                    .getTargetConditions()
                    .range(_distance);
            }
        }

    }

    public static void fix_speed_attribute_update_error(@NotNull Mob _mob, double _speed) {
        _mob.setSpeed((float) _speed);
    }

    // 从存活实体中获取玩家实体
    public static @Nullable Player get_player(@NotNull LivingEntity _entity) {
        // 过滤非玩家实体
        if (_entity.getType() != EntityType.PLAYER)
            return null;
        return (Player) _entity;
    }

    public static boolean check_player_is_server_and_survival_and_not_client_side(Player _player) {
        if (!(_player instanceof ServerPlayer player__server))
            return false;

        // 检查玩家游戏模式, 只有生存或冒险模式生效
        if (!player__server.gameMode.isSurvival())
            return false;

        if (_player.level().isClientSide())
            return false;

        return true;
    }
}
