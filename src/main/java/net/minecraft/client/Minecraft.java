package net.minecraft.client;

import net.PeytonPlayz585.input.Keyboard;
import net.PeytonPlayz585.input.Mouse;
import net.PeytonPlayz585.opengl.Display;
import net.PeytonPlayz585.opengl.GL11;
import net.PeytonPlayz585.textures.TextureLocation;
import net.lax1dude.eaglercraft.GuiScreenEditProfile;
import net.lax1dude.eaglercraft.TextureNewClockFX;
import net.lax1dude.eaglercraft.TextureNewCompassFX;
import net.minecraft.src.AchievementList;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ChunkProviderLoadOrGenerate;
import net.minecraft.src.ColorizerFoliage;
import net.minecraft.src.ColorizerGrass;
import net.minecraft.src.ColorizerWater;
import net.minecraft.src.EaglerSaveFormat;
import net.minecraft.src.EffectRenderer;
import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerSP;
import net.minecraft.src.EntityRenderer;
import net.minecraft.src.EnumMovingObjectType;
import net.minecraft.src.EnumOptions;
import net.minecraft.src.FontRenderer;
import net.minecraft.src.GLAllocation;
import net.minecraft.src.GameSettings;
import net.minecraft.src.GuiAchievement;
import net.minecraft.src.GuiChat;
import net.minecraft.src.GuiConflictWarning;
import net.minecraft.src.GuiConnecting;
import net.minecraft.src.GuiErrorScreen;
import net.minecraft.src.GuiGameOver;
import net.minecraft.src.GuiIngame;
import net.minecraft.src.GuiIngameMenu;
import net.minecraft.src.GuiInventory;
import net.minecraft.src.GuiMainMenu;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiSleepMP;
import net.minecraft.src.GuiUnused;
import net.minecraft.src.IChunkProvider;
import net.minecraft.src.ISaveFormat;
import net.minecraft.src.ISaveHandler;
import net.minecraft.src.ItemRenderer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.LoadingScreenRenderer;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MinecraftError;
import net.minecraft.src.MinecraftException;
import net.minecraft.src.ModelBiped;
import net.minecraft.src.MouseHelper;
import net.minecraft.src.MovementInputFromOptions;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.NetClientHandler;
import net.minecraft.src.PlayerController;
import net.minecraft.src.PlayerControllerTest;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.RenderEngine;
import net.minecraft.src.RenderGlobal;
import net.minecraft.src.RenderManager;
import net.minecraft.src.ScaledResolution;
import net.minecraft.src.ScreenShotHelper;
import net.minecraft.src.Session;
import net.minecraft.src.SoundManager;
import net.minecraft.src.StatFileWriter;
import net.minecraft.src.StatList;
import net.minecraft.src.StatStringFormatKeyInv;
import net.minecraft.src.Teleporter;
import net.minecraft.src.Tessellator;
import net.minecraft.src.TexturePackList;
import net.minecraft.src.Timer;
import net.minecraft.src.UnexpectedThrowable;
import net.minecraft.src.Vec3D;
import net.minecraft.src.World;
import net.minecraft.src.WorldProvider;
import net.minecraft.src.WorldRenderer;

public class Minecraft implements Runnable {
	public static byte[] field_28006_b = new byte[10485760];
	private static Minecraft theMinecraft;
	public PlayerController playerController;
	private boolean hasCrashed = false;
	public int displayWidth;
	public int displayHeight;
	private Timer timer = new Timer(20.0F);
	public World theWorld;
	public RenderGlobal renderGlobal;
	public EntityPlayerSP thePlayer;
	public EntityLiving renderViewEntity;
	public EffectRenderer effectRenderer;
	public Session session = null;
	public String minecraftUri;
	public volatile boolean isGamePaused = false;
	public RenderEngine renderEngine;
	public FontRenderer fontRenderer;
	public GuiScreen currentScreen = null;
	public LoadingScreenRenderer loadingScreen = new LoadingScreenRenderer(this);
	public EntityRenderer entityRenderer;
	private int ticksRan = 0;
	private int leftClickCounter = 0;
	private int tempDisplayWidth;
	private int tempDisplayHeight;
	public GuiAchievement guiAchievement = new GuiAchievement(this);
	public GuiIngame ingameGUI;
	public boolean skipRenderWorld = false;
	public ModelBiped field_9242_w = new ModelBiped(0.0F);
	public MovingObjectPosition objectMouseOver = null;
	public GameSettings gameSettings;
	public SoundManager sndManager = new SoundManager();
	public MouseHelper mouseHelper;
	public TexturePackList texturePackList;
	private ISaveFormat saveLoader;
	public static long[] frameTimes = new long[512];
	public static long[] tickTimes = new long[512];
	public static int numRecordedFrameTimes = 0;
	public StatFileWriter statFileWriter;
	private String serverName;
	private int serverPort;
	private static final String minecraftDir = "minecraft";
	public volatile boolean running = true;
	public String debug = "";
	boolean isTakingScreenshot = false;
	long prevFrameTime = -1L;
	public boolean inGameHasFocus = false;
	private int mouseTicksRan = 0;
	public boolean isRaining = false;
	long systemTime = System.currentTimeMillis();
	private int joinPlayerCounter = 0;
	
	public static int debugFPS;

	public boolean hasRefreshed = false;
	public boolean doPrints;

	//control
	public static final int KEY_F2 = 60;
	public static final int KEY_F4 = 62;
	public static final int KEY_F6 = 64; 
	public static final int KEY_F9 = 67;

	
	private static final TextureLocation terrainTexture = new TextureLocation("/terrain.png");

	public Minecraft() {
		StatList.func_27360_a();
		this.tempDisplayHeight = GL11.EaglerAdapterImpl2.getCanvasHeight();
		this.displayWidth = GL11.EaglerAdapterImpl2.getCanvasWidth();
		this.displayHeight = GL11.EaglerAdapterImpl2.getCanvasHeight();

		theMinecraft = this;
	}

