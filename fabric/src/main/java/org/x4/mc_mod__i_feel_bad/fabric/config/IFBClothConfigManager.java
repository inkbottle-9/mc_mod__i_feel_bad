package org.x4.mc_mod__i_feel_bad.fabric.config;


import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;
import org.jetbrains.annotations.NotNull;
import org.x4.mc_mod__i_feel_bad.IFeelBadCommon;
import org.x4.mc_mod__i_feel_bad.config.IFBConfigCommon;
import org.x4.mc_mod__i_feel_bad.config.IIFBConfigTemplate;

import java.util.ArrayList;
import java.util.List;

// 阅读源码可知, 配置注册的时候会检查是否具有该注解, 不存在时报错
@Config(name = IFeelBadCommon.MOD_ID)
public class IFBClothConfigManager implements ConfigData, IIFBConfigTemplate {

    @ConfigEntry.Category("I_FEEL_BAD")
    public IFeelBadConfig config__i_feel_bad = new IFeelBadConfig(
        IFBConfigCommon.CONFIG.config__i_feel_bad
    );

    @ConfigEntry.Category("hp_modification")
    public ModificationConfig config__hp_modification = new ModificationConfig(
        IFBConfigCommon.CONFIG.config__hp_modification
    );

    @ConfigEntry.Category("food_level_modification")
    public ModificationConfig config__fullness_modification = new ModificationConfig(
        IFBConfigCommon.CONFIG.config__fullness_modification
    );

    @ConfigEntry.Category("saturation_modification")
    public ModificationConfig config__saturation_modification = new ModificationConfig(
        IFBConfigCommon.CONFIG.config__saturation_modification
    );

    @ConfigEntry.Category("status_effect_assignment")
    public StatusEffectAssignmentConfig config__status_assignment = new StatusEffectAssignmentConfig(
        IFBConfigCommon.CONFIG.config__status_assignment
    );

    @ConfigEntry.Category("experience_mechanism_modification")
    public LevelExpRequirementCoverageConfig config__level_exp_req_coverage = new LevelExpRequirementCoverageConfig(
        IFBConfigCommon.CONFIG.config__level_exp_req_coverage
    );

    @ConfigEntry.Category("entity_attributes_modification")
    public EntityJoiningAttributesModificationConfig config__entity_attributes_modification
        = new EntityJoiningAttributesModificationConfig(
        IFBConfigCommon.CONFIG.config__entity_attributes_modification
    );


    public IFBClothConfigManager() {

    }


    public static class IFeelBadConfig
        implements IFBConfigCommon.IIFeelBadConfigTemplate {

        //        @ConfigEntry.Gui.Tooltip
//        @Comment("test comment")
        public boolean flag__enable_i_feel_bad;

        public boolean flag__enable_logging;

        public boolean flag__enable_config_check;

        public IFeelBadConfig() {
        }

        public IFeelBadConfig(@NotNull IFBConfigCommon.IFeelBadConfig _config) {
            flag__enable_i_feel_bad = _config.flag__enable_i_feel_bad;
            flag__enable_logging = _config.flag__enable_logging;
            flag__enable_config_check = _config.flag__enable_config_check;
        }

        @Override
        public boolean flag__enable_i_feel_bad() {
            return flag__enable_i_feel_bad;
        }

        @Override
        public boolean flag__enable_logging() {
            return flag__enable_logging;
        }

        @Override
        public boolean flag__enable_config_check() {
            return flag__enable_config_check;
        }
    }

    public static class ModificationConfig
        implements IFBConfigCommon.IModificationConfigTemplate {

        public boolean flag__enable_modification;
        public double coefficient__k;
        public double coefficient__b;
        public double minimum;
        public double maximum;

        public ModificationConfig() {
        }

        public ModificationConfig(
            boolean _enabled, double _k, double _b, double _min, double _max
        ) {
            this.flag__enable_modification = _enabled;
            this.coefficient__k = _k;
            this.coefficient__b = _b;
            this.minimum = _min;
            this.maximum = _max;
        }

        public ModificationConfig(
            @NotNull IFBConfigCommon.ModificationConfig _config
        ) {
            this.flag__enable_modification = _config.flag__enable_modification;
            this.coefficient__k = _config.coefficient__k;
            this.coefficient__b = _config.coefficient__b;
            this.minimum = _config.minimum;
            this.maximum = _config.maximum;
        }

        @Override
        public boolean flag__enable_modification() {
            return flag__enable_modification;
        }

        @Override
        public double coefficient__k() {
            return coefficient__k;
        }

        @Override
        public double coefficient__b() {
            return coefficient__b;
        }

        @Override
        public double minimum() {
            return minimum;
        }

        @Override
        public double maximum() {
            return maximum;
        }
    }

