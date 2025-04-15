package baguchi.end_fountain.entity.projectile;

import baguchi.end_fountain.register.ModEntities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;

public class SnareBall extends ThrowableProjectile {
    public float damage = 2;
    @Nullable
    protected ItemStack firedFromWeapon = null;

    public SnareBall(EntityType<? extends SnareBall> p_i50154_1_, Level p_i50154_2_) {
        super(p_i50154_1_, p_i50154_2_);
    }

    public SnareBall(Level worldIn, LivingEntity throwerIn) {
        super(ModEntities.SNARE_BALL.get(), throwerIn, worldIn);
    }

    public SnareBall(Level worldIn, LivingEntity throwerIn, ItemStack stack) {
        super(ModEntities.SNARE_BALL.get(), throwerIn, worldIn);
        this.firedFromWeapon = stack.copy();
    }

    public SnareBall(Level worldIn, double x, double y, double z) {
        super(ModEntities.SNARE_BALL.get(), x, y, z, worldIn);

    }

    public SnareBall(EntityType<? extends SnareBall> p_i50154_1_, Level worldIn, double x, double y, double z) {
        super(p_i50154_1_, x, y, z, worldIn);
    }

    public SnareBall(EntityType<? extends SnareBall> entityType, LivingEntity throwerIn, Level worldIn) {
        super(entityType, throwerIn, worldIn);
    }

    protected void defineSynchedData(SynchedEntityData.Builder builder) {
    }


    @Override
    protected void onHitEntity(EntityHitResult p_37404_) {
        super.onHitEntity(p_37404_);
        Entity entity = p_37404_.getEntity();

        if (this.getOwner() != null && this.getOwner().isAlliedTo(entity)) {
            return;
        }
        if (!this.level().isClientSide) {
            playSound(SoundEvents.SLIME_JUMP_SMALL, 0.8F, 0.8F + this.level().random.nextFloat() * 0.4F);

            if (!this.level().isClientSide) {
                SnareWebEntity snareWebEntity = new SnareWebEntity(this.level(), getX(), getY(), getZ());
                this.level().addFreshEntity(snareWebEntity);
                this.discard();
            }
        }

    }


    protected void hitBlockEnchantmentEffects(ServerLevel p_345462_, BlockHitResult p_345204_, ItemStack p_345083_) {
        Vec3 vec3 = p_345204_.getBlockPos().clampLocationWithin(p_345204_.getLocation());
        EnchantmentHelper.onHitBlock(
                p_345462_,
                p_345083_,
                this.getOwner() instanceof LivingEntity livingentity ? livingentity : null,
                this,
                null,
                vec3,
                p_345462_.getBlockState(p_345204_.getBlockPos()),
                p_348569_ -> this.firedFromWeapon = null
        );
    }

    @Override
    public ItemStack getWeaponItem() {
        return this.firedFromWeapon;
    }

    @Override
    protected void onHit(HitResult p_37406_) {
        super.onHit(p_37406_);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);

        ItemStack itemstack = this.getWeaponItem();
        if (this.level() instanceof ServerLevel serverlevel && itemstack != null) {
            this.hitBlockEnchantmentEffects(serverlevel, result, itemstack);
        }
        if (!this.level().isClientSide) {
            playSound(SoundEvents.SLIME_JUMP_SMALL, 0.8F, 0.8F + this.level().random.nextFloat() * 0.4F);
            if (!this.level().isClientSide) {
                //I don't use it particularly, but it's something.↓
                this.level().broadcastEntityEvent(this, (byte) 80);
                SnareWebEntity entity = new SnareWebEntity(this.level(), result.getLocation().x, result.getLocation().y, result.getLocation().z);
                entity.setAttachFace(result.getDirection());
                this.level().addFreshEntity(entity);
                this.discard();
            }
        }
    }

    public void addAdditionalSaveData(CompoundTag p_37222_) {
        super.addAdditionalSaveData(p_37222_);
        p_37222_.putFloat("Damage", (byte) this.damage);
        if (this.firedFromWeapon != null) {
            p_37222_.put("weapon", this.firedFromWeapon.save(this.registryAccess(), new CompoundTag()));
        }
    }

    public void readAdditionalSaveData(CompoundTag p_37220_) {
        super.readAdditionalSaveData(p_37220_);
        if (p_37220_.contains("Damage", 99)) {
            this.damage = p_37220_.getFloat("Damage");
        }
        if (p_37220_.contains("weapon", 10)) {
            this.firedFromWeapon = ItemStack.parse(this.registryAccess(), p_37220_.getCompound("weapon")).orElse(null);
        } else {
            this.firedFromWeapon = null;
        }
    }
}