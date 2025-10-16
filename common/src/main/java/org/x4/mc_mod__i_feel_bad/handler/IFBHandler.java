package org.x4.mc_mod__i_feel_bad.handler;


import com.mojang.logging.LogUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.x4.mc_mod__i_feel_bad.config.IFBConfigCommon;
import org.x4.mc_mod__i_feel_bad.data_structure.IFBPlayerDeathState;
import org.x4.mc_mod__i_feel_bad.data_structure.IFBStatusAssignmentRecord;


// IFB 核心功能处理器
@SuppressWarnings("resource")
public class IFBHandler {
    // 日志对象
    public static final Logger LOGGER = LogUtils.getLogger();

    // 添加状态效果
    public static boolean add_effect_by_record(@NotNull IFBStatusAssignmentRecord _record, Player _player) {
        if (_record.id__status_effect == null || _record.id__status_effect.isBlank())
            return false;

        // 解析资源位置
        ResourceLocation id = ResourceLocation.tryParse(_record.id__status_effect);

        var server = _player.getServer();

        if (server == null)
            return false;

        Registry<MobEffect> registry =
            server.registryAccess().registry(Registries.MOB_EFFECT).orElseThrow();

        if (id == null)
            return false;

//        MobEffect effect = registry.get(id);
        // 需要 Holder<MobEffect> 而不是 MobEffect
        Holder<MobEffect> holder = registry.getHolder(id).orElse(null);

        if (holder == null)
            return false;

        _player.addEffect(new MobEffectInstance(
            holder,
            _record.duration,
            _record.amplifier,
            _record.flag__ambient,
            _record.flag__visible,
            _record.flag__show_icon
        ));

        return true;
    }

    // 修改实体属性
    public static void modify_entity_attributes(Entity _entity__event) {
        // 检查是否开启功能
        if (!IFBConfigCommon.CONFIG.config__i_feel_bad.flag__enable_i_feel_bad)
            return;

//        if (!_entity__event.isAddedToLevel())
//            return;

        if (
            IFBConfigCommon.CONFIG
                .config__entity_attributes_modification
                .flag__enable_entity_attributes_modification
        ) {
            // 检查是否为存活实体
            if (!(_entity__event instanceof LivingEntity entity__living))
                return;

            // 遍历记录列表, 如果正则可以匹配, 就执行修改
            for (var entry : IFBConfigCommon.CONFIG.config__entity_attributes_modification.map__entity_sets.entrySet()) {
                var entity_set = entry.getValue();
                double random = Math.random();
                if (entity_set.list__entries.isEmpty() || entity_set.list__modifications.isEmpty())
                    continue;
                // 先进行概率判定
                if (random > entity_set.probability)
                    continue;
                // 检查是否可以匹配
                if (entity_set.check_entity(entity__living)) {
                    String id__full = null;

                    if (IFBConfigCommon.CONFIG.config__i_feel_bad.flag__enable_logging) {
//                        ResourceLocation location__entity
//                            = BuiltInRegistries.ENTITY_TYPE.getKey(_entity__event.getType());
//                        RegistryAccess access = Minecraft.getInstance().level.registryAccess();
//                        Registry<EntityType<?>> reg = access.registryOrThrow(Registries.ENTITY_TYPE);
//
//                        EntityType.ZOMBIE;
//                        EntityTypeTags.ZOMBIES;

                        id__full = _entity__event.getType().toString();

                        // 获取完整 ID (形如 minecraft:xxx)
//                        id__full = location__entity.toString();

                        LOGGER.info(
                            "{} i_feel_bad modify entity; type = {}",
                            String.format("(?%.2f<%.2f)", random, entity_set.probability), id__full
                        );
                    }

                    // 获取属性映射表
                    AttributeMap map__attributes = entity__living.getAttributes();

                    // 遍历所有的属性记录
                    for (var attribute_modification : entity_set.list__modifications) {
                        for (var modification_entry : attribute_modification.list__attributes_modi_entries) {
                            modification_entry.modify_entity_attribute(
                                entity__living,
                                map__attributes,
                                attribute_modification.flag__fix_attribute,
                                id__full
                            );
                        }
                    }
                }
                // 失败就检查下一条记录
            }
        }
    }