    public static class StatusEffectAssignmentConfig
        implements IFBConfigCommon.IStatusEffectAssignmentConfigTemplate {

        public boolean flag__enable_status_effect_assignment;
        public final List<String> list__status_effects = new ArrayList<>();

        public StatusEffectAssignmentConfig(
            @NotNull IFBConfigCommon.StatusEffectAssignmentConfig _config
        ) {
            flag__enable_status_effect_assignment = _config.flag__enable_status_effect_assignment;
            list__status_effects.addAll(_config.list__status_effect_assignment_records__default);
        }

        @Override
        public boolean flag__enable_status_effect_assignment() {
            return flag__enable_status_effect_assignment;
        }

        @Override
        public List<? extends String> list__status_effect_assignment_records() {
            return list__status_effects;
        }
    }

    public static class LevelExpRequirementCoverageConfig
        implements IFBConfigCommon.ILevelExpRequirementCoverageConfigTemplate {

        public boolean flag__enable_coverage;
        public int basic__exp_requirement;
        public int increment__exp_req_per_level;
        public int minimum__exp_req_for_each_level;
        public int maximum__exp_req_for_each_level;

        public LevelExpRequirementCoverageConfig(
            IFBConfigCommon.@NotNull LevelExpRequirementCoverageConfig _config
        ) {
            flag__enable_coverage = _config.flag__enable_coverage;
            basic__exp_requirement = _config.basic__exp_requirement;
            increment__exp_req_per_level = _config.increment__exp_req_per_level;
            minimum__exp_req_for_each_level = _config.minimum__exp_req_for_each_level;
            maximum__exp_req_for_each_level = _config.maximum__exp_req_for_each_level;
        }

        @Override
        public boolean flag__enable_coverage() {
            return flag__enable_coverage;
        }

        @Override
        public int basic__exp_requirement() {
            return basic__exp_requirement;
        }

        @Override
        public int increment__exp_req_per_level() {
            return increment__exp_req_per_level;
        }

        @Override
        public int minimum__exp_req_for_each_level() {
            return minimum__exp_req_for_each_level;
        }

        @Override
        public int maximum__exp_req_for_each_level() {
            return maximum__exp_req_for_each_level;
        }
    }

    public static class EntityJoiningAttributesModificationConfig
        implements IFBConfigCommon.IEntityJoiningAttributesModificationConfigTemplate {

        public boolean flag__enable_entity_attributes_modification;
        public final List<String> list__entity_sets = new ArrayList<>();
        public List<String> list__attribute_records = new ArrayList<>();
        public List<String> list__id_mappings = new ArrayList<>();

        public EntityJoiningAttributesModificationConfig(
            IFBConfigCommon.@NotNull EntityJoiningAttributesModificationConfig _config
        ) {
            flag__enable_entity_attributes_modification = _config.flag__enable_entity_attributes_modification;
            list__entity_sets.addAll(_config.list__entity_sets__default);
            list__attribute_records.addAll(_config.list__attribute_modification_records__default);
            list__id_mappings.addAll(_config.list__id_mappings__default);
        }

        @Override
        public boolean flag__enable_entity_attributes_modification() {
            return flag__enable_entity_attributes_modification;
        }

        @Override
        public List<? extends String> list__entity_sets() {
            return list__entity_sets;
        }

        @Override
        public List<? extends String> list__attribute_records() {
            return list__attribute_records;
        }

        @Override
        public List<? extends String> list__id_mappings() {
            return list__id_mappings;
        }
    }


    // 配置加载 / 重载后, 将值同步到 common 层
    // 官方文档
    // https://shedaniel.gitbook.io/cloth-config/auto-config/post-validation
    @Override
    public void validatePostLoad() throws ValidationException {
        // 运行在加载/重载之后，主线程

        // 同步到 common
        sync_config_to_common();
        if (config__i_feel_bad.flag__enable_config_check) {
            String message__error = check_config();
            if (message__error != null){
                IFBConfigCommon.LOGGER.error(message__error);
                // 抛出异常
                throw new ValidationException(message__error);
            }
        }
        if (config__i_feel_bad.flag__enable_logging) {
            IFBConfigCommon.LOGGER.info("[i_feel_bad] cloth_config loaded / reloaded.");
        }
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


    /* 简单的配置合法性检查，返回 null 代表通过 */
    private String check_config() {
        // TODO:
        return null;
    }


}