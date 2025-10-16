package org.x4.mc_mod__i_feel_bad.neoforge.config;

import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.x4.mc_mod__i_feel_bad.IFeelBadCommon;
import org.x4.mc_mod__i_feel_bad.config.IFBConfigCommon;
import org.x4.mc_mod__i_feel_bad.config.IIFBConfigTemplate;
import org.x4.mc_mod__i_feel_bad.data_structure.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

// 类 配置
// 模组全局配置
public class IFBConfigManager implements IIFBConfigTemplate {
    // 构建器
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // 全局单例, 外部需通过该单例访问配置
    public static final IFBConfigManager CONFIG = new IFBConfigManager();

    // 构建 SPEC 对象 (用于注册); 注意该构建应置于所有配置项定义之后 (若为 static, 应置于本类末尾或 CONFIG 单例之后)
    public static final ModConfigSpec SPEC = BUILDER.build();

    // @SubscribeEvent 手动注册无需注解
    public static void on_local_config_loaded(@NotNull ModConfigEvent.Loading _event) {
        init_config(_event.getConfig(), false);
    }

    public static void on_local_config_reloaded(@NotNull ModConfigEvent.Reloading _event) {
        init_config(_event.getConfig(), true);
    }

    private static void init_config(@NotNull ModConfig _config, boolean _flag__reload) {
        if (!_config.getModId().equals(IFeelBadCommon.MOD_ID))
            return;
        switch (_config.getType()) {
            case COMMON -> // 初始化配置项
                IFBConfigManager.CONFIG.init();
            case SERVER -> {
            }
        }
        // 输出日志
        if (IFBConfigCommon.CONFIG.config__i_feel_bad.flag__enable_logging) {
            if (_flag__reload)
                IFBConfigCommon.LOGGER.info("i_feel_bad configuration has been hot-reloaded");
            else
                IFBConfigCommon.LOGGER.info("i_feel_bad configuration has been loaded");
        }
    }

    // 重载配置
    public static void reload() {
//        Path path__file = FMLPaths.CONFIGDIR.get();
//        try {
//            // 该代码会在 ~/con fig/ab/c 下尝试读取配置文件, 无法读取时新建默认模板
//            ConfigTracker.INSTANCE.loadConfigs(ModConfig.Type.COMMON, Path.of("ab/c") );
//        } catch (Exception exception) {
//            LOGGER.info(exception.toString()); 
//        }

        if (IFBConfigCommon.CONFIG.config__i_feel_bad.flag__enable_logging) {
            IFBConfigCommon.LOGGER.info("reloaded");
        }
    }

    public static void on_add_game_reload_listeners(@NotNull AddReloadListenerEvent event) {
        // 注意: 这里注册后, 玩家执行 /reload 时会自动回调 on_reload()
        event.addListener(IFBConfigManager::on_game_reload);
    }

    // 游戏内执行 /reload 时的回调
    @SuppressWarnings("DataFlowIssue")
    private static @NotNull CompletableFuture<Void> on_game_reload(
        PreparableReloadListener.@NotNull PreparationBarrier _stage,
        ResourceManager _resource_manager, ProfilerFiller _preparations_profiler,
        ProfilerFiller _reload_profiler, Executor _background_executor, Executor _game_executor
    ) {
        // 执行重载
        return _stage.wait(null).thenRunAsync(IFBConfigManager::reload, _game_executor);
    }

    @Override
    public IFBConfigCommon.IIFeelBadConfigTemplate config__i_feel_bad() {
        return config__i_feel_bad;
    }

    @Override
    public IFBConfigCommon.IModificationConfigTemplate config__hp_modification() {
        return config__hp_modification;
    }

    @Override
    public IFBConfigCommon.IModificationConfigTemplate config__fullness_modification() {
        return config__fullness_modification;
    }

    @Override
    public IFBConfigCommon.IModificationConfigTemplate config__saturation_modification() {
        return config__saturation_modification;
    }

    @Override
    public IFBConfigCommon.IStatusEffectAssignmentConfigTemplate config__status_assignment() {
        return config__status_assignment;
    }

