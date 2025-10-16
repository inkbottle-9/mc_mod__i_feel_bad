package org.x4.mc_mod__i_feel_bad.data_structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * @param value__hp         生命值
 * @param value__fullness   饱腹值
 * @param value__saturation 饱和度
 */ // 类 用户死亡快照
public record IFBPlayerDeathState(
    float value__hp,
    int value__fullness,
    float value__saturation,
    // 用于标记该对象是否是默认值, 如果是默认值不需要考虑
    boolean flag__is_default
) {
    // 空值
    public static final IFBPlayerDeathState default_value
        = new IFBPlayerDeathState(0.0f, 0, 0.0f, true);

    public static final Codec<IFBPlayerDeathState> CODEC = RecordCodecBuilder.create(
        instance -> instance.group(
            Codec.FLOAT.fieldOf("value__hp").forGetter(IFBPlayerDeathState::value__hp),
            Codec.INT.fieldOf("value__fullness").forGetter(IFBPlayerDeathState::value__fullness),
            Codec.FLOAT.fieldOf("value__saturation").forGetter(IFBPlayerDeathState::value__saturation),
            Codec.BOOL.fieldOf("flag__is_default").forGetter(IFBPlayerDeathState::flag__is_default)
        ).apply(instance, IFBPlayerDeathState::new)
    );

    public boolean equals(@NotNull IFBPlayerDeathState _another) {
        return this.value__fullness == _another.value__fullness && this.value__hp == _another.value__hp;
    }
}
