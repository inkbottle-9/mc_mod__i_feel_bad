package org.x4.mc_mod__i_feel_bad.config;

import com.mojang.logging.LogUtils;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.x4.mc_mod__i_feel_bad.data_structure.IFBEntityAttributeModificationRecord;
import org.x4.mc_mod__i_feel_bad.data_structure.IFBEntitySet;
import org.x4.mc_mod__i_feel_bad.data_structure.IFBIdPair;
import org.x4.mc_mod__i_feel_bad.data_structure.IFBStatusAssignmentRecord;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// 类 IFB 通用配置
// 每个平台在启动后应解析文件中的配置并同步到该对象中
public class IFBConfigCommon {
    // 日志对象
    public static final Logger LOGGER = LogUtils.getLogger();

    // 全局单例
    public static final IFBConfigCommon CONFIG = new IFBConfigCommon();

    // 类 总配置
    public static class IFeelBadConfig {
        public boolean flag__enable_i_feel_bad = true;

        public boolean flag__enable_logging = false;

        public boolean flag__enable_config_check = false;
    }

    public interface IIFeelBadConfigTemplate {
        public boolean flag__enable_i_feel_bad();

        public boolean flag__enable_logging();

        public boolean flag__enable_config_check();

        default public void sync(@NotNull IFBConfigCommon.IFeelBadConfig _config) {
            _config.flag__enable_i_feel_bad = flag__enable_i_feel_bad();
            _config.flag__enable_logging = flag__enable_logging();
            _config.flag__enable_config_check = flag__enable_config_check();
        }
    }

    public static class ModificationConfig {
        // 启用标记
        public boolean flag__enable_modification = true;
        // 系数 k
        public double coefficient__k = 1.0;
        // 系数 b
        public double coefficient__b = 0.0;
        // 最小值
        public double minimum = 1.0;
        // 最大值
        public double maximum = 20.0;
    }

    public interface IModificationConfigTemplate {
        // 启用标记
        public boolean flag__enable_modification();

        // 系数 k
        public double coefficient__k();

        // 系数 b
        public double coefficient__b();

        // 最小值
        public double minimum();

        // 最大值
        public double maximum();

        default public void sync(@NotNull IFBConfigCommon.ModificationConfig _config) {
            _config.flag__enable_modification = flag__enable_modification();
            _config.coefficient__k = coefficient__k();
            _config.coefficient__b = coefficient__b();
            _config.minimum = minimum();
            _config.maximum = maximum();
        }
    }

    public static class StatusEffectAssignmentConfig {
        public boolean flag__enable_status_effect_assignment = true;

        public final List<IFBStatusAssignmentRecord> list__status_effect_assignment_records
            = new ArrayList<>();

        public final List<String> list__status_effect_assignment_records__default
            = new ArrayList<>();
    }

    public interface IStatusEffectAssignmentConfigTemplate {
        public boolean flag__enable_status_effect_assignment();

        public List<? extends String> list__status_effect_assignment_records();

        default public void sync(@NotNull IFBConfigCommon.StatusEffectAssignmentConfig _config) {
            _config.flag__enable_status_effect_assignment = flag__enable_status_effect_assignment();

            _config.list__status_effect_assignment_records.clear();
            // 状态效果
            for (String str : list__status_effect_assignment_records()) {
                _config.list__status_effect_assignment_records.add(IFBStatusAssignmentRecord.from_string(str));
            }
        }
    }

    public static class LevelExpRequirementCoverageConfig {
        public boolean flag__enable_coverage = true;

        public int basic__exp_requirement = 20;

        public int increment__exp_req_per_level = 0;

        public int minimum__exp_req_for_each_level = 1;

        public int maximum__exp_req_for_each_level = 100;
    }

    public interface ILevelExpRequirementCoverageConfigTemplate {
        public boolean flag__enable_coverage();

        public int basic__exp_requirement();

        public int increment__exp_req_per_level();

        public int minimum__exp_req_for_each_level();

        public int maximum__exp_req_for_each_level();

        default public void sync(@NotNull IFBConfigCommon.LevelExpRequirementCoverageConfig _config) {
            _config.flag__enable_coverage = flag__enable_coverage();
            _config.basic__exp_requirement = basic__exp_requirement();
            _config.increment__exp_req_per_level = increment__exp_req_per_level();
            _config.minimum__exp_req_for_each_level = minimum__exp_req_for_each_level();
            _config.maximum__exp_req_for_each_level = maximum__exp_req_for_each_level();
        }
    }

    public static class EntityJoiningAttributesModificationConfig {
        public boolean flag__enable_entity_attributes_modification = false;

        public final Map<String, IFBEntitySet> map__entity_sets
            = new LinkedHashMap<>();

        public final List<String> list__entity_sets__default
            = new ArrayList<>();

        public final Map<String, IFBEntityAttributeModificationRecord> map__attribute_modification_records
            = new LinkedHashMap<>();

        public final List<String> list__attribute_modification_records__default
            = new ArrayList<>();

        public final List<IFBIdPair> list__id_mappings
            = new ArrayList<>();