    @Override
    public IFBConfigCommon.ILevelExpRequirementCoverageConfigTemplate config__level_exp_req_coverage() {
        return config__level_exp_req_coverage;
    }

    @Override
    public IFBConfigCommon.IEntityJoiningAttributesModificationConfigTemplate config__entity_attributes_modification() {
        return config__entity_attributes_modification;
    }

    // 类 总配置
    public static class IFeelBadConfig
        implements IFBConfigCommon.IIFeelBadConfigTemplate {

        public final ModConfigSpec.BooleanValue flag__enable_i_feel_bad;

        public final ModConfigSpec.BooleanValue flag__enable_logging;

        public final ModConfigSpec.BooleanValue flag__enable_config_check;

        public IFeelBadConfig(@NotNull ModConfigSpec.Builder _builder) {
            _builder
                .comment(" common settings for module I Feel Bad")
                .translation("i_feel_bad.configuration.gui.i_feel_bad_config")
                .push("I_FEEL_BAD");

            flag__enable_i_feel_bad = _builder
                .comment(" enable i_feel_bad (this mod); false to disable all")
                .translation("i_feel_bad.configuration.gui.i_feel_bad_config.flag__enable_i_feel_bad")
                .define(
                    "flag__enable_i_feel_bad",
                    IFBConfigCommon.CONFIG.config__i_feel_bad.flag__enable_i_feel_bad
                );

            flag__enable_logging = _builder
                .comment(" generating logs during the operation of the module")
                .translation("i_feel_bad.configuration.gui.i_feel_bad_config.flag__enable_logging")
                .define(
                    "flag__enable_logging",
                    IFBConfigCommon.CONFIG.config__i_feel_bad.flag__enable_logging
                );

            flag__enable_config_check = _builder
                .comment(" check whether the configuration is valid")
                .translation("i_feel_bad.configuration.gui.i_feel_bad_config.flag__enable_config_check")
                .define(
                    "flag__enable_config_check",
                    IFBConfigCommon.CONFIG.config__i_feel_bad.flag__enable_config_check
                );

            _builder.pop();
        }

        @Override
        public boolean flag__enable_i_feel_bad() {
            return flag__enable_i_feel_bad.getAsBoolean();
        }

        @Override
        public boolean flag__enable_logging() {
            return flag__enable_logging.getAsBoolean();
        }

        @Override
        public boolean flag__enable_config_check() {
            return flag__enable_config_check.getAsBoolean();
        }

        // 有默认的实现不需要重写
//        public void sync(IFBConfigCommon.@NotNull IFeelBadConfig _config) {
//            _config.flag__enable_i_feel_bad = flag__enable_i_feel_bad.getAsBoolean();
//            _config.flag__enable_logging = flag__enable_logging.getAsBoolean();
//            _config.flag__enable_config_check = flag__enable_config_check.getAsBoolean();
//        }
    }

