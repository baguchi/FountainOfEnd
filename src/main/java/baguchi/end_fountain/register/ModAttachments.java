package baguchi.end_fountain.register;

import baguchi.end_fountain.FountainOfEnd;
import baguchi.end_fountain.attachment.StuckAttachment;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, FountainOfEnd.MODID);

    public static final Supplier<AttachmentType<StuckAttachment>> STUCK = ATTACHMENT_TYPES.register(
            "stuck", () -> AttachmentType.builder(StuckAttachment::new).build());
}
