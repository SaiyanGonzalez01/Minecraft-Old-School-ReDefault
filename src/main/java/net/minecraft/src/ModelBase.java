package net.minecraft.src;

public abstract class ModelBase {
	public float onGround;
	public boolean isRiding = false;
	public boolean isChild = true;

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	public void render(float var1, float var2, float var3, float var4, float var5, float var6) {
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are used
	 * for animating the movement of arms and legs, where par1 represents the
	 * time(so that arms and legs swing back and forth) and par2 represents how
	 * "far" arms and legs can swing at most.
	 */
	public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6) {
	}

	/**
	 * Used for easily adding entity-dependent animations. The second and third
	 * float params here are the same second and third as in the setRotationAngles
	 * method.
	 */
	public void setLivingAnimations(EntityLiving var1, float var2, float var3, float var4) {
	}
}
