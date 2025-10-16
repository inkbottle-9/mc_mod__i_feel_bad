package org.x4.mc_mod__i_feel_bad.neoforge.attachment;

import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.x4.mc_mod__i_feel_bad.IFeelBadCommon;
import org.x4.mc_mod__i_feel_bad.data_structure.IFBPlayerDeathState;

import java.util.function.Supplier;

// 类 IFB 玩家死亡状态附件
public class IFBPlayerDeathStateAttachment {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, IFeelBadCommon.MOD_ID);

    public static final Supplier<AttachmentType<IFBPlayerDeathState>> player_death_state =
            IFBPlayerDeathStateAttachment.ATTACHMENT_TYPES.register(
                    "player_death_state",
                    () -> AttachmentType.builder(() -> IFBPlayerDeathState.default_value)
                            .serialize(IFBPlayerDeathState.CODEC)
                            .copyOnDeath()
                            .build()
            );


}