    // 类 参数修改配置
    public static class ModificationConfig
        implements IFBConfigCommon.IModificationConfigTemplate {
        // 全静态也可以, 将构造函数改为 static {...} 即可; 不过还是实例化方便点

        // 启用标记
        public final ModConfigSpec.BooleanValue flag__enable_modification;
        // 系数 k
        public final ModConfigSpec.DoubleValue coefficient__k;
        // 系数 b
        public final ModConfigSpec.DoubleValue coefficient__b;
        // 最小值
        public final ModConfigSpec.DoubleValue minimum;
        // 最大值
        public final ModConfigSpec.DoubleValue maximum;

        // 构造函数
        public ModificationConfig(
            ModConfigSpec.@NotNull Builder _builder, String _name__value, String _full_name, @NotNull IFBLinearMappingDefaultValuePackage _defaults
        ) {
            String path__basic = IFBLinearMappingDefaultValuePackage.config_path__basic + _defaults.config_path__title;
            String path__basic_with_point = path__basic + ".";

            _builder.comment(String.format(" Configurations related to %s modification", _full_name))
                .translation(path__basic)
                .push(_name__value + "_modification");

            flag__enable_modification = _builder
                .comment(" false to disable modification behavior")
                .translation(path__basic_with_point + _defaults.config_path__flag__enabled)
                .define("flag__enable_modification", _defaults.flag__enabled);

            coefficient__k = _builder
                .comment(" the modification is achieved through function y=kx+b, this is k;")
                .comment(
                    String.format(
                        " here y represents the new value (%s at respawn), x the old value (%s at death);"
                        , _name__value, _name__value
                    )
                )
                .translation(path__basic_with_point + _defaults.config_path__coefficient__k)
                .defineInRange("coefficient__k", _defaults.default__k, -10000.0f, 10000.0f);

            coefficient__b = _builder
                .comment(" the modification is achieved through function y=kx+b, this is b;")
                .translation(path__basic_with_point + _defaults.config_path__coefficient__b)
                .defineInRange("coefficient__b", _defaults.default__b, -10000.0f, 10000.0f);

            minimum = _builder
                .comment(" the revised target value will not be lower than this value;")
                .comment(" this value should be lower than the maximum;")
                .translation(path__basic_with_point + _defaults.config_path__minimum)
                .defineInRange("minimum", _defaults.default__minimum, 0.0f, 10000.0f);
            maximum = _builder
                .comment(" the revised target value will not exceed this limit;")
                .translation(path__basic_with_point + _defaults.config_path__maximum)
                .defineInRange("maximum", _defaults.default__maximum, 0.0f, 10000.0f);

            // 与 push 配对使用
            _builder.pop();
        }

        @Override
        public boolean flag__enable_modification() {
            return flag__enable_modification.getAsBoolean();
        }

        @Override
        public double coefficient__k() {
            return coefficient__k.getAsDouble();
        }

        @Override
        public double coefficient__b() {
            return coefficient__b.getAsDouble();
        }

        @Override
        public double minimum() {
            return minimum.getAsDouble();
        }

        @Override
        public double maximum() {
            return maximum.getAsDouble();
        }

        // 有默认的实现不需要重写
//        public void sync(@NotNull IFBConfigCommon.ModificationConfig _config) {
//            _config.flag__enable_modification = flag__enable_modification.getAsBoolean();
//            _config.coefficient__k = coefficient__k.getAsDouble();
//            _config.coefficient__b = coefficient__b.getAsDouble();
//            _config.minimum = minimum.getAsDouble();
//            _config.maximum = maximum.getAsDouble();
//        }
    }

    // 类 状态添加配置
    public static class StatusEffectAssignmentConfig
        implements IFBConfigCommon.IStatusEffectAssignmentConfigTemplate {

        public final ModConfigSpec.BooleanValue flag__enable_status_effect_assignment;

        public final ModConfigSpec.ConfigValue<List<? extends String>> list__status_effect_assignment_records;

        public StatusEffectAssignmentConfig(ModConfigSpec.@NotNull Builder _builder) {
            _builder.comment(" Configurations related to status effect assignment")
                .translation("i_feel_bad.configuration.gui.status_effect_assignment_config")
                .push("status_effect_assignment");

            flag__enable_status_effect_assignment = _builder
                .comment(" enable status effect assignment after player respawn")
                .translation("i_feel_bad.configuration.gui.status_effect_assignment_config.flag__enable_status_effect_assignment")
                .define(
                    "flag__enable_status_effect_assignment",
                    IFBConfigCommon.CONFIG.config__status_assignment.flag__enable_status_effect_assignment
                );

            list__status_effect_assignment_records = _builder
                .comment(" a list of status effect record; fields:")
                .comment(" string id | int *duration (in tick) | int *level (amplifier, [0, 255)) | bool *ambient | bool *visible | bool *show_icon")
                .comment(" fields can be omitted (excluding ID) to use their default values;")
                .comment(" for example:")
                .comment("   [0] \"minecraft:slowness,3000,\"")
                .comment("   [1] \"minecraft:slowness,,,\"")
                .comment("   [2] \"minecraft:slowness,,2,\"")
                .comment(" common negative effect ids (also support positive effects):")
                .comment(" minecraft:slowness|mining_fatigue|instant_damage|blindness|hunger|weakness|darkness|...")
                .comment(" see details on wiki: https://minecraft.wiki/w/Effect")
                .translation("i_feel_bad.configuration.gui.status_effect_assignment_config.list__status_effect_assignment_records")
                .defineListAllowEmpty(
                    "list__status_effect_assignment_records",
//                    IFBStatusAssignmentRecord.str_list__default,
                    IFBConfigCommon.CONFIG.config__status_assignment.list__status_effect_assignment_records__default,
                    () -> "minecraft:xxx,6000,0,false,true,true",                   // 用于 GUI 新建条目时的默认样板
                    o -> {
                        // 检查是否为字符串
                        return o instanceof String;
                    }
                );

            _builder.pop();
        }

        @Override
        public boolean flag__enable_status_effect_assignment() {
            return flag__enable_status_effect_assignment.getAsBoolean();
        }

        @Override
        public List<? extends String> list__status_effect_assignment_records() {
            return list__status_effect_assignment_records.get();
        }

//        public void sync(@NotNull IFBConfigCommon.StatusEffectAssignmentConfig _config) {
//            _config.flag__enable_status_effect_assignment = flag__enable_status_effect_assignment.getAsBoolean();
//
//            _config.list__status_effect_assignment_records.clear();
//            // 状态效果
//            for (String str : list__status_effect_assignment_records.get()) {
//                _config.list__status_effect_assignment_records.add(IFBStatusAssignmentRecord.from_string(str));
//            }
//        }
    }

