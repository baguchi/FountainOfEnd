package baguchi.end_fountain.util;

import baguchi.end_fountain.entity.Enderling;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class EnderlingUtil {
    public static void angerNearbyEnderling(Player player, boolean angerOnlyIfCanSee) {
        List<Enderling> list = player.level().getEntitiesOfClass(Enderling.class, player.getBoundingBox().inflate(18.0));
        list.stream().filter(p_34881_ -> !angerOnlyIfCanSee || BehaviorUtils.canSee(p_34881_, player)).forEach(p_352819_ -> {
            if (p_352819_.getTarget() == null) {
                p_352819_.setTarget(player);
            }
        });
    }
}
