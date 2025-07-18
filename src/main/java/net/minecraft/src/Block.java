package net.minecraft.src;

import java.util.ArrayList;

public class Block {
	public static final StepSound soundPowderFootstep = new StepSound("stone", 1.0F, 1.0F);
	public static final StepSound soundWoodFootstep = new StepSound("wood", 1.0F, 1.0F);
	public static final StepSound soundGravelFootstep = new StepSound("gravel", 1.0F, 1.0F);
	public static final StepSound soundGrassFootstep = new StepSound("grass", 1.0F, 1.0F);
	public static final StepSound soundStoneFootstep = new StepSound("stone", 1.0F, 1.0F);
	public static final StepSound soundMetalFootstep = new StepSound("stone", 1.0F, 1.5F);
	public static final StepSound soundGlassFootstep = new StepSoundStone("stone", 1.0F, 1.0F);
	public static final StepSound soundClothFootstep = new StepSound("cloth", 1.0F, 1.0F);
	public static final StepSound soundSandFootstep = new StepSoundSand("sand", 1.0F, 1.0F);
	public static final Block[] blocksList = new Block[256];
	public static final boolean[] tickOnLoad = new boolean[256];
	public static final boolean[] opaqueCubeLookup = new boolean[256];
	public static final boolean[] isBlockContainer = new boolean[256];
	public static final int[] lightOpacity = new int[256];
	public static final boolean[] canBlockGrass = new boolean[256];
	public static final int[] lightValue = new int[256];
	public static final boolean[] field_28032_t = new boolean[256];
	public static final Block stone = (new BlockStone(1, 1)).setHardness(1.5F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("stone");
	public static final BlockGrass grass = (BlockGrass)(new BlockGrass(2)).setHardness(0.6F).setStepSound(soundGrassFootstep).setBlockName("grass");
	public static final Block dirt = (new BlockDirt(3, 2)).setHardness(0.5F).setStepSound(soundGravelFootstep).setBlockName("dirt");
	public static final Block cobblestone = (new Block(4, 16, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("stonebrick");
	public static final Block planks = (new Block(5, 4, Material.wood)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep).setBlockName("wood").disableNeighborNotifyOnMetadataChange();
	public static final Block sapling = (new BlockSapling(6, 15)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("sapling").disableNeighborNotifyOnMetadataChange();
	public static final Block bedrock = (new Block(7, 17, Material.rock)).setBlockUnbreakable().setResistance(6000000.0F).setStepSound(soundStoneFootstep).setBlockName("bedrock").disableStats();
	public static final Block waterMoving = (new BlockFlowing(8, Material.water)).setHardness(100.0F).setLightOpacity(3).setBlockName("water").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block waterStill = (new BlockStationary(9, Material.water)).setHardness(100.0F).setLightOpacity(3).setBlockName("water").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block lavaMoving = (new BlockFlowing(10, Material.lava)).setHardness(0.0F).setLightValue(1.0F).setLightOpacity(255).setBlockName("lava").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block lavaStill = (new BlockStationary(11, Material.lava)).setHardness(100.0F).setLightValue(1.0F).setLightOpacity(255).setBlockName("lava").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block sand = (new BlockSand(12, 18)).setHardness(0.5F).setStepSound(soundSandFootstep).setBlockName("sand");
	public static final Block gravel = (new BlockGravel(13, 19)).setHardness(0.6F).setStepSound(soundGravelFootstep).setBlockName("gravel");
	public static final Block oreGold = (new BlockOre(14, 32)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setBlockName("oreGold");
	public static final Block oreIron = (new BlockOre(15, 33)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setBlockName("oreIron");
	public static final Block oreCoal = (new BlockOre(16, 34)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setBlockName("oreCoal");
	public static final Block wood = (new BlockLog(17)).setHardness(2.0F).setStepSound(soundWoodFootstep).setBlockName("log").disableNeighborNotifyOnMetadataChange();
	public static final BlockLeaves leaves = (BlockLeaves)(new BlockLeaves(18, 52)).setHardness(0.2F).setLightOpacity(1).setStepSound(soundGrassFootstep).setBlockName("leaves").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block sponge = (new BlockSponge(19)).setHardness(0.6F).setStepSound(soundGrassFootstep).setBlockName("sponge");
	public static final Block glass = (new BlockGlass(20, 49, Material.glass, false)).setHardness(0.3F).setStepSound(soundGlassFootstep).setBlockName("glass");
	public static final Block oreLapis = (new BlockOre(21, 160)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setBlockName("oreLapis");
	public static final Block blockLapis = (new Block(22, 144, Material.rock)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setBlockName("blockLapis");
	public static final Block dispenser = (new BlockDispenser(23)).setHardness(3.5F).setStepSound(soundStoneFootstep).setBlockName("dispenser").disableNeighborNotifyOnMetadataChange();
	public static final Block sandStone = (new BlockSandStone(24)).setStepSound(soundStoneFootstep).setHardness(0.8F).setBlockName("sandStone");
	public static final Block musicBlock = (new BlockNote(25)).setHardness(0.8F).setBlockName("musicBlock").disableNeighborNotifyOnMetadataChange();
	public static final Block blockBed = (new BlockBed(26)).setHardness(0.2F).setBlockName("bed").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block railPowered = (new BlockRail(27, 179, true)).setHardness(0.7F).setStepSound(soundMetalFootstep).setBlockName("goldenRail").disableNeighborNotifyOnMetadataChange();
	public static final Block railDetector = (new BlockDetectorRail(28, 195)).setHardness(0.7F).setStepSound(soundMetalFootstep).setBlockName("detectorRail").disableNeighborNotifyOnMetadataChange();
	public static final Block pistonStickyBase = (new BlockPistonBase(29, 106, true)).setBlockName("pistonStickyBase").disableNeighborNotifyOnMetadataChange();
	public static final Block web = (new BlockWeb(30, 11)).setLightOpacity(1).setHardness(4.0F).setBlockName("web");
	public static final BlockTallGrass tallGrass = (BlockTallGrass)(new BlockTallGrass(31, 39)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("tallgrass");
	public static final BlockDeadBush deadBush = (BlockDeadBush)(new BlockDeadBush(32, 55)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("deadbush");
	public static final Block pistonBase = (new BlockPistonBase(33, 107, false)).setBlockName("pistonBase").disableNeighborNotifyOnMetadataChange();
	public static final BlockPistonExtension pistonExtension = (BlockPistonExtension)(new BlockPistonExtension(34, 107)).disableNeighborNotifyOnMetadataChange();
	public static final Block cloth = (new BlockCloth()).setHardness(0.8F).setStepSound(soundClothFootstep).setBlockName("cloth").disableNeighborNotifyOnMetadataChange();
	public static final BlockPistonMoving pistonMoving = new BlockPistonMoving(36);
	public static final BlockFlower plantYellow = (BlockFlower)(new BlockFlower(37, 13)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("flower");
	public static final BlockFlower plantRed = (BlockFlower)(new BlockFlower(38, 12)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("rose");
	public static final BlockFlower mushroomBrown = (BlockFlower)(new BlockMushroom(39, 29)).setHardness(0.0F).setStepSound(soundGrassFootstep).setLightValue(2.0F / 16.0F).setBlockName("mushroomBrown");
	public static final BlockFlower mushroomRed = (BlockFlower)(new BlockMushroom(40, 28)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("mushroomRed");
	public static final Block blockGold = (new BlockOreStorage(41, 23)).setHardness(3.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setBlockName("blockGold");
	public static final Block blockSteel = (new BlockOreStorage(42, 22)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setBlockName("blockIron");
	public static final Block stairDouble = (new BlockStep(43, true)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("stoneSlab");
	public static final Block stairSingle = (new BlockStep(44, false)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("stoneSlab");
	public static final Block brick = (new Block(45, 7, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("brick");
	public static final Block tnt = (new BlockTNT(46, 156)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("tnt");
	public static final Block bookShelf = (new BlockBookshelf(47, 35)).setHardness(1.5F).setStepSound(soundWoodFootstep).setBlockName("bookshelf");
	public static final Block cobblestoneMossy = (new Block(48, 36, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("stoneMoss");
	public static final Block obsidian = (new BlockObsidian(49, 37)).setHardness(10.0F).setResistance(2000.0F).setStepSound(soundGlassFootstep).setBlockName("obsidian");
	public static final Block torchWood = (new BlockTorch(50, 80)).setHardness(0.0F).setLightValue(15.0F / 16.0F).setStepSound(soundWoodFootstep).setBlockName("torch").disableNeighborNotifyOnMetadataChange();
	public static final BlockFire fire = (BlockFire)(new BlockFire(51, 31)).setHardness(0.0F).setLightValue(1.0F).setStepSound(soundWoodFootstep).setBlockName("fire").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block mobSpawner = (new BlockMobSpawner(52, 65)).setHardness(5.0F).setStepSound(soundMetalFootstep).setBlockName("mobSpawner").disableStats();
	public static final Block stairCompactPlanks = (new BlockStairs(53, planks)).setBlockName("stairsWood").disableNeighborNotifyOnMetadataChange();
	public static final Block chest = (new BlockChest(54)).setHardness(2.5F).setStepSound(soundWoodFootstep).setBlockName("chest").disableNeighborNotifyOnMetadataChange();
	public static final Block redstoneWire = (new BlockRedstoneWire(55, 164)).setHardness(0.0F).setStepSound(soundPowderFootstep).setBlockName("redstoneDust").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block oreDiamond = (new BlockOre(56, 50)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setBlockName("oreDiamond");
	public static final Block blockDiamond = (new BlockOreStorage(57, 24)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setBlockName("blockDiamond");
	public static final Block workbench = (new BlockWorkbench(58)).setHardness(2.5F).setStepSound(soundWoodFootstep).setBlockName("workbench");
	public static final Block crops = (new BlockCrops(59, 88)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("crops").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block tilledField = (new BlockFarmland(60)).setHardness(0.6F).setStepSound(soundGravelFootstep).setBlockName("farmland");
	public static final Block stoneOvenIdle = (new BlockFurnace(61, false)).setHardness(3.5F).setStepSound(soundStoneFootstep).setBlockName("furnace").disableNeighborNotifyOnMetadataChange();
	public static final Block stoneOvenActive = (new BlockFurnace(62, true)).setHardness(3.5F).setStepSound(soundStoneFootstep).setLightValue(14.0F / 16.0F).setBlockName("furnace").disableNeighborNotifyOnMetadataChange();
	public static final Block signPost = (new BlockSign(63, TileEntitySign.class, true)).setHardness(1.0F).setStepSound(soundWoodFootstep).setBlockName("sign").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block doorWood = (new BlockDoor(64, Material.wood)).setHardness(3.0F).setStepSound(soundWoodFootstep).setBlockName("doorWood").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block ladder = (new BlockLadder(65, 83)).setHardness(0.4F).setStepSound(soundWoodFootstep).setBlockName("ladder").disableNeighborNotifyOnMetadataChange();
	public static final Block rail = (new BlockRail(66, 128, false)).setHardness(0.7F).setStepSound(soundMetalFootstep).setBlockName("rail").disableNeighborNotifyOnMetadataChange();
	public static final Block stairCompactCobblestone = (new BlockStairs(67, cobblestone)).setBlockName("stairsStone").disableNeighborNotifyOnMetadataChange();
	public static final Block signWall = (new BlockSign(68, TileEntitySign.class, false)).setHardness(1.0F).setStepSound(soundWoodFootstep).setBlockName("sign").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block lever = (new BlockLever(69, 96)).setHardness(0.5F).setStepSound(soundWoodFootstep).setBlockName("lever").disableNeighborNotifyOnMetadataChange();
	public static final Block pressurePlateStone = (new BlockPressurePlate(70, stone.blockIndexInTexture, EnumMobType.mobs, Material.rock)).setHardness(0.5F).setStepSound(soundStoneFootstep).setBlockName("pressurePlate").disableNeighborNotifyOnMetadataChange();
	public static final Block doorSteel = (new BlockDoor(71, Material.iron)).setHardness(5.0F).setStepSound(soundMetalFootstep).setBlockName("doorIron").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block pressurePlatePlanks = (new BlockPressurePlate(72, planks.blockIndexInTexture, EnumMobType.everything, Material.wood)).setHardness(0.5F).setStepSound(soundWoodFootstep).setBlockName("pressurePlate").disableNeighborNotifyOnMetadataChange();
	public static final Block oreRedstone = (new BlockRedstoneOre(73, 51, false)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setBlockName("oreRedstone").disableNeighborNotifyOnMetadataChange();
	public static final Block oreRedstoneGlowing = (new BlockRedstoneOre(74, 51, true)).setLightValue(10.0F / 16.0F).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setBlockName("oreRedstone").disableNeighborNotifyOnMetadataChange();
	public static final Block torchRedstoneIdle = (new BlockRedstoneTorch(75, 115, false)).setHardness(0.0F).setStepSound(soundWoodFootstep).setBlockName("notGate").disableNeighborNotifyOnMetadataChange();
	public static final Block torchRedstoneActive = (new BlockRedstoneTorch(76, 99, true)).setHardness(0.0F).setLightValue(0.5F).setStepSound(soundWoodFootstep).setBlockName("notGate").disableNeighborNotifyOnMetadataChange();
	public static final Block button = (new BlockButton(77, stone.blockIndexInTexture)).setHardness(0.5F).setStepSound(soundStoneFootstep).setBlockName("button").disableNeighborNotifyOnMetadataChange();
	public static final Block snow = (new BlockSnow(78, 66)).setHardness(0.1F).setStepSound(soundClothFootstep).setBlockName("snow");
	public static final Block ice = (new BlockIce(79, 67)).setHardness(0.5F).setLightOpacity(3).setStepSound(soundGlassFootstep).setBlockName("ice");
	public static final Block blockSnow = (new BlockSnowBlock(80, 66)).setHardness(0.2F).setStepSound(soundClothFootstep).setBlockName("snow");
	public static final Block cactus = (new BlockCactus(81, 70)).setHardness(0.4F).setStepSound(soundClothFootstep).setBlockName("cactus");
	public static final Block blockClay = (new BlockClay(82, 72)).setHardness(0.6F).setStepSound(soundGravelFootstep).setBlockName("clay");
	public static final Block reed = (new BlockReed(83, 73)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("reeds").disableStats();
	public static final Block jukebox = (new BlockJukeBox(84, 74)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("jukebox").disableNeighborNotifyOnMetadataChange();
	public static final Block fence = (new BlockFence(85, 4)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep).setBlockName("fence").disableNeighborNotifyOnMetadataChange();
	public static final Block pumpkin = (new BlockPumpkin(86, 102, false)).setHardness(1.0F).setStepSound(soundWoodFootstep).setBlockName("pumpkin").disableNeighborNotifyOnMetadataChange();
	public static final Block netherrack = (new BlockNetherrack(87, 103)).setHardness(0.4F).setStepSound(soundStoneFootstep).setBlockName("hellrock");
	public static final Block slowSand = (new BlockSoulSand(88, 104)).setHardness(0.5F).setStepSound(soundSandFootstep).setBlockName("hellsand");
	public static final Block glowStone = (new BlockGlowStone(89, 105, Material.rock)).setHardness(0.3F).setStepSound(soundGlassFootstep).setLightValue(1.0F).setBlockName("lightgem");
	public static final BlockPortal portal = (BlockPortal)(new BlockPortal(90, 14)).setHardness(-1.0F).setStepSound(soundGlassFootstep).setLightValue(12.0F / 16.0F).setBlockName("portal").disableStats();
	public static final Block pumpkinLantern = (new BlockPumpkin(91, 102, true)).setHardness(1.0F).setStepSound(soundWoodFootstep).setLightValue(1.0F).setBlockName("litpumpkin").disableNeighborNotifyOnMetadataChange();
	public static final Block cake = (new BlockCake(92, 121)).setHardness(0.5F).setStepSound(soundClothFootstep).setBlockName("cake").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block redstoneRepeaterIdle = (new BlockRedstoneRepeater(93, false)).setHardness(0.0F).setStepSound(soundWoodFootstep).setBlockName("diode").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block redstoneRepeaterActive = (new BlockRedstoneRepeater(94, true)).setHardness(0.0F).setLightValue(10.0F / 16.0F).setStepSound(soundWoodFootstep).setBlockName("diode").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block lockedChest = (new BlockLockedChest(95)).setHardness(0.0F).setLightValue(1.0F).setStepSound(soundWoodFootstep).setBlockName("lockedchest").disableStats().setTickOnLoad(true).disableNeighborNotifyOnMetadataChange();
	public static final Block trapdoor = (new BlockTrapDoor(96, Material.wood)).setHardness(3.0F).setStepSound(soundWoodFootstep).setBlockName("trapdoor").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block oreRuby = (new BlockOre(97, 196)).setHardness(3.0F).setResistance(5.0F).setStepSound(soundStoneFootstep).setBlockName("oreRuby");
	public static final Block blockRuby = (new BlockOreStorage(98, 148)).setHardness(5.0F).setResistance(10.0F).setStepSound(soundMetalFootstep).setBlockName("blockRuby");
	public static final Block granite = (new BlockGranite(99, 30)).setHardness(1.5F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("granite");
	public static final Block brickGranite = (new Block(100, 76, Material.rock)).setHardness(2.0F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("brickGranite");
	public static final BlockFlower mushroomwhite = (BlockFlower)(new BlockMushroom(101, 77)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("mushroomwhite");
	public static final Block stoneSlab = (new BlockStone(102, 6)).setHardness(1.5F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("stoneSlab");
	public static final Block sawdustBlock = (new BlockSand(103, 54)).setHardness(0.5F).setStepSound(soundGravelFootstep).setBlockName("sawdustBlock").disableNeighborNotifyOnMetadataChange();
	public static final Block salt = (new BlockSalt(104, 78, Material.rock)).setHardness(1.2F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("salt");
	public static final BlockFlower plantCyan = (BlockFlower)(new BlockFlower(105, 111)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("cyanRose");
	public static final Block brickStone = (new Block(106, 85, Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("brickStone");
	public static final Block pillarStone = (new Block(107, 126, Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("pillarStone");
	public static final Block potatoCrops = (new BlockCropsP(108, 136)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("crops").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block planksColored = (new BlockPlanks()).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep).setBlockName("woodColored").disableNeighborNotifyOnMetadataChange();
	public static final Block netherrackBrick = (new Block(109, 166, Material.rock)).setHardness(1.4F).setStepSound(soundStoneFootstep).setBlockName("hellbrick");
	public static final BlockNetherRice netherriceWild = (BlockNetherRice)(new BlockNetherRice(113, 183)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("netherriceWild");
	public static final Block netherriceCrops = (new BlockCropsR(114, 226)).setHardness(0.0F).setStepSound(soundGrassFootstep).setBlockName("crops").disableStats().disableNeighborNotifyOnMetadataChange();
	public static final Block blackstone = (new Block(115, 182, Material.rock)).setHardness(1.5F).setResistance(10.0F).setStepSound(soundStoneFootstep).setBlockName("blackstone");
	public static final Block oreGoldNether = (new BlockNetherGoldOre(116, 153, Material.rock)).setHardness(0.5F).setStepSound(soundStoneFootstep).setBlockName("netherGold");
	public static final Block planksTreated = (new Block(117, 8, Material.wood)).setHardness(2.0F).setResistance(5.0F).setStepSound(soundWoodFootstep).setBlockName("woodTreated").disableNeighborNotifyOnMetadataChange();
	public int blockIndexInTexture;
	public final int blockID;
	protected float blockHardness;
	protected float blockResistance;
	protected boolean blockConstructorCalled;
	protected boolean enableStats;
	public double minX;
	public double minY;
	public double minZ;
	public double maxX;
	public double maxY;
	public double maxZ;
	public StepSound stepSound;
	public float blockParticleGravity;
	public final Material blockMaterial;
	public float slipperiness;
	private String blockName;

	protected Block(int var1, Material var2) {
		this.blockConstructorCalled = true;
		this.enableStats = true;
		this.stepSound = soundPowderFootstep;
		this.blockParticleGravity = 1.0F;
		this.slipperiness = 0.6F;
		if(blocksList[var1] != null) {
			throw new IllegalArgumentException("Slot " + var1 + " is already occupied by " + blocksList[var1] + " when adding " + this);
		} else {
			this.blockMaterial = var2;
			blocksList[var1] = this;
			this.blockID = var1;
			this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			opaqueCubeLookup[var1] = this.isOpaqueCube();
			lightOpacity[var1] = this.isOpaqueCube() ? 255 : 0;
			canBlockGrass[var1] = !var2.getCanBlockGrass();
			isBlockContainer[var1] = false;
		}
	}

	protected Block disableNeighborNotifyOnMetadataChange() {
		field_28032_t[this.blockID] = true;
		return this;
	}

	protected void initializeBlock() {
	}

	protected Block(int var1, int var2, Material var3) {
		this(var1, var3);
		this.blockIndexInTexture = var2;
	}

	protected Block setStepSound(StepSound var1) {
		this.stepSound = var1;
		return this;
	}

	protected Block setLightOpacity(int var1) {
		lightOpacity[this.blockID] = var1;
		return this;
	}

	protected Block setLightValue(float var1) {
		lightValue[this.blockID] = (int)(15.0F * var1);
		return this;
	}

	protected Block setResistance(float var1) {
		this.blockResistance = var1 * 3.0F;
		return this;
	}

	public boolean renderAsNormalBlock() {
		return true;
	}

	public int getRenderType() {
		return 0;
	}

	protected Block setHardness(float var1) {
		this.blockHardness = var1;
		if(this.blockResistance < var1 * 5.0F) {
			this.blockResistance = var1 * 5.0F;
		}

		return this;
	}

	protected Block setBlockUnbreakable() {
		this.setHardness(-1.0F);
		return this;
	}

	public float getHardness() {
		return this.blockHardness;
	}

	protected Block setTickOnLoad(boolean var1) {
		tickOnLoad[this.blockID] = var1;
		return this;
	}

	public void setBlockBounds(float var1, float var2, float var3, float var4, float var5, float var6) {
		this.minX = (double)var1;
		this.minY = (double)var2;
		this.minZ = (double)var3;
		this.maxX = (double)var4;
		this.maxY = (double)var5;
		this.maxZ = (double)var6;
	}

	public float getBlockBrightness(IBlockAccess var1, int var2, int var3, int var4) {
		return var1.getBrightness(var2, var3, var4, lightValue[this.blockID]);
	}

	public boolean shouldSideBeRendered(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		return var5 == 0 && this.minY > 0.0D ? true : (var5 == 1 && this.maxY < 1.0D ? true : (var5 == 2 && this.minZ > 0.0D ? true : (var5 == 3 && this.maxZ < 1.0D ? true : (var5 == 4 && this.minX > 0.0D ? true : (var5 == 5 && this.maxX < 1.0D ? true : !var1.isBlockOpaqueCube(var2, var3, var4))))));
	}

	public boolean getIsBlockSolid(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		return var1.getBlockMaterial(var2, var3, var4).isSolid();
	}

	public int getBlockTexture(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		return this.getBlockTextureFromSideAndMetadata(var5, var1.getBlockMetadata(var2, var3, var4));
	}

	public int getBlockTextureFromSideAndMetadata(int var1, int var2) {
		return this.getBlockTextureFromSide(var1);
	}

	public int getBlockTextureFromSide(int var1) {
		return this.blockIndexInTexture;
	}

	public AxisAlignedBB getSelectedBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		return AxisAlignedBB.getBoundingBoxFromPool((double)var2 + this.minX, (double)var3 + this.minY, (double)var4 + this.minZ, (double)var2 + this.maxX, (double)var3 + this.maxY, (double)var4 + this.maxZ);
	}

	public void getCollidingBoundingBoxes(World var1, int var2, int var3, int var4, AxisAlignedBB var5, ArrayList var6) {
		AxisAlignedBB var7 = this.getCollisionBoundingBoxFromPool(var1, var2, var3, var4);
		if(var7 != null && var5.intersectsWith(var7)) {
			var6.add(var7);
		}

	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World var1, int var2, int var3, int var4) {
		return AxisAlignedBB.getBoundingBoxFromPool((double)var2 + this.minX, (double)var3 + this.minY, (double)var4 + this.minZ, (double)var2 + this.maxX, (double)var3 + this.maxY, (double)var4 + this.maxZ);
	}

	public boolean isOpaqueCube() {
		return true;
	}

	public boolean canCollideCheck(int var1, boolean var2) {
		return this.isCollidable();
	}

	public boolean isCollidable() {
		return true;
	}

	public void updateTick(World var1, int var2, int var3, int var4, Random var5) {
	}

	public void randomDisplayTick(World var1, int var2, int var3, int var4, Random var5) {
	}

	public void onBlockDestroyedByPlayer(World var1, int var2, int var3, int var4, int var5) {
	}

	public void onNeighborBlockChange(World var1, int var2, int var3, int var4, int var5) {
	}

	public int tickRate() {
		return 10;
	}

	public void onBlockAdded(World var1, int var2, int var3, int var4) {
	}

	public void onBlockRemoval(World var1, int var2, int var3, int var4) {
	}

	public int quantityDropped(Random var1) {
		return 1;
	}

	public int idDropped(int var1, Random var2) {
		return this.blockID;
	}

	public float blockStrength(EntityPlayer var1) {
		return this.blockHardness < 0.0F ? 0.0F : (!var1.canHarvestBlock(this) ? 1.0F / this.blockHardness / 100.0F : var1.getCurrentPlayerStrVsBlock(this) / this.blockHardness / 30.0F);
	}

	public final void dropBlockAsItem(World var1, int var2, int var3, int var4, int var5) {
		this.dropBlockAsItemWithChance(var1, var2, var3, var4, var5, 1.0F);
	}

	public void dropBlockAsItemWithChance(World var1, int var2, int var3, int var4, int var5, float var6) {
		if(!var1.multiplayerWorld) {
			int var7 = this.quantityDropped(var1.rand);

			for(int var8 = 0; var8 < var7; ++var8) {
				if(var1.rand.nextFloat() <= var6) {
					int var9 = this.idDropped(var5, var1.rand);
					if(var9 > 0) {
						this.dropBlockAsItem_do(var1, var2, var3, var4, new ItemStack(var9, 1, this.damageDropped(var5)));
					}
				}
			}

		}
	}

	protected void dropBlockAsItem_do(World var1, int var2, int var3, int var4, ItemStack var5) {
		if(!var1.multiplayerWorld) {
			float var6 = 0.7F;
			double var7 = (double)(var1.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
			double var9 = (double)(var1.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
			double var11 = (double)(var1.rand.nextFloat() * var6) + (double)(1.0F - var6) * 0.5D;
			EntityItem var13 = new EntityItem(var1, (double)var2 + var7, (double)var3 + var9, (double)var4 + var11, var5);
			var13.delayBeforeCanPickup = 10;
			var1.entityJoinedWorld(var13);
		}
	}

	protected int damageDropped(int var1) {
		return 0;
	}

	public float getExplosionResistance(Entity var1) {
		return this.blockResistance / 5.0F;
	}

	public MovingObjectPosition collisionRayTrace(World var1, int var2, int var3, int var4, Vec3D var5, Vec3D var6) {
		this.setBlockBoundsBasedOnState(var1, var2, var3, var4);
		var5 = var5.addVector((double)(-var2), (double)(-var3), (double)(-var4));
		var6 = var6.addVector((double)(-var2), (double)(-var3), (double)(-var4));
		Vec3D var7 = var5.getIntermediateWithXValue(var6, this.minX);
		Vec3D var8 = var5.getIntermediateWithXValue(var6, this.maxX);
		Vec3D var9 = var5.getIntermediateWithYValue(var6, this.minY);
		Vec3D var10 = var5.getIntermediateWithYValue(var6, this.maxY);
		Vec3D var11 = var5.getIntermediateWithZValue(var6, this.minZ);
		Vec3D var12 = var5.getIntermediateWithZValue(var6, this.maxZ);
		if(!this.isVecInsideYZBounds(var7)) {
			var7 = null;
		}

		if(!this.isVecInsideYZBounds(var8)) {
			var8 = null;
		}

		if(!this.isVecInsideXZBounds(var9)) {
			var9 = null;
		}

		if(!this.isVecInsideXZBounds(var10)) {
			var10 = null;
		}

		if(!this.isVecInsideXYBounds(var11)) {
			var11 = null;
		}

		if(!this.isVecInsideXYBounds(var12)) {
			var12 = null;
		}

		Vec3D var13 = null;
		if(var7 != null && (var13 == null || var5.distanceTo(var7) < var5.distanceTo(var13))) {
			var13 = var7;
		}

		if(var8 != null && (var13 == null || var5.distanceTo(var8) < var5.distanceTo(var13))) {
			var13 = var8;
		}

		if(var9 != null && (var13 == null || var5.distanceTo(var9) < var5.distanceTo(var13))) {
			var13 = var9;
		}

		if(var10 != null && (var13 == null || var5.distanceTo(var10) < var5.distanceTo(var13))) {
			var13 = var10;
		}

		if(var11 != null && (var13 == null || var5.distanceTo(var11) < var5.distanceTo(var13))) {
			var13 = var11;
		}

		if(var12 != null && (var13 == null || var5.distanceTo(var12) < var5.distanceTo(var13))) {
			var13 = var12;
		}

		if(var13 == null) {
			return null;
		} else {
			byte var14 = -1;
			if(var13 == var7) {
				var14 = 4;
			}

			if(var13 == var8) {
				var14 = 5;
			}

			if(var13 == var9) {
				var14 = 0;
			}

			if(var13 == var10) {
				var14 = 1;
			}

			if(var13 == var11) {
				var14 = 2;
			}

			if(var13 == var12) {
				var14 = 3;
			}

			return new MovingObjectPosition(var2, var3, var4, var14, var13.addVector((double)var2, (double)var3, (double)var4));
		}
	}

	private boolean isVecInsideYZBounds(Vec3D var1) {
		return var1 == null ? false : var1.yCoord >= this.minY && var1.yCoord <= this.maxY && var1.zCoord >= this.minZ && var1.zCoord <= this.maxZ;
	}

	private boolean isVecInsideXZBounds(Vec3D var1) {
		return var1 == null ? false : var1.xCoord >= this.minX && var1.xCoord <= this.maxX && var1.zCoord >= this.minZ && var1.zCoord <= this.maxZ;
	}

	private boolean isVecInsideXYBounds(Vec3D var1) {
		return var1 == null ? false : var1.xCoord >= this.minX && var1.xCoord <= this.maxX && var1.yCoord >= this.minY && var1.yCoord <= this.maxY;
	}

	public void onBlockDestroyedByExplosion(World var1, int var2, int var3, int var4) {
	}

	public int getRenderBlockPass() {
		return 0;
	}

	public boolean canPlaceBlockOnSide(World var1, int var2, int var3, int var4, int var5) {
		return this.canPlaceBlockAt(var1, var2, var3, var4);
	}

	public boolean canPlaceBlockAt(World var1, int var2, int var3, int var4) {
		int var5 = var1.getBlockId(var2, var3, var4);
		return var5 == 0 || blocksList[var5].blockMaterial.getIsGroundCover();
	}

	public boolean blockActivated(World var1, int var2, int var3, int var4, EntityPlayer var5) {
		return false;
	}

	public void onEntityWalking(World var1, int var2, int var3, int var4, Entity var5) {
	}

	public void onBlockPlaced(World var1, int var2, int var3, int var4, int var5) {
	}

	public void onBlockClicked(World var1, int var2, int var3, int var4, EntityPlayer var5) {
	}

	public void velocityToAddToEntity(World var1, int var2, int var3, int var4, Entity var5, Vec3D var6) {
	}

	public void setBlockBoundsBasedOnState(IBlockAccess var1, int var2, int var3, int var4) {
	}

	public int getRenderColor(int var1) {
		return 16777215;
	}

	public int colorMultiplier(IBlockAccess var1, int var2, int var3, int var4) {
		return 16777215;
	}

	public boolean isPoweringTo(IBlockAccess var1, int var2, int var3, int var4, int var5) {
		return false;
	}

	public boolean canProvidePower() {
		return false;
	}

	public void onEntityCollidedWithBlock(World var1, int var2, int var3, int var4, Entity var5) {
	}

	public boolean isIndirectlyPoweringTo(World var1, int var2, int var3, int var4, int var5) {
		return false;
	}

	public void setBlockBoundsForItemRender() {
	}

	public void harvestBlock(World var1, EntityPlayer var2, int var3, int var4, int var5, int var6) {
		var2.addStat(StatList.mineBlockStatArray[this.blockID], 1);
		this.dropBlockAsItem(var1, var3, var4, var5, var6);
	}

	public boolean canBlockStay(World var1, int var2, int var3, int var4) {
		return true;
	}

	public void onBlockPlacedBy(World var1, int var2, int var3, int var4, EntityLiving var5) {
	}

	public Block setBlockName(String var1) {
		this.blockName = "tile." + var1;
		return this;
	}

	public String translateBlockName() {
		return StatCollector.translateToLocal(this.getBlockName() + ".name");
	}

	public String getBlockName() {
		return this.blockName;
	}

	public void playBlock(World var1, int var2, int var3, int var4, int var5, int var6) {
	}

	public boolean getEnableStats() {
		return this.enableStats;
	}

	protected Block disableStats() {
		this.enableStats = false;
		return this;
	}

	public int getMobilityFlag() {
		return this.blockMaterial.getMaterialMobility();
	}

	static {
		Item.itemsList[cloth.blockID] = (new ItemCloth(cloth.blockID - 256)).setItemName("cloth");
		Item.itemsList[planksColored.blockID] = (new ItemPlanks(planksColored.blockID - 256)).setItemName("woodColor");
		Item.itemsList[wood.blockID] = (new ItemLog(wood.blockID - 256)).setItemName("log");
		Item.itemsList[stairSingle.blockID] = (new ItemSlab(stairSingle.blockID - 256)).setItemName("stoneSlab");
		Item.itemsList[sapling.blockID] = (new ItemSapling(sapling.blockID - 256)).setItemName("sapling");
		Item.itemsList[leaves.blockID] = (new ItemLeaves(leaves.blockID - 256)).setItemName("leaves");
		Item.itemsList[pistonBase.blockID] = new ItemPiston(pistonBase.blockID - 256);
		Item.itemsList[pistonStickyBase.blockID] = new ItemPiston(pistonStickyBase.blockID - 256);

		for(int var0 = 0; var0 < 256; ++var0) {
			if(blocksList[var0] != null && Item.itemsList[var0] == null) {
				Item.itemsList[var0] = new ItemBlock(var0 - 256);
				blocksList[var0].initializeBlock();
			}
		}

		canBlockGrass[0] = true;
		StatList.func_25154_a();
	}
}