    // 类 等级经验需求覆盖配置
    public static class LevelExpRequirementCoverageConfig
        implements IFBConfigCommon.ILevelExpRequirementCoverageConfigTemplate {

        public final ModConfigSpec.BooleanValue flag__enable_coverage;

        public final ModConfigSpec.IntValue basic__exp_requirement;

        public final ModConfigSpec.IntValue increment__exp_req_per_level;

        public final ModConfigSpec.IntValue minimum__exp_req_for_each_level;

        public final ModConfigSpec.IntValue maximum__exp_req_for_each_level;

        // 构造函数
        public LevelExpRequirementCoverageConfig(ModConfigSpec.@NotNull Builder _builder) {
            _builder.comment(" Configurations related to modification of the experience mechanism")
                .translation("i_feel_bad.configuration.gui.level_exp_req_coverage_config")
                .push("experience_mechanism_modification");

            flag__enable_coverage = _builder
                .comment(" enable level exp requirement method coverage")
                .translation("i_feel_bad.configuration.gui.level_exp_req_coverage_config.flag__enable_coverage")
                .define(
                    "flag__enable_coverage",
                    IFBConfigCommon.CONFIG.config__level_exp_req_coverage.flag__enable_coverage
                );

            basic__exp_requirement = _builder
                .comment(" the necessary basic experience for upgrading")
                .translation("i_feel_bad.configuration.gui.level_exp_req_coverage_config.basic__exp_requirement")
                .defineInRange(
                    "basic__exp_requirement",
                    IFBConfigCommon.CONFIG.config__level_exp_req_coverage.basic__exp_requirement,
                    1, 2147483647
                );

            increment__exp_req_per_level = _builder
                .comment(" the increment of experience requirement per level")
                .comment(" the final required experience = basic + increment * level")
                .translation("i_feel_bad.configuration.gui.level_exp_req_coverage_config.increment__exp_req_per_level")
                .defineInRange("increment__exp_req_per_level",
                    IFBConfigCommon.CONFIG.config__level_exp_req_coverage.increment__exp_req_per_level,
                    -100000000, 100000000
                );

            minimum__exp_req_for_each_level = _builder
                .comment(" minimum experience requirement, used to clamp the computed result from below")
                .translation("i_feel_bad.configuration.gui.level_exp_req_coverage_config.minimum__exp_req_for_each_level")
                .defineInRange("minimum__exp_req_for_each_level",
                    IFBConfigCommon.CONFIG.config__level_exp_req_coverage.minimum__exp_req_for_each_level
                    , 1, 2147483647
                );

            maximum__exp_req_for_each_level = _builder
                .comment(" maximum experience requirement, used to clamp the computed result from above")
                .translation("i_feel_bad.configuration.gui.level_exp_req_coverage_config.maximum__exp_req_for_each_level")
                .defineInRange("maximum__exp_req_for_each_level",
                    IFBConfigCommon.CONFIG.config__level_exp_req_coverage.maximum__exp_req_for_each_level,
                    1, 2147483647
                );

            _builder.pop();
        }

        @Override
        public boolean flag__enable_coverage() {
            return flag__enable_coverage.getAsBoolean();
        }

        @Override
        public int basic__exp_requirement() {
            return basic__exp_requirement.getAsInt();
        }

        @Override
        public int increment__exp_req_per_level() {
            return increment__exp_req_per_level.getAsInt();
        }

        @Override
        public int minimum__exp_req_for_each_level() {
            return minimum__exp_req_for_each_level.getAsInt();
        }

        @Override
        public int maximum__exp_req_for_each_level() {
            return maximum__exp_req_for_each_level.getAsInt();
        }

//        public void sync(@NotNull IFBConfigCommon.LevelExpRequirementCoverageConfig _config) {
//            _config.flag__enable_coverage = flag__enable_coverage.getAsBoolean();
//            _config.basic__exp_requirement = basic__exp_requirement.getAsInt();
//            _config.increment__exp_req_per_level = increment__exp_req_per_level.getAsInt();
//            _config.minimum__exp_req_for_each_level = minimum__exp_req_for_each_level.getAsInt();
//            _config.maximum__exp_req_for_each_level = maximum__exp_req_for_each_level.getAsInt();
//        }
    }

