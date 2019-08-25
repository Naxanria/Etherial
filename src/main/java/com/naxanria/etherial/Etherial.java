package com.naxanria.etherial;

import com.naxanria.etherial.client.event.ClientEventHandler;
import com.naxanria.etherial.config.Config;
import com.naxanria.naxlib.client.event.KeyHandler;
import com.naxanria.naxlib.client.event.KeyParser;
import com.naxanria.naxlib.util.StringUtil;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

@Mod(Etherial.MODID)
public class Etherial
{
  public static final String MODID = "etherial";
  public static final Logger LOGGER = LogManager.getLogger(MODID);
  
  public Etherial()
  {
    IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    
    DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
    {
      modEventBus.addListener(this::clientSetup);
      Config.register(ModLoadingContext.get());
//      modEventBus.addListener(ClientEventHandler::clientTick);
    });

    modEventBus.addListener(this::commonSetup);
  }
  
  private void commonSetup(final FMLCommonSetupEvent event)
  {
    LOGGER.info("Common setup");
  }
  
  private void clientSetup(final FMLClientSetupEvent event)
  {
    LOGGER.info("Client setup");
    
    LOGGER.info("Registering key bindings");
    KeyHandler.register(new KeyParser("Config", GLFW.GLFW_KEY_KP_3, KeyConflictContext.IN_GAME, "Etherial")
    {
      @Override
      public void onKeyDown()
      {
        mc.displayGuiScreen(Config.getGui());
      }
  
      @Override
      public boolean isListening()
      {
        return mc.currentScreen == null;
      }
    });
    
    
  }
}
