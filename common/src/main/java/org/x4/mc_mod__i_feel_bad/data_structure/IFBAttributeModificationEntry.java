package org.x4.mc_mod__i_feel_bad.data_structure;


import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.jetbrains.annotations.Nullable;
import org.x4.mc_mod__i_feel_bad.config.IFBConfigCommon;
import org.x4.mc_mod__i_feel_bad.utils.IFBUtils;

//import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// 类 属性修改条目
// 用于存放单条属性修改条目
public class IFBAttributeModificationEntry {

    // 属性 ID
    public String id__attribute = null;

    // 标记 当缺失时强制添加
    public boolean flag__add_when_missing = false;

    // 属性对象
    @Nullable
    public Holder<Attribute> attribute_type = null;

    private boolean flag__cached = false;

    // 执行概率
    public double probability = 0.0;
    // 最小值
    public double coefficient__k = 1.0;
    // 最大值
    public double coefficient__b = 0.0;

    public double floating = 0.0;

    public double minimum = 1.0;

    public double maximum = 1000.0;

    // 默认值
    public static final IFBAttributeModificationEntry default_value
        = new IFBAttributeModificationEntry();

    // \s*(?:\(\?([+-]?(?:\d+\.?\d*|\d*\.?\d+))f?\))?\s*([:.\w]+)\s*=\s*([+-]?(?:\d+\.?\d*|\d*\.?\d+))\s*x\s*\+\s*([+-]?(?:\d+\.?\d*|\d*\.?\d+))\s*\$[+-]?((?:\d+\.?\d*|\d*\.?\d+))\s*\[\s*([+-]?(?:\d+\.?\d*|\d*\.?\d+))\s*~\s*([+-]?(?:\d+\.?\d*|\d*\.?\d+))\s*\]\s*
    // 正则表达式 用于检查字段
    public static final String regex__attribute_record
        = "\\s*(?:\\(\\?([+-]?(?:\\d+\\.?\\d*|\\d*\\.?\\d+))\\))?\\s*(\\+?)([:.\\w]+)\\s*=\\s*([+-]?(?:\\d+\\.?\\d*|\\d*\\.?\\d+))\\s*x\\s*\\+\\s*([+-]?(?:\\d+\\.?\\d*|\\d*\\.?\\d+))\\s*\\$[+-]?((?:\\d+\\.?\\d*|\\d*\\.?\\d+))\\s*\\[\\s*([+-]?(?:\\d+\\.?\\d*|\\d*\\.?\\d+))\\s*~\\s*([+-]?(?:\\d+\\.?\\d*|\\d*\\.?\\d+))\\s*\\]\\s*";

    // 预编译的正则状态机
    public static final Pattern pattern__attribute = Pattern.compile(regex__attribute_record);

    public String toString() {
        return String.format(
            "(?%.2f) %s%s=%.2fx+%.2f $%.2f [%.2f~%.2f]",
            probability, flag__add_when_missing ? "+" : "", id__attribute,
            coefficient__k, coefficient__b, floating, minimum, maximum
        );
    }

    public static @Nullable IFBAttributeModificationEntry from_string(String _str) {
        // 正则匹配
        Matcher matcher = pattern__attribute.matcher(_str);

        IFBAttributeModificationEntry attribute_data = null;

        if (matcher.find()) {
            attribute_data = new IFBAttributeModificationEntry();

            String string__group;

            try {
                string__group = matcher.group(1);
                if (string__group != null && !string__group.isEmpty())
                    attribute_data.probability = Double.parseDouble(string__group);

                string__group = matcher.group(2);
                attribute_data.flag__add_when_missing
                    = string__group != null && !string__group.isEmpty();

                string__group = matcher.group(3);
                if (string__group != null && !string__group.isEmpty())
                    attribute_data.id__attribute = matcher.group(3);

                string__group = matcher.group(4);
                if (string__group != null && !string__group.isEmpty())
                    attribute_data.coefficient__k = Double.parseDouble(matcher.group(4));

                string__group = matcher.group(5);
                if (string__group != null && !string__group.isEmpty())
                    attribute_data.coefficient__b = Double.parseDouble(matcher.group(5));

                string__group = matcher.group(6);
                if (string__group != null && !string__group.isEmpty())
                    attribute_data.floating = Double.parseDouble(matcher.group(6));

                string__group = matcher.group(7);
                if (string__group != null && !string__group.isEmpty())
                    attribute_data.minimum = Double.parseDouble(matcher.group(7));

                string__group = matcher.group(8);
                if (string__group != null && !string__group.isEmpty())
                    attribute_data.maximum = Double.parseDouble(matcher.group(8));
            } catch (Exception e) {
                IFBConfigCommon.LOGGER.error(
                    "error parsing attribute modification entry, value = \"{}\", message = {}",
                    _str, e.getMessage()
                );
            }
        }

        return attribute_data;
    }

