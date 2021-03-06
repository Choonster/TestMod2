package com.choonster.testmod2;

import com.choonster.testmod2.compat.BCCompat;
import com.choonster.testmod2.config.Config;
import com.choonster.testmod2.crafting.FuelHandler;
import com.choonster.testmod2.creativetab.CreativeTabTestMod2;
import com.choonster.testmod2.event.BlockEventHandler;
import com.choonster.testmod2.event.EntityEventHandler;
import com.choonster.testmod2.event.ItemDestroyEventHandler;
import com.choonster.testmod2.init.*;
import com.choonster.testmod2.proxy.CommonProxy;
import com.choonster.testmod2.tweak.moddedstatsfix.ModdedStatsFix;
import com.choonster.testmod2.util.SplitterTest;
import com.choonster.testmod2.world.gen.WorldGenSheepSpawner;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;

import java.util.UUID;

@Mod(name = "Test Mod 2", modid = References.MODID, guiFactory = "com.choonster.testmod2.config.GuiConfigFactoryTestMod2", dependencies = "after:BiomesOPlenty")
public class TestMod2 {
	@Instance
	public static TestMod2 instance;

	public static CreativeTabs tab;

	@SidedProxy(clientSide = "com.choonster.testmod2.proxy.ClientProxy", serverSide = "com.choonster.testmod2.proxy.ServerProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		FMLLog.bigWarning("Random UUID: %s", UUID.randomUUID().toString());

		tab = new CreativeTabTestMod2();

		Config.load(event);

		ModFluids.registerFluids();
		ModBlocks.registerBlocks();
		ModItems.registerItems();

		ModBiomes.registerBiomes();
		ModEntities.preInit();
		ModMapGen.registerMapGen();

		MinecraftForge.EVENT_BUS.register(new BlockEventHandler());
		MinecraftForge.EVENT_BUS.register(new ItemDestroyEventHandler());
		MinecraftForge.EVENT_BUS.register(new EntityEventHandler());
		//ItemTooltipEventHandler.init();

		SplitterTest.splitStrings();

		GameRegistry.registerFuelHandler(new FuelHandler());

		proxy.preInit();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		ModRecipes.registerRecipes();
		ModLoot.initLoot();

		//UnpunchableLogs.init();
		BCCompat.init();

		GameRegistry.registerWorldGenerator(new WorldGenSheepSpawner(), 100);

		FMLInterModComms.sendMessage("Waila", "register", "com.choonster.testmod2.compat.waila.WailaCompat.register");

		proxy.init();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ModdedStatsFix.addStats();
		ModRecipes.replaceAndRemoveRecipes();

		proxy.postInit();
	}

	@EventHandler
	public void remapIDs(FMLModIdMappingEvent event) {
		ModdedStatsFix.remapIDs(event.remappedIds);
	}

	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event){
		Logger.info("Server stopping. Dedicated? %s", MinecraftServer.getServer().isDedicatedServer());
	}

	@EventHandler
	public void serverStopped(FMLServerStoppedEvent event){
		Logger.info("Server stopped. Dedicated? %s", MinecraftServer.getServer().isDedicatedServer());
	}
}
