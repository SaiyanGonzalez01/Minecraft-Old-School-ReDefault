package net.minecraft.src;

public class EntityIceSkeleton extends EntityMob {
	private static final ItemStack defaultHeldItem = new ItemStack(Item.iceBall, 1);

	public EntityIceSkeleton(World var1) {
		super(var1);
	}

	protected String getLivingSound() {
		return "mob.skeleton";
	}

	protected String getHurtSound() {
		return "mob.skeletonhurt";
	}

	protected String getDeathSound() {
		return "mob.skeletonhurt";
	}

	public void onLivingUpdate() {
		if(this.worldObj.isDaytime()) {
			float var1 = this.getEntityBrightness(1.0F);
			if(var1 > 0.5F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F) {
				this.fire = 300;
			}
		}

		super.onLivingUpdate();
	}

	protected void attackEntity(Entity var1, float var2) {
		if(var2 < 10.0F) {
			double var3 = var1.posX - this.posX;
			double var5 = var1.posZ - this.posZ;
			if(this.attackTime == 0) {
				EntityArrow var7 = new EntityArrow(this.worldObj, this);
				var7.posY += (double)1.4F;
				double var8 = var1.posY + (double)var1.getEyeHeight() - (double)0.2F - var7.posY;
				float var10 = MathHelper.sqrt_double(var3 * var3 + var5 * var5) * 0.2F;
				this.worldObj.playSoundAtEntity(this, "random.bow", 0.5F, 0.4F / (this.rand.nextFloat() * 0.4F + 0.8F));
				this.worldObj.entityJoinedWorld(var7);
				var7.setArrowHeading(var3, var8 + (double)var10, var5, 0.6F, 12.0F);
				this.attackTime = 30;
			}

			this.rotationYaw = (float)(Math.atan2(var5, var3) * 180.0D / (double)((float)Math.PI)) - 90.0F;
			this.hasAttacked = true;
		}

	}

	public void writeEntityToNBT(NBTTagCompound var1) {
		super.writeEntityToNBT(var1);
	}

	public void readEntityFromNBT(NBTTagCompound var1) {
		super.readEntityFromNBT(var1);
	}

	protected int getDropItemId() {
		return Item.iceBall.shiftedIndex;
	}

	protected void dropFewItems() {
		int var1 = this.rand.nextInt(3);

		int var2;
		for(var2 = 0; var2 < var1; ++var2) {
			this.dropItem(Item.iceBall.shiftedIndex, 1);
		}

		var1 = this.rand.nextInt(3);

		for(var2 = 0; var2 < var1; ++var2) {
			this.dropItem(Item.bone.shiftedIndex, 1);
		}

	}

	public ItemStack getHeldItem() {
		return defaultHeldItem;
	}
}