    // 默认构造函数
    public IFBAttributeModificationEntry() {
//        id__attribute = null;
//        attribute_type = null;
//        probability = 0.0;
//        coefficient__k = 1.0;
//        coefficient__b = 0.0;
//        floating = 0.0;
//        minimum = 1.0;
//        maximum = 1000.0;
    }

    public IFBAttributeModificationEntry(
        String _id__attribute, boolean _flag__add_when_missing,
        double _probability, double _coefficient__k, double _coefficient__b,
        double _floating, double _minimum, double _maximum
    ) {
        id__attribute = _id__attribute;
        flag__add_when_missing = _flag__add_when_missing;
        probability = _probability;
        coefficient__k = _coefficient__k;
        coefficient__b = _coefficient__b;
        floating = _floating;
        minimum = _minimum;
        maximum = _maximum;
    }

    // 缓存属性类型
    public void cache_attribute_type() {
        // 查找并设置属性对象; 属性对象用于游戏运行时获取实体的属性
        ResourceLocation location__attribute = ResourceLocation.tryParse(id__attribute);
        if (location__attribute != null) {
            var optional__holder = BuiltInRegistries.ATTRIBUTE.getHolder(location__attribute);
            // 检查是否存在
            attribute_type = optional__holder.orElse(null);
        } else {
            attribute_type = null;
        }
        // 无论是否成功获取, 都视为已缓存
        flag__cached = true;
    }

    // 修改实体属性
    public void modify_entity_attribute(
        LivingEntity _entity,
        AttributeMap _map__attributes,
        boolean _flag__fix_attribute,
        String _type
    ) {
        // 检查是否已经缓存, 未缓存就先获取
        if (!flag__cached)
            cache_attribute_type();

        // 检查属性是否为空
        if (this.attribute_type == null)
            return;

        double random = Math.random();
        // 判定
        if (random > this.probability)
            return;

        // 检查属性是否存在
        if (!_map__attributes.hasAttribute(this.attribute_type)) {
            // 检查是否需要在不存在时添加属性
            if (!this.flag__add_when_missing) {
                // 不强制添加, 跳过
                return;
            }
        }

        // 不为空时获取属性示例
        var attribute = _map__attributes.getInstance(this.attribute_type);

        // 计算目标值
        double value__target
            = this.coefficient__k
            * (attribute == null ? 0 : attribute.getBaseValue())
            + this.coefficient__b;

        // 随机浮动
        value__target += -this.floating + Math.random() * 2 * this.floating;

        // 限制范围
        value__target = Math.clamp(value__target, this.minimum, this.maximum);

        // 设置属性值
        if (attribute != null) {
            attribute.setBaseValue(value__target);
        }

        // 修复属性问题
        if (_flag__fix_attribute) {
            // 检查是否为 MAX_HEALTH, 若是则再补充设置当前实体的生命值
            if (this.attribute_type == Attributes.MAX_HEALTH)
                _entity.setHealth((float) value__target);

            // 检查是否为 FOLLOW_RANGE, 若是则进行更新修复
            if (this.attribute_type == Attributes.FOLLOW_RANGE)
                if (_entity instanceof Mob mob)
                    IFBUtils.fix_follow_range_attribute_update_error(mob, value__target);

            // 检查是否为 MOVEMENT_SPEED, 若是则补充设置速度
            if (this.attribute_type == Attributes.MOVEMENT_SPEED)
                _entity.setSpeed((float) value__target);
        }

        if (IFBConfigCommon.CONFIG.config__i_feel_bad.flag__enable_logging) {
            IFBConfigCommon.LOGGER.info(
                "{} i_feel_bad modify entity attribute; type = {}, {} = {}",
                String.format("(?%.2f<%.2f)", random, probability), _type, this.id__attribute, value__target
            );
        }

    }
}