	public void onMinecraftCrash(UnexpectedThrowable var1) {
		this.hasCrashed = true;
	}

	public void setServer(String var1, int var2) {
		this.serverName = var1;
		this.serverPort = var2;
	}

	public void startGame() {
		this.saveLoader = new EaglerSaveFormat(minecraftDir + "/" + "saves");
		this.gameSettings = new GameSettings(this, minecraftDir);
		this.doPrints = this.gameSettings.doPrints;
		this.texturePackList = new TexturePackList(this, this.minecraftDir);
		this.renderEngine = new RenderEngine(this.texturePackList, this.gameSettings);
		this.fontRenderer = new FontRenderer(this.gameSettings, "/font/default.png", this.renderEngine);
		ColorizerWater.func_28182_a(this.renderEngine.func_28149_a("/misc/watercolor.png"));
		ColorizerGrass.func_28181_a(this.renderEngine.func_28149_a("/misc/grasscolor.png"));
		ColorizerFoliage.func_28152_a(this.renderEngine.func_28149_a("/misc/foliagecolor.png"));
		this.entityRenderer = new EntityRenderer(this);
		RenderManager.instance.itemRenderer = new ItemRenderer(this);
		this.statFileWriter = new StatFileWriter(this.session, minecraftDir + "/misc");
		AchievementList.openInventory.setStatStringFormatter(new StatStringFormatKeyInv(this));
		this.loadScreen();
		this.mouseHelper = new MouseHelper();

		this.checkGLError("Pre startup");
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glClearDepth(1.0F);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthFunc(GL11.GL_LEQUAL);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		GL11.glCullFace(GL11.GL_BACK);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		this.checkGLError("Startup");
		this.sndManager.loadSoundSettings(this.gameSettings);
		renderEngine.registerTextureFX(new TextureNewCompassFX());
		renderEngine.registerTextureFX(new TextureNewClockFX());
		renderEngine.registerSpriteSheet("portal", Block.portal.blockIndexInTexture, 1);
		renderEngine.registerSpriteSheet("water", Block.waterStill.blockIndexInTexture, 1);
		renderEngine.registerSpriteSheet("water_flow", Block.waterMoving.blockIndexInTexture + 1, 2);
		renderEngine.registerSpriteSheet("lava", Block.lavaStill.blockIndexInTexture, 1);
		renderEngine.registerSpriteSheet("lava_flow", Block.lavaMoving.blockIndexInTexture + 1, 2);
		renderEngine.registerSpriteSheet("fire_0", Block.fire.blockIndexInTexture, 1);
		renderEngine.registerSpriteSheet("fire_1", Block.fire.blockIndexInTexture + 16, 1);
		this.renderGlobal = new RenderGlobal(this, this.renderEngine);
		GL11.glViewport(0, 0, this.displayWidth, this.displayHeight);
		this.effectRenderer = new EffectRenderer(this.theWorld, this.renderEngine);

		this.checkGLError("Post startup");
		this.ingameGUI = new GuiIngame(this);
		
		GL11.anisotropicPatch(GL11.EaglerAdapterImpl2.glNeedsAnisotropicFix());
		
		this.displayGuiScreen(new GuiScreenEditProfile(new GuiMainMenu()));
	}

