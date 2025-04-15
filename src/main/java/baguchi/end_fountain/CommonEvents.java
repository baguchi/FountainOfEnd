package baguchi.end_fountain;

import baguchi.end_fountain.register.ModAttachments;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = FountainOfEnd.MODID)
public class CommonEvents {
    @SubscribeEvent
    public static void onTickEvent(EntityTickEvent.Pre event) {
        event.getEntity().getData(ModAttachments.STUCK).tick();
    }
}