    // 将死亡时的状态参数 (生命值等等) 经过计算后设置到重生后的玩家实体上, 同时赋予状态效果
    public static void process_player_respawn(Player _player, @Nullable IFBPlayerDeathState _state) {
        // 检查是否开启功能
        if (!IFBConfigCommon.CONFIG.config__i_feel_bad.flag__enable_i_feel_bad)
            return;

        // 如果需要打印日志, 就获取玩家名称
        String name__player
            = IFBConfigCommon.CONFIG.config__i_feel_bad.flag__enable_logging
            ? _player.getName().getString() : null;

        // 获取模组附件
        if (_state == null || _state.flag__is_default()) {
            if (IFBConfigCommon.CONFIG.config__i_feel_bad.flag__enable_logging) {
                LOGGER.info("player \"{}\" has no old data, keep all default", name__player);
            }
            // 退出不设置
            return;
        }

        float hp__target = -1.0f;
        int food_level__target = -1;
        float target__saturation = -1.0f;

        if (IFBConfigCommon.CONFIG.config__hp_modification.flag__enable_modification) {
            hp__target =
                (float) Math.clamp(
                    // y = kx + b; 注意这里使用最大生命值而不是死亡时的值 (死亡时的值理论上恒为 0)
                    _player.getMaxHealth()
                        * IFBConfigCommon.CONFIG.config__hp_modification.coefficient__k
                        + IFBConfigCommon.CONFIG.config__hp_modification.coefficient__b,
                    IFBConfigCommon.CONFIG.config__hp_modification.minimum,        // 最小值
                    IFBConfigCommon.CONFIG.config__hp_modification.maximum         // 最大值
                );
            // 额外检查防止可能的崩溃
            hp__target = Math.clamp(hp__target, 0.0f, _player.getMaxHealth());
            _player.setHealth(hp__target);
        }

        if (IFBConfigCommon.CONFIG.config__fullness_modification.flag__enable_modification) {
            food_level__target =
                // 限制
                Math.clamp(Math.round((float)
                        _state.value__fullness()
                        * IFBConfigCommon.CONFIG.config__fullness_modification.coefficient__k
                        + IFBConfigCommon.CONFIG.config__fullness_modification.coefficient__b
                    ),
                    Math.round((float) IFBConfigCommon.CONFIG.config__fullness_modification.minimum),
                    Math.round((float) IFBConfigCommon.CONFIG.config__fullness_modification.maximum)
                );
            // 检查: 不可为负值
            if (food_level__target < 0)
                food_level__target = 0;
            _player.getFoodData().setFoodLevel(food_level__target);

            // 饱和度修改 (当且仅当开启饱腹度修改)
            if (IFBConfigCommon.CONFIG.config__saturation_modification.flag__enable_modification) {
                target__saturation =
                    // 四舍五入
                    Math.round((float) Math.clamp(
                        _state.value__saturation()
                            * IFBConfigCommon.CONFIG.config__saturation_modification.coefficient__k
                            + IFBConfigCommon.CONFIG.config__saturation_modification.coefficient__b,
                        IFBConfigCommon.CONFIG.config__saturation_modification.minimum,
                        IFBConfigCommon.CONFIG.config__saturation_modification.maximum
                    ));
                // 额外检查: 饱和值不可超过饱腹值
                target__saturation = Math.clamp(target__saturation, 0.0f, (float) food_level__target);
                _player.getFoodData().setSaturation(target__saturation);
            }
        }

        if (IFBConfigCommon.CONFIG.config__status_assignment.flag__enable_status_effect_assignment) {
            // 遍历状态记录列表
            for (var record : IFBConfigCommon.CONFIG.config__status_assignment.list__status_effect_assignment_records) {
                // 添加状态
                var ignored = IFBHandler.add_effect_by_record(record, _player);
            }
        }

        // 日志输出
        if (IFBConfigCommon.CONFIG.config__i_feel_bad.flag__enable_logging) {
            LOGGER.info("player {} respawned with: hp={}, food={}, saturation={} (negative means disabled)",
                name__player,
                hp__target,
                food_level__target,
                target__saturation
            );
        }
    }

    // 处理玩家死亡
    public static @Nullable IFBPlayerDeathState get_player_death_data(@NotNull Player player) {
        // 检查是否开启功能
        if (!IFBConfigCommon.CONFIG.config__i_feel_bad.flag__enable_i_feel_bad)
            return null;

        // 客户端不处理. 这里无需手动过滤, 死亡回调仅在服务端触发
        if (player.level().isClientSide())
            return null;

        String name__player;

        var food_data = player.getFoodData();

        float hp__at_death = player.getHealth();
        float saturation__at_death = food_data.getSaturationLevel();
        int food_level__at_death = food_data.getFoodLevel();

        if (IFBConfigCommon.CONFIG.config__i_feel_bad.flag__enable_logging) {
            name__player = player.getName().getString();
            LOGGER.info(
                "player \"{}\" has died with:  hp={}, food={}, saturation={}; the data will be saved",
                name__player, hp__at_death, food_level__at_death, saturation__at_death
            );
        }

        // set_player_data 是一个平台特定函数, 在不同的平台上有不同的实现
        // 事实证明不能这样写, 因为 common 环境中类型拿不到
//        set_player_data(
//            player,
//            new IFBPlayerDeathState(
//                hp__at_death,
//                food_level__at_death,
//                saturation__at_death,
//                false
//            )
//        );

        return new IFBPlayerDeathState(
            hp__at_death,
            food_level__at_death,
            saturation__at_death,
            false
        );
    }

//    @ExpectPlatform
//    public static void set_player_data(Player _player, IFBPlayerDeathSnapshot _data) {
//
//    }


}