    // 类 实体加入时属性修改配置
    public static class EntityJoiningAttributesModificationConfig
        implements IFBConfigCommon.IEntityJoiningAttributesModificationConfigTemplate {

        public final ModConfigSpec.BooleanValue flag__enable_entity_attributes_modification;

        public final ModConfigSpec.ConfigValue<List<? extends String>> list__entity_sets;

        public final ModConfigSpec.ConfigValue<List<? extends String>> list__attribute_records;

        public final ModConfigSpec.ConfigValue<List<? extends String>> list__id_mappings;

        public EntityJoiningAttributesModificationConfig(ModConfigSpec.@NotNull Builder _builder) {
            _builder.comment(" Configurations related to modification of entity attributes when spawning")
                .translation("i_feel_bad.configuration.gui.entity_joining_attributes_modification_config")
                .push("entity_attributes_modification");

            flag__enable_entity_attributes_modification = _builder
                .comment(" enable attribute modification when entity spawning")
                .translation(
                    "i_feel_bad.configuration.gui.entity_joining_attributes_modification_config" +
                        ".flag__enable_entity_attributes_modification"
                )
                .define(
                    "flag__enable_entity_attributes_modification",
                    IFBConfigCommon.CONFIG.config__entity_attributes_modification
                        .flag__enable_entity_attributes_modification
                );

            list__entity_sets = _builder
                .comment(" a list of entity set definition; fields:")
                .comment(" string id | bool flag__and_mode | double probability | ENTRY_0 | ENTRY_1 | ...")
                .comment(" ----------------------------------------------------------------------")
                .comment(" id             : identifier of this set for citation;")
                .comment(" flag__and_mode : enable logical \"AND\" mode between the entries, default \"OR\" mode;")
                .comment(" probability    : probability to execute current procedure;")
                .comment(" ----------------------------------------------------------------------")
                .comment(" the above ENTRY_0, ENTRY_1 ... has the following format:")
                .comment(" !@#minecraft:zombies")
                .comment(" ----------------------------------------------------------------------")
                .comment(" !                 : logical negation, omit to disable;")
                .comment(" @                 : namespace matching mode, omit to disable;")
                .comment(" #                 : entity tag matching mode, omit to disable;")
                .comment(" minecraft:zombies : entity ID/TAG string")
                .comment(" ----------------------------------------------------------------------")
                .comment(" it's possible to declare an indefinite number of entity set ENTRY;")
                .comment(" you can use any invalid ID and negate it to apply to all entities, for example: \"!i_feel_bad:none\";")
                .comment(" ----------------------------------------------------------------------")
                .comment(" see details on wiki:")
                .comment("   https://minecraft.wiki/w/Mob")
                .translation(
                    "i_feel_bad.configuration.gui.entity_joining_attributes_modification_config" +
                        ".list__entity_sets"
                )
                .defineListAllowEmpty(
                    "list__entity_sets",
//                    IFBEntitySet.str_list__default,
                    IFBConfigCommon.CONFIG.config__entity_attributes_modification.list__entity_sets__default,
                    IFBEntitySet.default_value::toString,
                    o -> {
                        // 检查是否为字符串
                        return o instanceof String;
                    }
                );

            list__attribute_records = _builder
                .comment(" a list of entity attribute record; fields:")
                .comment(" string id | bool flag__fix_attribute | double probability | ENTRY_0 | ENTRY_1 | ...")
                .comment(" ----------------------------------------------------------------------")
                .comment(" id                  : identifier of this attribute record for citation;")
                .comment(" flag__fix_attribute :")
                .comment("   [0] update health value synchronously when modifying max_health;")
                .comment("   [1] fix the update bug when modifying follow_range (https://bugs.mojang.com/browse/MC-145656);")
                .comment(" probability         : probability to execute current record;")
                .comment(" ----------------------------------------------------------------------")
                .comment(" the above ENTRY_0, ENTRY_1 ... has the following format:")
                .comment(" (?0.50) +minecraft:generic.max_health=1.00x+10.00 $10.00 [1.00~1000.00]")
                .comment(" ----------------------------------------------------------------------")
                .comment(" (?0.50)        : execution probability of the current entry;")
                .comment(" +              : force mode, omit to disable;")
                .comment(" minecraft:generic.max_health : id of target attribute;")
                .comment(" 1.00x+10.00    : calculated_value = 1.0 * old_value + 10.0;")
                .comment(" $10.00         : floating_value will be the random values within the range [calculated_value-10.0, calculated_value+10.0];")
                .comment(" [1.00~1000.00] : floating_value will be clamped within this range to get final_value;")
                .comment(" ----------------------------------------------------------------------")
                .comment(" it's possible to declare an indefinite number of attribute modification ENTRY;")
                .comment(" see details on wiki:")
                .comment("   https://minecraft.wiki/w/Attribute")
                .translation(
                    "i_feel_bad.configuration.gui.entity_joining_attributes_modification_config" +
                        ".list__attribute_records"
                )
                .defineListAllowEmpty(
                    "list__attribute_records",
//                    IFBEntityAttributeModificationRecord.str_list__default,
                    IFBConfigCommon.CONFIG.config__entity_attributes_modification
                        .list__attribute_modification_records__default,
                    IFBEntityAttributeModificationRecord.default_value::toString,
                    o -> {

                        // 检查是否为字符串
                        return o instanceof String;
                    }
                );

            list__id_mappings = _builder
                .comment(" a list of id mappings; fields:")
                .comment(" string id__entity_set | string id__attribute_record")
                .comment(" this list defines which attribute modifications should be applied to each entity set;")
                .comment(" this is a many-to-many mapping; invalid IDs will be ignored")
                .translation(
                    "i_feel_bad.configuration.gui.entity_joining_attributes_modification_config" +
                        ".list__id_mappings"
                )
                .defineListAllowEmpty(
                    "list__id_mappings",
//                    IFBIdPair.str_list__default,
                    IFBConfigCommon.CONFIG.config__entity_attributes_modification
                        .list__id_mappings__default,
                    IFBIdPair.default_value::toString,
                    o -> {
                        // 检查是否为字符串
                        return o instanceof String;
                    }
                );

            _builder.pop();

        }

        @Override
        public boolean flag__enable_entity_attributes_modification() {
            return flag__enable_entity_attributes_modification.getAsBoolean();
        }

        @Override
        public List<? extends String> list__entity_sets() {
            return list__entity_sets.get();
        }

        @Override
        public List<? extends String> list__attribute_records() {
            return list__attribute_records.get();
        }

        @Override
        public List<? extends String> list__id_mappings() {
            return list__id_mappings.get();
        }

        public void sync(@NotNull IFBConfigCommon.EntityJoiningAttributesModificationConfig _config) {
            _config.flag__enable_entity_attributes_modification
                = flag__enable_entity_attributes_modification.getAsBoolean();

            // 清除容器
            _config.map__entity_sets.clear();
            _config.map__attribute_modification_records.clear();
            _config.list__id_mappings.clear();

            // 遍历解析

            // 实体集合
            for (String str : list__entity_sets.get()) {
                var item = IFBEntitySet.from_string(str);
                _config.map__entity_sets.put(item.id, item);
            }

            // 属性修改记录
            for (String str : list__attribute_records.get()) {
                var item = IFBEntityAttributeModificationRecord.from_string(str);
                _config.map__attribute_modification_records.put(item.id, item);
            }

            // 映射表
            for (String str : list__id_mappings.get()) {
                var item = IFBIdPair.from_string(str);
                _config.list__id_mappings.add(item);
            }

            // 额外的数据结构初始化

            // 遍历 ID 映射表, 逐个添加映射关系
            for (var pair : _config.list__id_mappings) {
                // 查询
                var set = _config.map__entity_sets.get(pair.id__0);

                var mod = _config.map__attribute_modification_records.get(pair.id__1);

                // 检查集合对象与修改对象是否存在
                if (set != null && mod != null) {
                    // 加入列表
                    set.list__modifications.add(mod);
                }
            }
        }
    }

