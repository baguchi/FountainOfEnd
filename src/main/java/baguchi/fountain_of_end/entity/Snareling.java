package baguchi.fountain_of_end.entity;

import baguchi.fountain_of_end.entity.projectile.SnareBall;
import baguchi.fountain_of_end.register.ModAttachments;
import baguchi.fountain_of_end.register.ModSounds;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class Snareling extends Enderling implements RangedAttackMob {
    private static final EntityDataAccessor<Boolean> DATA_SPIN_ATTACK_ID = SynchedEntityData.defineId(Snareling.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState attackAnimationState = new AnimationState();

    public Snareling(EntityType<? extends Snareling> entityType, Level level) {
        super(entityType, level);
        this.xpReward = 8;
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_SPIN_ATTACK_ID, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SnarelingMeleeAttackGoal());
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 0.9F, 80, 10F) {
            @Override
            public boolean canUse() {
                return super.canUse() && getTarget() != null && !getTarget().getData(ModAttachments.STUCK).isStuck();
            }

            @Override
            public boolean canContinueToUse() {
                return super.canContinueToUse() && getTarget() != null && !getTarget().getData(ModAttachments.STUCK).isStuck();
            }
        });
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, (double) 0.8F, 0.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new EndermanLookForPlayerGoal(this, this::isAngryAt));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this, Enderling.class).setAlertOthers());
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal(this, false));
    }

    @Override
    public void makeStuckInBlock(BlockState state, Vec3 motionMultiplier) {
    }

    public boolean isSpinning() {
        return this.entityData.get(DATA_SPIN_ATTACK_ID);
    }

    public void setSpinning(boolean spinning) {
        this.entityData.set(DATA_SPIN_ATTACK_ID, spinning);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MAX_HEALTH, (double) 30.0F).add(Attributes.MOVEMENT_SPEED, (double) 0.24F).add(Attributes.ATTACK_DAMAGE, (double) 2.0F).add(Attributes.FOLLOW_RANGE, (double) 18.0F).add(Attributes.STEP_HEIGHT, (double) 1.0F);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.SNARELING_IDLE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.SNARELING_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.SNARELING_DEATH.get();
    }


    @Override
    public void aiStep() {
        super.aiStep();
        if (getTarget() != null && getTarget().getData(ModAttachments.STUCK).isStuck()) {
            if (this.distanceTo(getTarget()) > 4) {
                this.teleportTowards(getTarget());
            }
        }
        if (this.level().isClientSide()) {
            if (this.isSpinning()) {
                this.attackAnimationState.startIfStopped(this.tickCount);
            } else {
                this.attackAnimationState.stop();
            }
        }
    }

    public boolean isLookingAtMe(Player player) {
        ItemStack itemstack = (ItemStack) player.getInventory().armor.get(3);
        if (itemstack.getItem() == Blocks.CARVED_PUMPKIN.asItem()) {
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

    @Override
    public void performRangedAttack(LivingEntity target, float distanceFactor) {
        Vec3 vec3 = target.getDeltaMovement();
        double d0 = target.getX() + vec3.x - this.getX();
        double d1 = target.getEyeY() - 1.1F - this.getY();
        double d2 = target.getZ() + vec3.z - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        SnareBall thrownpotion = new SnareBall(this.level(), this);
        thrownpotion.setXRot(thrownpotion.getXRot() - -20.0F);
        thrownpotion.shoot(d0, d1 + d3 * 0.2, d2, 0.6F + distanceFactor * 0.2F, 8.5F);
        if (!this.isSilent()) {
            this.level()
                    .playSound(
                            null,
                            this.getX(),
                            this.getY(),
                            this.getZ(),
                            SoundEvents.LLAMA_SPIT,
                            this.getSoundSource(),
                            1.0F,
                            0.8F + this.random.nextFloat() * 0.4F
                    );
        }

        this.level().addFreshEntity(thrownpotion);

    }

    class SnarelingMeleeAttackGoal extends MeleeAttackGoal {
        public SnarelingMeleeAttackGoal() {
            super(Snareling.this, 1.1F, true);
        }

        @Override
        public boolean canUse() {
            return super.canUse() && getTarget() != null && getTarget().getData(ModAttachments.STUCK).isStuck();
        }

        @Override
        public boolean canContinueToUse() {
            return getTarget() != null && getTarget().getData(ModAttachments.STUCK).isStuck();
        }

        @Override
        protected void checkAndPerformAttack(LivingEntity target) {
            if (this.canPerformAttack(target)) {
                this.resetAttackCooldown();
                this.mob.doHurtTarget(target);
                Snareling.this.setSpinning(true);
            }
        }

        @Override
        public void stop() {
            Snareling.this.setSpinning(false);
            super.stop();
        }
    }

    static class EndermanLookForPlayerGoal extends NearestAttackableTargetGoal<Player> {
        private final Snareling enderman;
        @Nullable
        private Player pendingTarget;
        private int aggroTime;
        private int teleportTime;
        private final TargetingConditions startAggroTargetConditions;
        private final TargetingConditions continueAggroTargetConditions = TargetingConditions.forCombat().ignoreLineOfSight();
        private final Predicate<LivingEntity> isAngerInducing;

        public EndermanLookForPlayerGoal(Snareling enderman, @Nullable Predicate<LivingEntity> selectionPredicate) {
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