	private void loadScreen() {
		ScaledResolution var1 = new ScaledResolution(this.gameSettings, this.displayWidth, this.displayHeight);
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, var1.field_25121_a, var1.field_25120_b, 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
		GL11.glViewport(0, 0, this.displayWidth, this.displayHeight);
		GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
		Tessellator var2 = Tessellator.instance;
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.renderEngine.getTexture("/title/mojang.png"));
		var2.startDrawingQuads();
		var2.setColorRGBA_F(255, 255, 255, 255);
		var2.addVertexWithUV(0.0D, (double)this.displayHeight, 0.0D, 0.0D, 0.0D);
		var2.addVertexWithUV((double)this.displayWidth, (double)this.displayHeight, 0.0D, 0.0D, 0.0D);
		var2.addVertexWithUV((double)this.displayWidth, 0.0D, 0.0D, 0.0D, 0.0D);
		var2.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		var2.draw();
		short var3 = 256;
		short var4 = 256;
		var2.setColorRGBA_F(255, 255, 255, 255);
		this.func_6274_a((var1.getScaledWidth() - var3) / 2, (var1.getScaledHeight() - var4) / 2, 0, 0, var3, var4);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_FOG);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
		
		//Emulate Display.swapBuffers()
		GL11.glFlush();
		Display.update();
		GL11.optimize();
	}

	public void func_6274_a(int var1, int var2, int var3, int var4, int var5, int var6) {
		float var7 = 0.00390625F;
		float var8 = 0.00390625F;
		Tessellator var9 = Tessellator.instance;
		var9.startDrawingQuads();
		var9.addVertexWithUV((double)(var1 + 0), (double)(var2 + var6), 0.0D, (double)((float)(var3 + 0) * var7), (double)((float)(var4 + var6) * var8));
		var9.addVertexWithUV((double)(var1 + var5), (double)(var2 + var6), 0.0D, (double)((float)(var3 + var5) * var7), (double)((float)(var4 + var6) * var8));
		var9.addVertexWithUV((double)(var1 + var5), (double)(var2 + 0), 0.0D, (double)((float)(var3 + var5) * var7), (double)((float)(var4 + 0) * var8));
		var9.addVertexWithUV((double)(var1 + 0), (double)(var2 + 0), 0.0D, (double)((float)(var3 + 0) * var7), (double)((float)(var4 + 0) * var8));
		var9.draw();
	}
	
	public String getSaveDir() {
		return Minecraft.minecraftDir;
	}

	public ISaveFormat getSaveLoader() {
		return this.saveLoader;
	}

	public void displayGuiScreen(GuiScreen var1) {
		if(!(this.currentScreen instanceof GuiUnused)) {
			if(this.currentScreen != null) {
				this.currentScreen.onGuiClosed();
			}

			if(var1 instanceof GuiMainMenu) {
				this.statFileWriter.func_27175_b();
			}

			this.statFileWriter.syncStats();
			if(var1 == null && this.theWorld == null) {
				var1 = new GuiMainMenu();
			} else if(var1 == null && this.thePlayer.health <= 0) {
				var1 = new GuiGameOver();
			}

			if(var1 instanceof GuiMainMenu) {
				this.ingameGUI.clearChatMessages();
			}

			this.currentScreen = (GuiScreen)var1;
			if(var1 != null) {
				this.setIngameNotInFocus();
				ScaledResolution var2 = new ScaledResolution(this.gameSettings, this.displayWidth, this.displayHeight);
				int var3 = var2.getScaledWidth();
				int var4 = var2.getScaledHeight();
				((GuiScreen)var1).setWorldAndResolution(this, var3, var4);
				this.skipRenderWorld = false;
			} else {
				this.setIngameFocus();
			}

		}
	}

	private void checkGLError(String var1) {
		int var2 = GL11.glGetError();
		if(var2 != 0) {
			String var3 = GL11.gluErrorString(var2);
			System.out.println("########## GL ERROR ##########");
			System.out.println("@ " + var1);
			System.out.println(var2 + ": " + var3);
		}

	}

	public void shutdownMinecraftApplet() {
		try {
			this.statFileWriter.func_27175_b();
			this.statFileWriter.syncStats();

			if(doPrints) {
				System.out.println("Stopping!");
			}

			try {
				this.changeWorld1((World)null);
			} catch (Throwable var8) {
			}

			try {
				GLAllocation.deleteTexturesAndDisplayLists();
			} catch (Throwable var7) {
			}

			this.sndManager.closeMinecraft();
		} catch(Exception e) {
			
		}

		System.gc();
	}

	public void run() {
		this.running = true;

		this.startGame();

		try {
			long var1 = System.currentTimeMillis();
			int var3 = 0;

			while(this.running) {
				try {
					AxisAlignedBB.clearBoundingBoxPool();
					Vec3D.initialize();

					if(this.isGamePaused && this.theWorld != null) {
						float var4 = this.timer.renderPartialTicks;
						this.timer.updateTimer();
						this.timer.renderPartialTicks = var4;
					} else {
						this.timer.updateTimer();
					}

					long var23 = System.nanoTime();

					for(int var6 = 0; var6 < this.timer.elapsedTicks; ++var6) {
						++this.ticksRan;

						try {
							this.runTick();
						} catch (MinecraftException var16) {
							this.theWorld = null;
							this.changeWorld1((World)null);
							this.displayGuiScreen(new GuiConflictWarning());
						}
					}

					long var24 = System.nanoTime() - var23;
					this.checkGLError("Pre render");
					RenderBlocks.fancyGrass = this.gameSettings.fancyGraphics;
					this.sndManager.func_338_a(this.thePlayer, this.timer.renderPartialTicks);
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					if(this.theWorld != null) {
						this.theWorld.updatingLighting();
					}

					Display.update();

					if(this.thePlayer != null && this.thePlayer.isEntityInsideOpaqueBlock()) {
						this.gameSettings.thirdPersonView = false;
					}

					if(!this.skipRenderWorld) {
						if(this.playerController != null) {
							this.playerController.setPartialTime(this.timer.renderPartialTicks);
						}

						this.entityRenderer.updateCameraAndRender(this.timer.renderPartialTicks);
					}

					if(this.gameSettings.showDebugInfo) {
						this.displayDebugInfo(var24);
					} else {
						this.prevFrameTime = System.nanoTime();
					}

					this.guiAchievement.updateAchievementWindow();

					if(GL11.EaglerAdapterImpl2.getCanvasWidth() != this.displayWidth || GL11.EaglerAdapterImpl2.getCanvasHeight() != this.displayHeight) {
						this.displayWidth = GL11.EaglerAdapterImpl2.getCanvasWidth();
						this.displayHeight = GL11.EaglerAdapterImpl2.getCanvasHeight();
						if(this.displayWidth <= 0) {
							this.displayWidth = 1;
						}

						if(this.displayHeight <= 0) {
							this.displayHeight = 1;
						}

						this.resize(this.displayWidth, this.displayHeight);
					}

					this.checkGLError("Post render");
					GL11.optimize();
					var3++;

					for(this.isGamePaused = !this.isMultiplayerWorld() && this.currentScreen != null && this.currentScreen.doesGuiPauseGame(); System.currentTimeMillis() >= var1 + 1000L; var3 = 0) {
						this.debug = var3 + " FPS, " + WorldRenderer.chunksUpdated + " CU";
						WorldRenderer.chunksUpdated = 0;
						debugFPS = var3;
						var3 = 0;
						var1 += 1000L;
					}
				} catch (MinecraftException var18) {
					this.theWorld = null;
					this.changeWorld1((World)null);
					this.displayGuiScreen(new GuiConflictWarning());
				} catch (OutOfMemoryError var19) {
					this.func_28002_e();
					this.displayGuiScreen(new GuiErrorScreen());
					System.gc();
				}
			}
		} catch (MinecraftError var20) {
		}
	}

	public void func_28002_e() {
		try {
			field_28006_b = new byte[0];
			this.renderGlobal.func_28137_f();
		} catch (Throwable var4) {
		}

		try {
			System.gc();
			AxisAlignedBB.func_28196_a();
			Vec3D.func_28215_a();
		} catch (Throwable var3) {
		}

		try {
			System.gc();
			this.changeWorld1((World)null);
		} catch (Throwable var2) {
		}

		System.gc();
	}

	private void displayDebugInfo(long var1) {
		long var3 = 16666666L;
		if(this.prevFrameTime == -1L) {
			this.prevFrameTime = System.nanoTime();
		}

		long var5 = System.nanoTime();
		tickTimes[numRecordedFrameTimes & frameTimes.length - 1] = var1;
		frameTimes[numRecordedFrameTimes++ & frameTimes.length - 1] = var5 - this.prevFrameTime;
		this.prevFrameTime = var5;
		GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0D, (double)this.displayWidth, (double)this.displayHeight, 0.0D, 1000.0D, 3000.0D);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
		GL11.glLineWidth(1.0F);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Tessellator var7 = Tessellator.instance;
		var7.startDrawing(7);
		int var8 = (int)(var3 / 200000L);
		var7.setColorOpaque_I(536870912);
		var7.addVertex(0.0D, (double)(this.displayHeight - var8), 0.0D);
		var7.addVertex(0.0D, (double)this.displayHeight, 0.0D);
		var7.addVertex((double)frameTimes.length, (double)this.displayHeight, 0.0D);
		var7.addVertex((double)frameTimes.length, (double)(this.displayHeight - var8), 0.0D);
		var7.setColorOpaque_I(538968064);
		var7.addVertex(0.0D, (double)(this.displayHeight - var8 * 2), 0.0D);
		var7.addVertex(0.0D, (double)(this.displayHeight - var8), 0.0D);
		var7.addVertex((double)frameTimes.length, (double)(this.displayHeight - var8), 0.0D);
		var7.addVertex((double)frameTimes.length, (double)(this.displayHeight - var8 * 2), 0.0D);
		var7.draw();
		long var9 = 0L;

		int var11;
		for(var11 = 0; var11 < frameTimes.length; ++var11) {
			var9 += frameTimes[var11];
		}

		var11 = (int)(var9 / 200000L / (long)frameTimes.length);
		var7.startDrawing(7);
		var7.setColorOpaque_I(541065216);
		var7.addVertex(0.0D, (double)(this.displayHeight - var11), 0.0D);
		var7.addVertex(0.0D, (double)this.displayHeight, 0.0D);
		var7.addVertex((double)frameTimes.length, (double)this.displayHeight, 0.0D);
		var7.addVertex((double)frameTimes.length, (double)(this.displayHeight - var11), 0.0D);
		var7.draw();
		var7.startDrawing(1);

		for(int var12 = 0; var12 < frameTimes.length; ++var12) {
			int var13 = (var12 - numRecordedFrameTimes & frameTimes.length - 1) * 255 / frameTimes.length;
			int var14 = var13 * var13 / 255;
			var14 = var14 * var14 / 255;
			int var15 = var14 * var14 / 255;
			var15 = var15 * var15 / 255;
			if(frameTimes[var12] > var3) {
				var7.setColorOpaque_I(-16777216 + var14 * 65536);
			} else {
				var7.setColorOpaque_I(-16777216 + var14 * 256);
			}

			long var16 = frameTimes[var12] / 200000L;
			long var18 = tickTimes[var12] / 200000L;
			var7.addVertex((double)((float)var12 + 0.5F), (double)((float)((long)this.displayHeight - var16) + 0.5F), 0.0D);
			var7.addVertex((double)((float)var12 + 0.5F), (double)((float)this.displayHeight + 0.5F), 0.0D);
			var7.setColorOpaque_I(-16777216 + var14 * 65536 + var14 * 256 + var14 * 1);
			var7.addVertex((double)((float)var12 + 0.5F), (double)((float)((long)this.displayHeight - var16) + 0.5F), 0.0D);
			var7.addVertex((double)((float)var12 + 0.5F), (double)((float)((long)this.displayHeight - (var16 - var18)) + 0.5F), 0.0D);
		}

		var7.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	public void shutdown() {
		this.running = false;
		GL11.EaglerAdapterImpl2.exit();
	}

	public void setIngameFocus() {
		if(!this.inGameHasFocus) {
			this.inGameHasFocus = true;
			this.mouseHelper.grabMouseCursor();
			this.displayGuiScreen((GuiScreen)null);
			this.leftClickCounter = 10000;
			this.mouseTicksRan = this.ticksRan + 10000;
		}
	}

	public void setIngameNotInFocus() {
		if(this.inGameHasFocus) {
			if(this.thePlayer != null) {
				this.thePlayer.resetPlayerKeyState();
			}

			this.inGameHasFocus = false;
			this.mouseHelper.ungrabMouseCursor();
		}
	}

	public void displayInGameMenu() {
		if(this.currentScreen == null) {
			this.displayGuiScreen(new GuiIngameMenu());
		}
	}

	private void func_6254_a(int var1, boolean var2) {
		if(!this.playerController.field_1064_b) {
			if(!var2) {
				this.leftClickCounter = 0;
			}

			if(var1 != 0 || this.leftClickCounter <= 0) {
				if(var2 && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE && var1 == 0) {
					int var3 = this.objectMouseOver.blockX;
					int var4 = this.objectMouseOver.blockY;
					int var5 = this.objectMouseOver.blockZ;
					this.playerController.sendBlockRemoving(var3, var4, var5, this.objectMouseOver.sideHit);
					this.effectRenderer.addBlockHitEffects(var3, var4, var5, this.objectMouseOver.sideHit);
				} else {
					this.playerController.resetBlockRemoving();
				}

			}
		}
	}

	private void clickMouse(int var1) {
		if(this.thePlayer == null) {
			if(doPrints) {
				System.out.println("NO PLAYER FOUND, MOUSE FUNCTIONALITY WILL NOT WORK");
			}
			return;
		}

		if(var1 != 0 || this.leftClickCounter <= 0) {
			if(var1 == 0) {
				this.thePlayer.swingItem();
			}

			boolean var2 = true;
			if(this.objectMouseOver == null) {
				if(var1 == 0 && !(this.playerController instanceof PlayerControllerTest)) {
					this.leftClickCounter = 10;
				}
			} else if(this.objectMouseOver.typeOfHit == EnumMovingObjectType.ENTITY) {
				if(var1 == 0) {
					this.playerController.attackEntity(this.thePlayer, this.objectMouseOver.entityHit);
				}

				if(var1 == 1) {
					this.playerController.interactWithEntity(this.thePlayer, this.objectMouseOver.entityHit);
				}
			} else if(this.objectMouseOver.typeOfHit == EnumMovingObjectType.TILE) {
				int var3 = this.objectMouseOver.blockX;
				int var4 = this.objectMouseOver.blockY;
				int var5 = this.objectMouseOver.blockZ;
				int var6 = this.objectMouseOver.sideHit;
				if(var1 == 0) {
					this.playerController.clickBlock(var3, var4, var5, this.objectMouseOver.sideHit);
				} else {
					ItemStack var7 = this.thePlayer.inventory.getCurrentItem();
					int var8 = var7 != null ? var7.stackSize : 0;
					if(this.playerController.sendPlaceBlock(this.thePlayer, this.theWorld, var7, var3, var4, var5, var6)) {
						var2 = false;
						this.thePlayer.swingItem();
					}

					if(var7 == null) {
						return;
					}

					if(var7.stackSize == 0) {
						this.thePlayer.inventory.mainInventory[this.thePlayer.inventory.currentItem] = null;
					} else if(var7.stackSize != var8) {
						this.entityRenderer.itemRenderer.func_9449_b();
					}
				}
			}

			if(var2 && var1 == 1) {
				ItemStack var9 = this.thePlayer.inventory.getCurrentItem();
				if(var9 != null && this.playerController.sendUseItem(this.thePlayer, this.theWorld, var9)) {
					this.entityRenderer.itemRenderer.func_9450_c();
				}
			}

		}
	}

	private void resize(int var1, int var2) {
		if(var1 <= 0) {
			var1 = 1;
		}

		if(var2 <= 0) {
			var2 = 1;
		}

		this.displayWidth = var1;
		this.displayHeight = var2;
		if(this.currentScreen != null) {
			ScaledResolution var3 = new ScaledResolution(this.gameSettings, var1, var2);
			int var4 = var3.getScaledWidth();
			int var5 = var3.getScaledHeight();
			this.currentScreen.setWorldAndResolution(this, var4, var5);
		}

	}

	private void clickMiddleMouseButton() {
		if(this.objectMouseOver != null) {
			int var1 = this.theWorld.getBlockId(this.objectMouseOver.blockX, this.objectMouseOver.blockY, this.objectMouseOver.blockZ);
			if(var1 == Block.grass.blockID) {
				var1 = Block.dirt.blockID;
			}

			if(var1 == Block.stairDouble.blockID) {
				var1 = Block.stairSingle.blockID;
			}

			if(var1 == Block.bedrock.blockID) {
				var1 = Block.stone.blockID;
			}

			if(this.thePlayer == null) {
				
				System.out.println("NO PLAYER FOUND");
				return;
			}

			this.thePlayer.inventory.setCurrentItem(var1, this.playerController instanceof PlayerControllerTest);
		}

	}

	private void func_28001_B() {
		//Fuck Mojang L :P
	}

	public void runTick() {
		if(this.ticksRan == 6000) {
			this.func_28001_B();
		}

		this.statFileWriter.func_27178_d();
		this.ingameGUI.updateTick();
		this.entityRenderer.getMouseOver(1.0F);
		int var3;
		if(this.thePlayer != null) {
			IChunkProvider var1 = this.theWorld.getIChunkProvider();
			if(var1 instanceof ChunkProviderLoadOrGenerate) {
				ChunkProviderLoadOrGenerate var2 = (ChunkProviderLoadOrGenerate)var1;
				var3 = MathHelper.floor_float((float)((int)this.thePlayer.posX)) >> 4;
				int var4 = MathHelper.floor_float((float)((int)this.thePlayer.posZ)) >> 4;
				var2.setCurrentChunkOver(var3, var4);
			}
		}

		if(!this.isGamePaused && this.theWorld != null) {
			this.playerController.updateController();
		}

		terrainTexture.bindTexture();
		if(!this.isGamePaused && this.renderEngine != null) {
			this.renderEngine.updateDynamicTextures();
		}

		if(!this.hasRefreshed && this.renderEngine != null) {
			this.renderEngine.refreshTextures();
			this.hasRefreshed = true;
		}

		if(this.currentScreen == null && this.thePlayer != null) {
			if(this.thePlayer.health <= 0) {
				this.displayGuiScreen((GuiScreen)null);
			} else if(this.thePlayer.isPlayerSleeping() && this.theWorld != null && this.theWorld.multiplayerWorld) {
				this.displayGuiScreen(new GuiSleepMP());
			}
		} else if(this.currentScreen != null && this.currentScreen instanceof GuiSleepMP && !this.thePlayer.isPlayerSleeping()) {
			this.displayGuiScreen((GuiScreen)null);
		}

		if(this.currentScreen != null) {
			this.leftClickCounter = 10000;
			this.mouseTicksRan = this.ticksRan + 10000;
		}

		if(this.currentScreen != null) {
			this.currentScreen.handleInput();
			if(this.currentScreen != null) {
				this.currentScreen.field_25091_h.func_25088_a();
				this.currentScreen.updateScreen();
			}
		}

		if(this.currentScreen == null || this.currentScreen.field_948_f) {
			label301:
			while(true) {
				while(true) {
					while(true) {
						long var5;
						do {
							if(!Mouse.next()) {
								if(this.leftClickCounter > 0) {
									--this.leftClickCounter;
								}

								while(true) {
									while(true) {
										do {
											if(!Keyboard.next()) {
												if(this.currentScreen == null) {
													if(Mouse.isButtonDown(0) && (float)(this.ticksRan - this.mouseTicksRan) >= this.timer.ticksPerSecond / 4.0F && this.inGameHasFocus) {
														this.clickMouse(0);
														this.mouseTicksRan = this.ticksRan;
													}

													if(Mouse.isButtonDown(1) && (float)(this.ticksRan - this.mouseTicksRan) >= this.timer.ticksPerSecond / 4.0F && this.inGameHasFocus) {
														this.clickMouse(1);
														this.mouseTicksRan = this.ticksRan;
													}
												}

												this.func_6254_a(0, this.currentScreen == null && Mouse.isButtonDown(0) && this.inGameHasFocus);
												break label301;
											}

											this.thePlayer.handleKeyPress(Keyboard.getEventKey(), Keyboard.getEventKeyState());
										} while(!Keyboard.getEventKeyState());

										if(this.currentScreen != null) {
											this.currentScreen.handleKeyboardInput();
										} else {
											if(Keyboard.getEventKey() == 1) {
												this.displayInGameMenu();
											}
											
											if(Keyboard.isKeyDown(this.gameSettings.keyBindToggleFog.keyCode) && Keyboard.getEventKey() == 2) {
												this.gameSettings.hideGUI = !this.gameSettings.hideGUI;
											}
											
											if(Keyboard.isKeyDown(this.gameSettings.keyBindToggleFog.keyCode) && Keyboard.getEventKey() == 3) {
												this.ingameGUI.addChatMessage(ScreenShotHelper.saveScreenshot());
											}
											
											if(Keyboard.isKeyDown(this.gameSettings.keyBindToggleFog.keyCode) && Keyboard.getEventKey() == 4) {
												this.gameSettings.showDebugInfo = !this.gameSettings.showDebugInfo;
											}
											
											if(Keyboard.isKeyDown(this.gameSettings.keyBindToggleFog.keyCode) && Keyboard.getEventKey() == 6) {
												this.gameSettings.thirdPersonView = !this.gameSettings.thirdPersonView;
											}
											
											if(Keyboard.isKeyDown(this.gameSettings.keyBindToggleFog.keyCode) && Keyboard.getEventKey() == 9) {
												this.gameSettings.smoothCamera = !this.gameSettings.smoothCamera;
											}

											if(Keyboard.getEventKey() == this.gameSettings.keyBindInventory.keyCode) {
												this.displayGuiScreen(new GuiInventory(this.thePlayer));
											}

											if(Keyboard.getEventKey() == this.gameSettings.keyBindDrop.keyCode) {
												this.thePlayer.dropCurrentItem();
											}

											if(Keyboard.getEventKey() == this.gameSettings.keyBindChat.keyCode) {
												this.displayGuiScreen(new GuiChat());
											}
										}

										for(int var6 = 0; var6 < 9; ++var6) {
											if(Keyboard.getEventKey() == 2 + var6 && !Keyboard.isKeyDown(this.gameSettings.keyBindToggleFog.keyCode)) {
												this.thePlayer.inventory.currentItem = var6;
											}
										}

										//rip
										//if(Keyboard.getEventKey() == this.gameSettings.keyBindToggleFog.keyCode) {
											//this.gameSettings.setOptionValue(EnumOptions.RENDER_DISTANCE, !Keyboard.isKeyDown(42) && !Keyboard.isKeyDown(54) ? 1 : -1);
										//}
									}
								}
							}

							var5 = System.currentTimeMillis() - this.systemTime;
						} while(var5 > 200L);

						var3 = Mouse.getEventDWheel();
						if(var3 != 0) {
							this.thePlayer.inventory.changeCurrentItem(var3);
							if(this.gameSettings.field_22275_C) {
								if(var3 > 0) {
									var3 = 1;
								}

								if(var3 < 0) {
									var3 = -1;
								}

								this.gameSettings.field_22272_F += (float)var3 * 0.25F;
							}
						}

						if(this.currentScreen == null) {
							if(!this.inGameHasFocus && Mouse.getEventButtonState()) {
								this.setIngameFocus();
							} else {
								if(Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
									this.clickMouse(0);
									this.mouseTicksRan = this.ticksRan;
								}

								if(Mouse.getEventButton() == 1 && Mouse.getEventButtonState()) {
									this.clickMouse(1);
									this.mouseTicksRan = this.ticksRan;
								}

								if(Mouse.getEventButton() == 2 && Mouse.getEventButtonState()) {
									this.clickMiddleMouseButton();
								}
							}
						} else if(this.currentScreen != null) {
							this.currentScreen.handleMouseInput();
						}
					}
				}
			}
		}

		if(this.theWorld != null) {
			if(this.thePlayer != null) {
				++this.joinPlayerCounter;
				if(this.joinPlayerCounter == 30) {
					this.joinPlayerCounter = 0;
					this.theWorld.joinEntityInSurroundings(this.thePlayer);
				}
			}

			this.theWorld.difficultySetting = this.gameSettings.difficulty;
			if(this.theWorld.multiplayerWorld) {
				this.theWorld.difficultySetting = 3;
			}

			if(!this.isGamePaused) {
				this.entityRenderer.updateRenderer();
			}

			if(!this.isGamePaused) {
				this.renderGlobal.updateClouds();
			}

			if(!this.isGamePaused && this.theWorld != null) {
				if(this.theWorld.field_27172_i > 0) {
					--this.theWorld.field_27172_i;
				}

				this.theWorld.updateEntities();
			}

			if(!this.isGamePaused || this.isMultiplayerWorld()) {
				this.theWorld.setAllowedMobSpawns(this.gameSettings.difficulty > 0, true);
				this.theWorld.tick();
			}

			if(!this.isGamePaused && this.theWorld != null) {
				this.theWorld.randomDisplayUpdates(MathHelper.floor_double(this.thePlayer.posX), MathHelper.floor_double(this.thePlayer.posY), MathHelper.floor_double(this.thePlayer.posZ));
			}

			if(!this.isGamePaused && this.effectRenderer != null) {
				this.effectRenderer.updateEffects();
			}
		}

		//if(this.systemTime) {
		this.systemTime = System.currentTimeMillis();
		//}
	}

	private void forceReload() {
		if(doPrints) {
			System.out.println("FORCING RELOAD!");
		}
		this.sndManager = new SoundManager();
		this.sndManager.loadSoundSettings(this.gameSettings);
	}

	public boolean isMultiplayerWorld() {
		return this.theWorld != null && this.theWorld.multiplayerWorld;
	}

	public void startWorld(String var1, String var2, long var3) {
		this.changeWorld1((World)null);
		System.gc();
		if(this.saveLoader.isOldMapFormat(var1)) {
			this.convertMapFormat(var1, var2);
		} else {
			ISaveHandler var5 = this.saveLoader.getSaveLoader(var1, false);
			World var6 = null;
			var6 = new World(var5, var2, var3);
			if(var6.isNewWorld) {
				this.statFileWriter.readStat(StatList.createWorldStat, 1);
				this.statFileWriter.readStat(StatList.startGameStat, 1);
				this.changeWorld2(var6, "Generating level");
			} else {
				this.statFileWriter.readStat(StatList.loadWorldStat, 1);
				this.statFileWriter.readStat(StatList.startGameStat, 1);
				this.changeWorld2(var6, "Loading level");
			}
		}

	}

	public void usePortal() {
		if(doPrints) {
			System.out.println("Toggling dimension!!");
		}

		if(this.thePlayer == null) {
			System.out.println("NO PLAYER FOUND");
			return;
		}

		if(this.thePlayer.dimension == -1) {
			this.thePlayer.dimension = 0;
		} else {
			this.thePlayer.dimension = -1;
		}

		this.theWorld.setEntityDead(this.thePlayer);
		this.thePlayer.isDead = false;
		double var1 = this.thePlayer.posX;
		double var3 = this.thePlayer.posZ;
		double var5 = 8.0D;
		World var7;
		if(this.thePlayer.dimension == -1) {
			var1 /= var5;
			var3 /= var5;
			this.thePlayer.setLocationAndAngles(var1, this.thePlayer.posY, var3, this.thePlayer.rotationYaw, this.thePlayer.rotationPitch);
			if(this.thePlayer.isEntityAlive()) {
				this.theWorld.updateEntityWithOptionalForce(this.thePlayer, false);
			}

			var7 = null;
			var7 = new World(this.theWorld, WorldProvider.getProviderForDimension(-1));
			this.changeWorld(var7, "Entering the Nether", this.thePlayer);
		} else {
			var1 *= var5;
			var3 *= var5;
			this.thePlayer.setLocationAndAngles(var1, this.thePlayer.posY, var3, this.thePlayer.rotationYaw, this.thePlayer.rotationPitch);
			if(this.thePlayer.isEntityAlive()) {
				this.theWorld.updateEntityWithOptionalForce(this.thePlayer, false);
			}

			var7 = null;
			var7 = new World(this.theWorld, WorldProvider.getProviderForDimension(0));
			this.changeWorld(var7, "Leaving the Nether", this.thePlayer);
		}

		this.thePlayer.worldObj = this.theWorld;
		if(this.thePlayer.isEntityAlive()) {
			this.thePlayer.setLocationAndAngles(var1, this.thePlayer.posY, var3, this.thePlayer.rotationYaw, this.thePlayer.rotationPitch);
			this.theWorld.updateEntityWithOptionalForce(this.thePlayer, false);
			(new Teleporter()).func_4107_a(this.theWorld, this.thePlayer);
		}

	}

	public void changeWorld1(World var1) {
		this.changeWorld2(var1, "");
	}

	public void changeWorld2(World var1, String var2) {
		this.changeWorld(var1, var2, (EntityPlayer)null);
	}

	public void changeWorld(World var1, String var2, EntityPlayer var3) {
		this.statFileWriter.func_27175_b();
		this.statFileWriter.syncStats();
		this.renderViewEntity = null;
		this.loadingScreen.printText(var2);
		this.loadingScreen.displayLoadingString("");
		if(this.sndManager != null) {
			this.sndManager.playStreaming((String)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
		}
		if(this.theWorld != null) {
			this.theWorld.saveWorldIndirectly(this.loadingScreen);
		}

		this.theWorld = var1;
		if(var1 != null) {
			this.playerController.func_717_a(var1);
			if(!this.isMultiplayerWorld()) {
				if(var3 == null) {
					this.thePlayer = (EntityPlayerSP)var1.func_4085_a(EntityPlayerSP.class);
				}
			} else if(this.thePlayer != null) {
				this.thePlayer.preparePlayerToSpawn();
				if(var1 != null) {
					var1.entityJoinedWorld(this.thePlayer);
				}
			}

			if(!var1.multiplayerWorld) {
				this.func_6255_d(var2);
			}

			if(this.thePlayer == null) {
				this.thePlayer = (EntityPlayerSP)this.playerController.createPlayer(var1);
				this.thePlayer.preparePlayerToSpawn();
				this.playerController.flipPlayer(this.thePlayer);
			}

			this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
			if(this.renderGlobal != null) {
				this.renderGlobal.changeWorld(var1);
			}

			if(this.effectRenderer != null) {
				this.effectRenderer.clearEffects(var1);
			}

			this.playerController.func_6473_b(this.thePlayer);
			if(var3 != null) {
				var1.emptyMethod1();
			}

			IChunkProvider var4 = var1.getIChunkProvider();
			if(var4 instanceof ChunkProviderLoadOrGenerate) {
				ChunkProviderLoadOrGenerate var5 = (ChunkProviderLoadOrGenerate)var4;
				int var6 = MathHelper.floor_float((float)((int)this.thePlayer.posX)) >> 4;
				int var7 = MathHelper.floor_float((float)((int)this.thePlayer.posZ)) >> 4;
				var5.setCurrentChunkOver(var6, var7);
			}

			var1.spawnPlayerWithLoadedChunks(this.thePlayer);
			if(var1.isNewWorld) {
				var1.saveWorldIndirectly(this.loadingScreen);
			}

			this.renderViewEntity = this.thePlayer;
		} else {
			this.thePlayer = null;
		}

		System.gc();
		this.systemTime = 0L;
	}

	private void convertMapFormat(String var1, String var2) {
		this.loadingScreen.printText("Converting World to " + this.saveLoader.func_22178_a());
		this.loadingScreen.displayLoadingString("This may take a while :3");
		this.saveLoader.convertMapFormat(var1, this.loadingScreen);
		this.startWorld(var1, var2, 0L);
	}

	private void func_6255_d(String var1) {
		this.loadingScreen.printText(var1);
		this.loadingScreen.displayLoadingString("Building terrain");
		short var2 = 128;
		int var3 = 0;
		int var4 = var2 * 2 / 16 + 1;
		var4 *= var4;
		IChunkProvider var5 = this.theWorld.getIChunkProvider();
		ChunkCoordinates var6 = this.theWorld.getSpawnPoint();
		if(this.thePlayer != null) {
			var6.x = (int)this.thePlayer.posX;
			var6.z = (int)this.thePlayer.posZ;
		}

		if(var5 instanceof ChunkProviderLoadOrGenerate) {
			ChunkProviderLoadOrGenerate var7 = (ChunkProviderLoadOrGenerate)var5;
			var7.setCurrentChunkOver(var6.x >> 4, var6.z >> 4);
		}

		for(int var10 = -var2; var10 <= var2; var10 += 16) {
			for(int var8 = -var2; var8 <= var2; var8 += 16) {
				this.loadingScreen.setLoadingProgress(var3++ * 100 / var4);
				this.theWorld.getBlockId(var6.x + var10, 64, var6.z + var8);
			}
		}

		this.loadingScreen.displayLoadingString("Simulating world for a bit");
		boolean var9 = true;
		this.theWorld.func_656_j();
	}

	public String func_6241_m() {
		return this.renderGlobal.getDebugInfoRenders();
	}

	public String func_6262_n() {
		return this.renderGlobal.getDebugInfoEntities();
	}

	public String func_21002_o() {
		return this.theWorld.func_21119_g();
	}

	public String func_6245_o() {
		return "P: " + this.effectRenderer.getStatistics() + ". T: " + this.theWorld.func_687_d();
	}

	public void respawn(boolean var1, int var2) {
		if(!this.theWorld.multiplayerWorld && !this.theWorld.worldProvider.canRespawnHere()) {
			this.usePortal();
		}

		ChunkCoordinates var3 = null;
		ChunkCoordinates var4 = null;
		boolean var5 = true;
		if(this.thePlayer != null && !var1) {
			var3 = this.thePlayer.getPlayerSpawnCoordinate();
			if(var3 != null) {
				var4 = EntityPlayer.func_25060_a(this.theWorld, var3);
				if(var4 == null) {
					this.thePlayer.addChatMessage("tile.bed.notValid");
				}
			}
		}

		if(var4 == null) {
			var4 = this.theWorld.getSpawnPoint();
			var5 = false;
		}

		IChunkProvider var6 = this.theWorld.getIChunkProvider();
		if(var6 instanceof ChunkProviderLoadOrGenerate) {
			ChunkProviderLoadOrGenerate var7 = (ChunkProviderLoadOrGenerate)var6;
			var7.setCurrentChunkOver(var4.x >> 4, var4.z >> 4);
		}

		this.theWorld.setSpawnLocation();
		this.theWorld.updateEntityList();
		int var8 = 0;
		if(this.thePlayer != null) {
			var8 = this.thePlayer.entityId;
			this.theWorld.setEntityDead(this.thePlayer);
		}

		this.renderViewEntity = null;
		this.thePlayer = (EntityPlayerSP)this.playerController.createPlayer(this.theWorld);
		this.thePlayer.dimension = var2;
		this.renderViewEntity = this.thePlayer;
		this.thePlayer.preparePlayerToSpawn();
		if(var5) {
			this.thePlayer.setPlayerSpawnCoordinate(var3);
			this.thePlayer.setLocationAndAngles((double)((float)var4.x + 0.5F), (double)((float)var4.y + 0.1F), (double)((float)var4.z + 0.5F), 0.0F, 0.0F);
		}

		this.playerController.flipPlayer(this.thePlayer);
		this.theWorld.spawnPlayerWithLoadedChunks(this.thePlayer);
		this.thePlayer.movementInput = new MovementInputFromOptions(this.gameSettings);
		this.thePlayer.entityId = var8;
		this.thePlayer.func_6420_o();
		this.playerController.func_6473_b(this.thePlayer);
		this.func_6255_d("Respawning");
		if(this.currentScreen instanceof GuiGameOver) {
			this.displayGuiScreen((GuiScreen)null);
		}

	}

	public NetClientHandler getSendQueue() {
		return this.thePlayer instanceof EntityClientPlayerMP ? ((EntityClientPlayerMP)this.thePlayer).sendQueue : null;
	}

	public static boolean isGuiEnabled() {
		return theMinecraft == null || !theMinecraft.gameSettings.hideGUI;
	}

	public static boolean isFancyGraphicsEnabled() {
		return theMinecraft != null && theMinecraft.gameSettings.fancyGraphics;
	}

	public static boolean isAmbientOcclusionEnabled() {
		return theMinecraft != null && theMinecraft.gameSettings.ambientOcclusion;
	}

	public static boolean isDebugInfoEnabled() {
		return theMinecraft != null && theMinecraft.gameSettings.showDebugInfo;
	}

	public boolean lineIsCommand(String var1) {
		if(var1.startsWith("/")) {
			return true;
		}

		return false;
	}
	
	public static Minecraft getMinecraft() {
		return theMinecraft;
	}
	
	public void displayChat(String s) {
		this.ingameGUI.addChatMessage(s);
	}

	public void displayErrorChat(String s) {
		this.ingameGUI.addChatMessage(FontRenderer.formatChar + "c" + s);
	}
}
