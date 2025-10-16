package org.x4.mc_mod__i_feel_bad.data_structure;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.x4.mc_mod__i_feel_bad.utils.IFBUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 类 IFB 实体集合条目
public class IFBEntitySetEntry {

    // 标记 逻辑非 (真值取反)
    public boolean flag__logical_negation = false;

    // 标记 使用标签过滤
    public boolean flag__tag_filter = false;

    // 标记 使用命名空间过滤
    public boolean flag__namespace_filter = false;

    private boolean flag__cached = false;

    // 字符串 过滤器
    @Nullable
    public String filter = null;

    @Nullable
    private TagKey<EntityType<?>> tag__entity = null;

    @Nullable
    private EntityType<?> type__entity = null;

    public static final IFBEntitySetEntry default_value;

    static {
        default_value = new IFBEntitySetEntry();
        default_value.filter = "minecraft:zombie";
    }

    // 默认构造函数
    public IFBEntitySetEntry() {
    }

    public void init() {
        // 不能在初始化配置时获取, 模组内容可能拿不到
//        if (filter != null) {
//            if (flag__tag_filter) {
//                // 获取标签对象
//                tag__entity = IFBUtils.get_entity_tag_without_checking(filter);
//            }
//        }
    }

    // 检查实体是否匹配当前条目
    public boolean check_entity(@NotNull Entity _entity) {
        if (filter == null)
            return false;
        // 比较结果, 是否产生了比较结果
        boolean flag__result = false, flag__has_result = false;

        // 标签过滤器
        if (flag__tag_filter) {
            if (tag__entity == null && !flag__cached) {
                tag__entity = IFBUtils.get_entity_tag_without_checking(filter);
                flag__cached = true;
            }
            // 判断是否满足标签
            flag__result = _entity.getType().is(tag__entity);
            flag__has_result = true;
        }

        // 命名空间过滤器
        if (flag__namespace_filter) {
            // 获取实体类型的的资源位置
            ResourceLocation location = EntityType.getKey(_entity.getType());
            // 判断命名空间是否在文本上相等
            flag__result = filter.equals(location.getNamespace());
            flag__has_result = true;
        }

        // 标准过滤器 (直接比较实体类型对象)
        if (!flag__has_result) {
            if (type__entity == null && !flag__cached) {
                // 根据 ID 获取实体类型
                type__entity = IFBUtils.get_entity_type(filter, _entity.level().registryAccess());
                flag__cached = true;
            }
            flag__result = _entity.getType() == type__entity;
        }
        // 异或操作, 相当于 flag__logical_negation 为真时对后者取反 (反过来说也成立)
        return flag__logical_negation ^ flag__result;
    }

    // 正则表达式 用于解析字符串的正则
    public static final String regex__parse = "\\s*(!?)\\s*(@?)\\s*(#?)\\s*([:.\\w]+)\\s*";

    // 预编译的正则状态机
    public static final Pattern pattern__parse = Pattern.compile(regex__parse);

    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (flag__logical_negation)
            builder.append('!');
        if (flag__namespace_filter)
            builder.append('@');
        if (flag__tag_filter)
            builder.append('#');
        if (filter != null)
            builder.append(filter);
        return builder.toString();
    }

    // 从字符串中解析
    // 字符串应具有如下格式:
    // "!@#xxxx:xxxx", 其中 !@# 是可选的标记
    public static IFBEntitySetEntry from_string(@NotNull String _string) {
        IFBEntitySetEntry instance = null;

        // 匹配
        Matcher matcher = pattern__parse.matcher(_string);

        if (matcher.find()) {
            instance = new IFBEntitySetEntry();

            String string__group;

            string__group = matcher.group(1);
            instance.flag__logical_negation
                = string__group != null && !string__group.isEmpty();

            string__group = matcher.group(2);
            instance.flag__namespace_filter
                = string__group != null && !string__group.isEmpty();

            string__group = matcher.group(3);
            instance.flag__tag_filter
                = string__group != null && !string__group.isEmpty();

            instance.filter = matcher.group(4);
            // 若非 null 但为空字符串, 则设为 null
            if (instance.filter != null && instance.filter.isEmpty())
                instance.filter = null;
        }

        return instance;
    }

}
