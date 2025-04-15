package baguchi.end_fountain.entity;

import bagu_chan.bagus_lib.entity.goal.AnimateAttackGoal;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.CommonHooks;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class Watchling extends EnderMan {
    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();

    public int attackAnimationTick;
    public final int attackAnimationLength = 20;
    public final int attackAnimationActionPoint = 5;

    public Watchling(EntityType<? extends Watchling> entityType, Level level) {
        super(entityType, level);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new AnimateAttackGoal(this, (double) 1.0F, this.attackAnimationActionPoint, this.attackAnimationLength));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, (double) 1.0F, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new EndermanLookForPlayerGoal(this, this::isAngryAt));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal(this, false));
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, (double) 20.0F).add(Attributes.MOVEMENT_SPEED, (double) 0.24F).add(Attributes.ATTACK_DAMAGE, (double) 4.0F).add(Attributes.FOLLOW_RANGE, (double) 24.0F).add(Attributes.STEP_HEIGHT, (double) 1.0F);
    }

    @Override
    public void handleEntityEvent(byte p_21375_) {
        if (p_21375_ == 4) {
            this.attackAnimationState.start(this.tickCount);
            this.attackAnimationTick = 0;
        } else {
            super.handleEntityEvent(p_21375_);
        }
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (this.level().isClientSide) {
            if (this.attackAnimationTick < this.attackAnimationLength) {
                this.attackAnimationTick++;
            }

            if (this.attackAnimationTick >= this.attackAnimationLength) {
                this.attackAnimationState.stop();
            }
            this.idleAnimationState.startIfStopped(this.tickCount);
        }
    }

    boolean isLookingAtMe(Player player) {
        ItemStack itemstack = (ItemStack) player.getInventory().armor.get(3);
        if (CommonHooks.shouldSuppressEnderManAnger(this, player, itemstack)) {
            return false;
        } else {
            Vec3 vec3 = player.getViewVector(1.0F).normalize();
            Vec3 vec31 = new Vec3(this.getX() - player.getX(), this.getEyeY() - player.getEyeY(), this.getZ() - player.getZ());
            double d0 = vec31.length();
            vec31 = vec31.normalize();
            double d1 = vec3.dot(vec31);
            return d1 > (double) 1.0F - 0.05 / d0 ? player.hasLineOfSight(this) : false;
        }
    }

    public void playStareSound() {
    }

    static class EndermanLookForPlayerGoal extends NearestAttackableTargetGoal<Player> {
        private final Watchling enderman;
        @Nullable
        private Player pendingTarget;
        private int aggroTime;
        private int teleportTime;
        private final TargetingConditions startAggroTargetConditions;
        private final TargetingConditions continueAggroTargetConditions = TargetingConditions.forCombat().ignoreLineOfSight();
        private final Predicate<LivingEntity> isAngerInducing;

        public EndermanLookForPlayerGoal(Watchling enderman, @Nullable Predicate<LivingEntity> selectionPredicate) {
            super(enderman, Player.class, 10, false, false, selectionPredicate);
            this.enderman = enderman;
            this.isAngerInducing = (p_325811_) -> (enderman.isLookingAtMe((Player) p_325811_) || enderman.isAngryAt(p_325811_)) && !enderman.hasIndirectPassenger(p_325811_);
            this.startAggroTargetConditions = TargetingConditions.forCombat().range(this.getFollowDistance()).selector(this.isAngerInducing);
        }

        public boolean canUse() {
            this.pendingTarget = this.enderman.level().getNearestPlayer(this.startAggroTargetConditions, this.enderman);
            return this.pendingTarget != null;
        }

        public void start() {
            this.aggroTime = this.adjustedTickDelay(5);
            this.teleportTime = 0;
            this.enderman.setBeingStaredAt();
        }

        public void stop() {
            this.pendingTarget = null;
            super.stop();
        }

        public boolean canContinueToUse() {
            if (this.pendingTarget != null) {
                if (!this.isAngerInducing.test(this.pendingTarget)) {
                    return false;
                } else {
                    this.enderman.lookAt(this.pendingTarget, 10.0F, 10.0F);
                    return true;
                }
            } else {
                if (this.target != null) {
                    if (this.enderman.hasIndirectPassenger(this.target)) {
                        return false;
                    }

                    if (this.continueAggroTargetConditions.test(this.enderman, this.target)) {
                        return true;
                    }
                }

                return super.canContinueToUse();
            }
        }

        public void tick() {
            if (this.enderman.getTarget() == null) {
                super.setTarget((LivingEntity) null);
            }

            if (this.pendingTarget != null) {
                if (--this.aggroTime <= 0) {
                    this.target = this.pendingTarget;
                    this.pendingTarget = null;
                    super.start();
                }
            } else {
                if (this.target != null && !this.enderman.isPassenger()) {
                    if (this.enderman.isLookingAtMe((Player) this.target)) {
                        if (this.target.distanceToSqr(this.enderman) < (double) 16.0F) {
                            this.enderman.teleport();
                        }

                        this.teleportTime = 0;
                    }
                }

                super.tick();
            }

        }
    }
}
