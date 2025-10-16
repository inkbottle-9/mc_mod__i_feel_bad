package org.x4.mc_mod__i_feel_bad.data_structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

// 类 状态赋值记录
// 存储添加状态时需要使用的信息
public class IFBStatusAssignmentRecord {
    public String id__status_effect = null;     // 效果 ID (例如 "minecraft:slowness")
    public int duration = 200;                  // 持续时间, 单位: 刻 (每秒 20 刻)
    public int amplifier = 0;                   // 效果等级, 0 表示等级 1
    public boolean flag__ambient = false;       // 是否为环境来源
    public boolean flag__visible = true;        // 是否可见 (粒子效果)
    public boolean flag__show_icon = true;      // 是否显示图标

    public IFBStatusAssignmentRecord() {
    }

    // 只初始化 ID 其余默认
    public IFBStatusAssignmentRecord(String _id__status_effect) {
        this.id__status_effect = _id__status_effect;
    }

    // 初始化 ID 和时长
    public IFBStatusAssignmentRecord(String _id__status_effect, int _duration) {
        this.id__status_effect = _id__status_effect;
        this.duration = _duration;
    }

    // 初始化 ID, 时长, 级别
    public IFBStatusAssignmentRecord(String _id__status_effect, int _duration, int _amplifier) {
        this.id__status_effect = _id__status_effect;
        this.duration = _duration;
        this.amplifier = _amplifier;
    }

    // 全部值初始化
    public IFBStatusAssignmentRecord(
        String _id__status_effect,
        int _duration,
        int _amplifier,
        boolean _flag__ambient,
        boolean _flag__visible,
        boolean _flag__show_icon
    ) {
        this.id__status_effect = _id__status_effect;
        this.duration = _duration;
        this.amplifier = _amplifier;
        this.flag__ambient = _flag__ambient;
        this.flag__visible = _flag__visible;
        this.flag__show_icon = _flag__show_icon;
    }

//    public static final List<StatusAssignmentRecord> list__default = new ArrayList<>(List.of(
//        new StatusAssignmentRecord("minecraft:slowness", 600), // 缓慢
//        new StatusAssignmentRecord("minecraft:weakness", 600), // 虚弱
//        new StatusAssignmentRecord("minecraft:darkness", 300), // 黑暗
//        new StatusAssignmentRecord("minecraft:nausea", 300)    // 反胃
//    ));

    // 默认值 (用于提供给配置器)
    // 一分钟 1200 刻, 5 秒 100 刻
    public static final List<String> str_list__default = new ArrayList<>(List.of(
//        new IFBStatusAssignmentRecord("minecraft:unluck", 6000).toString(),  // 霉运
        new IFBStatusAssignmentRecord("minecraft:infested", 6000).toString(),// 感染
        new IFBStatusAssignmentRecord("minecraft:mining_fatigue", 3600).toString(), // 挖掘疲劳
        new IFBStatusAssignmentRecord("minecraft:slowness", 600).toString(), // 缓慢
        new IFBStatusAssignmentRecord("minecraft:weakness", 500).toString(), // 虚弱
        new IFBStatusAssignmentRecord("minecraft:darkness", 400).toString()  // 黑暗
    ));

    public static final Codec<IFBStatusAssignmentRecord> CODEC = RecordCodecBuilder.create(
        inst -> inst.group(
            Codec.STRING.fieldOf("id__status_effect").forGetter(d -> d.id__status_effect),
            Codec.INT.fieldOf("duration").forGetter(d -> d.duration),
            Codec.INT.fieldOf("amplifier").forGetter(d -> d.amplifier),
            Codec.BOOL.fieldOf("flag__ambient").forGetter(d -> d.flag__ambient),
            Codec.BOOL.fieldOf("flag__visible").forGetter(d -> d.flag__visible),
            Codec.BOOL.fieldOf("flag__show_icon").forGetter(d -> d.flag__show_icon)
        ).apply(inst, IFBStatusAssignmentRecord::new)
    );

    // 实例转字符串
    public String toString() {
        return String.format(
            "%s,%d,%d,%b,%b,%b",
            id__status_effect, duration, amplifier, flag__ambient, flag__visible, flag__show_icon
        );
    }

    // 字符串转实例
    public static @NotNull IFBStatusAssignmentRecord from_string(@NotNull String _string) {
        // 去掉空白字符
        String string__no_s = _string.replaceAll("\\s+", "");
        String[] array__string = string__no_s.split(",");
        var instance = new IFBStatusAssignmentRecord();// 具有默认值

        String str__trimmed;

        switch (array__string.length) {
            case 6:
                str__trimmed = array__string[5];
                if (!str__trimmed.isEmpty())
                    instance.flag__show_icon = Boolean.parseBoolean(str__trimmed);
            case 5:
                str__trimmed = array__string[4];
                if (!str__trimmed.isEmpty())
                    instance.flag__visible = Boolean.parseBoolean(str__trimmed);
            case 4:
                str__trimmed = array__string[3];
                if (!str__trimmed.isEmpty())
                    instance.flag__ambient = Boolean.parseBoolean(str__trimmed);
            case 3:
                str__trimmed = array__string[2];
                if (!str__trimmed.isEmpty())
                    instance.amplifier = Integer.parseInt(str__trimmed);
            case 2:
                str__trimmed = array__string[1];
                if (!str__trimmed.isEmpty())
                    instance.duration = Integer.parseInt(str__trimmed);
            case 1:
                str__trimmed = array__string[0];
                if (!str__trimmed.isEmpty())
                    instance.id__status_effect = str__trimmed;
        }
        return instance;
    }
}
