package org.x4.mc_mod__i_feel_bad.data_structure;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.x4.mc_mod__i_feel_bad.config.IFBConfigCommon;

import java.util.ArrayList;
import java.util.List;

// 类 IFB 实体集合
// 用于判断某个实体是否属于该集合
public class IFBEntitySet {

    public String id = null;

    public boolean flag__and_mode = false;

    public double probability = 1.0;

    public final List<IFBEntitySetEntry> list__entries = new ArrayList<>();

    // 应用于该实体集合的修改项目列表 (在解析后会遍历映射列表添加)
    public final List<IFBEntityAttributeModificationRecord> list__modifications
        = new ArrayList<>();

    public static final IFBEntitySet default_value;

    public static final List<String> str_list__default = new ArrayList<>();

    static {
        default_value = new IFBEntitySet();

        default_value.id = "entities__0";
        default_value.add_entry(IFBEntitySetEntry.default_value);

        str_list__default.add(default_value.toString());
    }

    // 检查实体是否匹配当前集合
    public boolean check_entity(@NotNull Entity _entity) {
        if (flag__and_mode) {
            // 与模式, 要求所有条目都成立才成立
            for (var entry : list__entries) {
                if (!entry.check_entity(_entity)) {
                    return false;
                }
            }
            return true;
        } else {
            // 或模式, 只要有条目成立就成立
            for (var entry : list__entries) {
                if (entry.check_entity(_entity)) {
                    return true;
                }
            }
            return false;
        }
    }

    public String toString() {
        StringBuilder builder = new StringBuilder(
            String.format("%s,%b,%.2f", id, flag__and_mode, probability)
        );

        for (var entry : list__entries) {
            builder.append(',');
            builder.append(entry.toString());
        }

        return builder.toString();
    }

    // 从字符串中解析
    public static @NotNull IFBEntitySet from_string(@NotNull String _string) {
        IFBEntitySet instance = new IFBEntitySet();

        String[] array__string = _string.split(",");

        String string;

        int length__for_switch = array__string.length;

        if (length__for_switch > 3) {
            // 遍历解析所有属性
            for (int index = 3; index < array__string.length; index++) {
                string = array__string[index]; // 格式应该为

                // 添加属性
                instance.add_entry(IFBEntitySetEntry.from_string(string));
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
                            "error parsing IFBEntitySet.probability (double), value = \"{}\", message = {}",
                            string, e.getMessage()
                        );
                    }
                }
            case 2:
                string = array__string[1].trim();
                if (!string.isEmpty()) {
                    try {
                        instance.flag__and_mode = Boolean.parseBoolean(string);
                    } catch (Exception e) {
                        IFBConfigCommon.LOGGER.error(
                            "error parsing IFBEntitySet.flag__and_mode (boolean), value = \"{}\", message = {}",
                            string, e.getMessage()
                        );
                    }
                }
            case 1:
                string = array__string[0].trim();
                if (!string.isEmpty()) {
                    instance.id = string;
                }
        }

        return instance;
    }

    public void add_entry(IFBEntitySetEntry _entry) {
        list__entries.add(_entry);
    }


}
