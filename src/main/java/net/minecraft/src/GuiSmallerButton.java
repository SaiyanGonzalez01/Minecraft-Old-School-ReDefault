package net.minecraft.src;

public class GuiSmallerButton extends GuiButton {
	private final EnumOptions enumOptions;

	public GuiSmallerButton(int var1, int var2, int var3, String var4) {
		this(var1, var2, var3, (EnumOptions)null, var4);
	}

	public GuiSmallerButton(int var1, int var2, int var3, int var4, int var5, String var6) {
		super(var1, var2, var3, var4, var5, var6);
		this.enumOptions = null;
	}

	public GuiSmallerButton(int var1, int var2, int var3, EnumOptions var4, String var5) {
		super(var1, var2, var3, 20, 20, var5);
		this.enumOptions = var4;
	}

	public EnumOptions returnEnumOptions() {
		return this.enumOptions;
	}
}