        public final List<String> list__id_mappings__default = new ArrayList<>();
    }

    public interface IEntityJoiningAttributesModificationConfigTemplate {
        public boolean flag__enable_entity_attributes_modification();

        public List<? extends String> list__entity_sets();

        public List<? extends String> list__attribute_records();

        public List<? extends String> list__id_mappings();

        default public void sync(@NotNull IFBConfigCommon.EntityJoiningAttributesModificationConfig _config) {
            _config.flag__enable_entity_attributes_modification
                = flag__enable_entity_attributes_modification();

            // 清除容器
            _config.map__entity_sets.clear();
            _config.map__attribute_modification_records.clear();
            _config.list__id_mappings.clear();

            // 遍历解析

            // 实体集合
            for (String str : list__entity_sets()) {
                var item = IFBEntitySet.from_string(str);
                _config.map__entity_sets.put(item.id, item);
            }

            // 属性修改记录
            for (String str : list__attribute_records()) {
                var item = IFBEntityAttributeModificationRecord.from_string(str);
                _config.map__attribute_modification_records.put(item.id, item);
            }

            // 映射表
            for (String str : list__id_mappings()) {
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

    // ----------------------------------------------------------------------------------------------------------------
    // 下面是实际的配置对象, 配置对象本身不会重建, 同步时直接更新对象内的成员

    public final IFeelBadConfig config__i_feel_bad
        = new IFeelBadConfig();

    public final ModificationConfig config__hp_modification
        = new ModificationConfig();
    public final ModificationConfig config__fullness_modification
        = new ModificationConfig();
    public final ModificationConfig config__saturation_modification
        = new ModificationConfig();

    public final StatusEffectAssignmentConfig config__status_assignment
        = new StatusEffectAssignmentConfig();

    public final LevelExpRequirementCoverageConfig config__level_exp_req_coverage
        = new LevelExpRequirementCoverageConfig();

    public final EntityJoiningAttributesModificationConfig config__entity_attributes_modification
        = new EntityJoiningAttributesModificationConfig();


    static {
        // 初始化默认值, 这些值会成为当配置文件不存在而需要生成时的默认值
        CONFIG.config__hp_modification.flag__enable_modification = true;
        CONFIG.config__hp_modification.coefficient__k = 1.0;
        CONFIG.config__hp_modification.coefficient__b = -4.0;
        CONFIG.config__hp_modification.minimum = 2.0;
        CONFIG.config__hp_modification.maximum = 100.0;

        CONFIG.config__fullness_modification.flag__enable_modification = true;
        CONFIG.config__fullness_modification.coefficient__k = 0.5;
        CONFIG.config__fullness_modification.coefficient__b = 2.0;
        CONFIG.config__fullness_modification.minimum = 7.0;
        CONFIG.config__fullness_modification.maximum = 100.0;

        CONFIG.config__saturation_modification.flag__enable_modification = true;
        CONFIG.config__saturation_modification.coefficient__k = 0.0;
        CONFIG.config__saturation_modification.coefficient__b = 0.0;
        CONFIG.config__saturation_modification.minimum = 0.0;
        CONFIG.config__saturation_modification.maximum = 0.0;

        CONFIG.config__status_assignment.flag__enable_status_effect_assignment = true;
        CONFIG.config__status_assignment.list__status_effect_assignment_records.addAll(
            List.of(
//                new IFBStatusAssignmentRecord("minecraft:unluck", 6000),    // 霉运
                new IFBStatusAssignmentRecord("minecraft:infested", 6000),  // 感染
                new IFBStatusAssignmentRecord("minecraft:mining_fatigue", 3600), // 挖掘疲劳
                new IFBStatusAssignmentRecord("minecraft:slowness", 600),   // 缓慢
                new IFBStatusAssignmentRecord("minecraft:weakness", 500),   // 虚弱
                new IFBStatusAssignmentRecord("minecraft:darkness", 400)    // 黑暗
            )
        );
        for (var item : CONFIG.config__status_assignment.list__status_effect_assignment_records){
            CONFIG.config__status_assignment.list__status_effect_assignment_records__default.add(item.toString());
        }



        CONFIG.config__entity_attributes_modification.flag__enable_entity_attributes_modification = false;
        CONFIG.config__entity_attributes_modification.map__entity_sets.put(
            IFBEntitySet.default_value.id, IFBEntitySet.default_value
        );
        CONFIG.config__entity_attributes_modification.list__entity_sets__default
            .add(IFBEntitySet.default_value.toString());

        CONFIG.config__entity_attributes_modification.map__attribute_modification_records.put(
            IFBEntityAttributeModificationRecord.default_value.id, IFBEntityAttributeModificationRecord.default_value
        );
        CONFIG.config__entity_attributes_modification.list__attribute_modification_records__default
            .add(IFBEntityAttributeModificationRecord.default_value.toString());

        CONFIG.config__entity_attributes_modification.list__id_mappings.add(IFBIdPair.default_value);
        CONFIG.config__entity_attributes_modification.list__id_mappings__default
            .add(IFBIdPair.default_value.toString());

    }

}
