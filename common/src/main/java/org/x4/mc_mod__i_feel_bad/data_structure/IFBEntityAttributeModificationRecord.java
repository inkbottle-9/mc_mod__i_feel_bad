package org.x4.mc_mod__i_feel_bad.data_structure;

import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.NotNull;
import org.x4.mc_mod__i_feel_bad.config.IFBConfigCommon;
import org.x4.mc_mod__i_feel_bad.utils.IFBUtils;

import java.util.ArrayList;
import java.util.List;


// IFB 实体属性修改信息
public class IFBEntityAttributeModificationRecord {

    // ID, 使用正则表达式
    public String id = null;

    // 标记 修正属性修改问题
    // [0] 修改 max_health 时会同步更新实体当前生命值
    // [1] 修改 follow_range 时会修复不更新的错误
    public boolean flag__fix_attribute = true;

    // 概率 当前分类的实体的执行修改的总概率
    public double probability = 1.0;

    public List<IFBAttributeModificationEntry> list__attributes_modi_entries = new ArrayList<>();

    public static final IFBEntityAttributeModificationRecord default_value;

    // 默认值 (用于提供给配置器)
    public static final List<String> str_list__default = new ArrayList<>();

    static {
        default_value = new IFBEntityAttributeModificationRecord();

//        default_value.regex__id = "minecraft:zombie";
//        default_value.id = BuiltInRegistries.ENTITY_TYPE.getKey(EntityType.ZOMBIE).toString();
        default_value.id = "modification__0";

        default_value.list__attributes_modi_entries.add(new IFBAttributeModificationEntry(
            Attributes.MAX_HEALTH.getRegisteredName(), false,
            0.8, 1.0, 10.0, 10.0, 1.0, 1000.0
        ));
        default_value.list__attributes_modi_entries.add(new IFBAttributeModificationEntry(
            Attributes.ARMOR.getRegisteredName(), false,
            0.8, 1.0, 10.0, 10.0, 1.0, 1000.0
        ));

        str_list__default.add(default_value.toString());
    }

    // 默认构造函数
    public IFBEntityAttributeModificationRecord() {

    }

    public IFBEntityAttributeModificationRecord(String _regex__id, boolean _flag__check_living) {
        id = _regex__id;
        flag__fix_attribute = _flag__check_living;
    }

    // 添加属性 (链式调用)
    public IFBEntityAttributeModificationRecord add_attribute(
        String _id__attribute, boolean _flag__add_when_missing,
        double _probability, double _coefficient__k, double _coefficient__b,
        double _floating, double _minimum, double _maximum
    ) {
        list__attributes_modi_entries.add(new IFBAttributeModificationEntry(
            _id__attribute, _flag__add_when_missing,
            _probability, _coefficient__k, _coefficient__b, _floating, _minimum, _maximum
        ));
        return this;
    }

    //  添加属性对象 (链式调用)
    public IFBEntityAttributeModificationRecord add_attribute(IFBAttributeModificationEntry _attribute_data) {
        list__attributes_modi_entries.add(_attribute_data);
        return this;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(
            "%s,%b,%.2f", id, flag__fix_attribute, probability
        ));
        for (var item : list__attributes_modi_entries) {
            builder.append(',');
            builder.append(item);
        }
        return builder.toString();
    }


    // ID, 修复, 概率, 属性名0:值0, 属性名1:值1, ...
    public static @NotNull IFBEntityAttributeModificationRecord from_string(@NotNull String _string) {
        IFBEntityAttributeModificationRecord instance = new IFBEntityAttributeModificationRecord(); // 具有默认值

        // 去掉空白字符
//        String string__no_s = _string.replaceAll("\\s+", "");
        String[] array__string = _string.split(",");

        String string;

        int length__for_switch = array__string.length;

        if (length__for_switch > 3) {
            // 遍历解析所有属性
            for (int index = 3; index < array__string.length; index++) {
                string = array__string[index]; // 格式应该为

                // 添加属性
                instance.add_attribute(IFBAttributeModificationEntry.from_string(string));
            }
            length__for_switch = 3;
        }

        switch (length__for_switch) {
            case 3:
                string = array__string[2].trim();
                if (!string.isEmpty()) {
                    try {
                        instance.probability = Double.parseDouble(string);
                    } catch (Exception e) {
                        IFBConfigCommon.LOGGER.error(
                            "error parsing attribute modification probability, value = \"{}\", message = {}",
                            string, e.getMessage()
                        );
                    }
                }
            case 2:
                string = array__string[1].trim();
                if (!string.isEmpty()) {
                    try {
                        instance.flag__fix_attribute = Boolean.parseBoolean(string);
                    } catch (Exception e) {
                        IFBConfigCommon.LOGGER.error(
                            "error parsing attribute modification check living flag, value = \"{}\", message = {}",
                            string, e.getMessage()
                        );
                    }
                }
            case 1:
                instance.id = IFBUtils.match_id(array__string[0]);

//                IfeelBadConfigManager.LOGGER.warn("str__trimmed =" + str__trimmed);
//                if (!string.matches("")) {
//                    instance.id = string;
//                    try {
//                        // 预编译正则状态机
//                        instance.pattern__id = Pattern.compile(instance.id);
//                    } catch (PatternSyntaxException e) {
//                        IFBConfigManager.LOGGER.warn(
//                            "cannot compile regex, value = \"{}\", message={}",
//                            instance.id, e.toString()
//                        );
//                    }
//                }
        }

        return instance;
    }


}
