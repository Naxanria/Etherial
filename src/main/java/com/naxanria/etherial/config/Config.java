package com.naxanria.etherial.config;

import com.naxanria.naxlib.client.gui.config.ConfigGui;
import net.minecraft.util.Direction;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;

public class Config
{
  public static boolean test1 = false;
  public static int test2 = 1;
  public static Direction test3 = Direction.NORTH;
  
  private static Client clientConfig;
  private static ForgeConfigSpec clientSpec;
  
  static
  {
    final Pair<Client, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure((Client::new));
    clientConfig = specPair.getKey();
    clientSpec = specPair.getValue();
  }
  
  public static void register(final ModLoadingContext context)
  {
    FMLJavaModLoadingContext.get().getModEventBus().addListener(Config::reload);
    
    context.registerConfig(ModConfig.Type.CLIENT, clientSpec);
  }
  
  public static void reload(final ModConfig.ModConfigEvent event)
  {
    ModConfig config = event.getConfig();
    
    if (config.getSpec() == clientSpec)
    {
      reloadClient();
    }
  }
  
  private static void reloadClient()
  {
    test1 = clientConfig.test1.get();
    test2 = clientConfig.test2.get();
    test3 = clientConfig.test3.get();
  }
  
  public static ConfigGui getGui()
  {
    return ConfigGui.Builder.create(null, clientSpec)
      .push("General")
      .add(clientConfig.test1, false)
      .comment("Boolean test")
      .push("Blah")
      .add(clientConfig.test2, -1, -10, 10)
      .comment("Integer Test")
      .toTop().push("General2")
      .add(clientConfig.test3, Direction.NORTH)
      .comment("EnumTest")
      .build();
  }
  
  public static Client getClientConfig()
  {
    return clientConfig;
  }
  
  public static ForgeConfigSpec getClientSpec()
  {
    return clientSpec;
  }
  
  public static class Client
  {
    private final ForgeConfigSpec.BooleanValue test1;
    private final ForgeConfigSpec.IntValue test2;
    private final ForgeConfigSpec.EnumValue<Direction> test3;
    
    public Client(final ForgeConfigSpec.Builder builder)
    {
      builder.push("General");
      
      test1 = builder
        .comment("Test boolean")
        .define("test1", false);
      
      test2 = builder
        .comment("Integer test")
        .defineInRange("test2", -1, -10, 10);
      
      test3 = builder
        .comment("Enum test")
        .defineEnum("test3", Direction.NORTH);
    }
  }
  
  public class Common
  {
  
  }
}