    // ---------------------------------------------------------------------------------------------

    // 模组总配置项
    public final IFeelBadConfig config__i_feel_bad
        = new IFeelBadConfig(BUILDER);

    // HP 生命值 配置项
    public final ModificationConfig config__hp_modification
        = new ModificationConfig(BUILDER, "hp", "health point",
        new IFBLinearMappingDefaultValuePackage(
            IFBConfigCommon.CONFIG.config__hp_modification
        ).set_config_path__title("hp_modification_config")
    );

    // FL 饱腹度 配置项
    public final ModificationConfig config__fullness_modification
        = new ModificationConfig(
        BUILDER, "food_level", "food level (fullness)",
        new IFBLinearMappingDefaultValuePackage(
            IFBConfigCommon.CONFIG.config__fullness_modification
        ).set_config_path__title("food_level_modification_config")
    );

    // SL 饱和度 配置项
    public final ModificationConfig config__saturation_modification
        = new ModificationConfig(
        BUILDER, "saturation", "saturation",
        new IFBLinearMappingDefaultValuePackage(
            IFBConfigCommon.CONFIG.config__saturation_modification
        ).set_config_path__title("saturation_modification_config")
    );

    // 状态赋值 配置项
    public final StatusEffectAssignmentConfig config__status_assignment
        = new StatusEffectAssignmentConfig(BUILDER);

    // 经验升级需求覆盖 配置项
    public final LevelExpRequirementCoverageConfig config__level_exp_req_coverage
        = new LevelExpRequirementCoverageConfig(BUILDER);

    // 实体加入时的属性修改 配置项
    public final EntityJoiningAttributesModificationConfig config__entity_attributes_modification
        = new EntityJoiningAttributesModificationConfig(BUILDER);


    // ---------------------------------------------------------------------------------------------

    // 私有构造函数 (禁止外部实例化)
    private IFBConfigManager() {

    }

    // 初始化 (需在配置读取完成后调用)
    public void init() {
        // 解析字符串
        sync_config_to_common();

        // 检查配置项
        if (this.config__i_feel_bad.flag__enable_config_check.getAsBoolean()) {
            String message__error = check_config();
            if (message__error != null)
                IFBConfigCommon.LOGGER.error(message__error);
        }
    }

    // 检查配置内容
    // 若返回 null, 表示配置正常, 否则以字符串形式提供错误信息
    private @Nullable String check_config() {
        // TODO:

        return null;
    }

//    private static boolean validateItemName(final Object obj) {
//        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
//    }

}
