package com.naxanria.etherial.setup;

import com.naxanria.etherial.Etherial;
import com.naxanria.naxlib.util.WorldUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class EtherialSetup
{
  private static List<BlockItem> blockItems = new ArrayList<>();
  
  private static IForgeRegistry<Item> itemRegistry;
  private static IForgeRegistry<Block> blockRegistry;
  private static IForgeRegistry<TileEntityType<?>> blockTileRegistry;
  
  private static ItemGroup itemGroup = new ItemGroup(Etherial.MODID)
  {
    @Override
    public ItemStack createIcon()
    {
      return new ItemStack(Items.ENDER_PEARL);
    }
  };
  
  private static  <T extends Item> T registerItem(T item, String name)
  {
    ResourceLocation location = new ResourceLocation(Etherial.MODID, name);
    item.setRegistryName(location);
    itemRegistry.register(item);
    
    return item;
  }
  
  private static <T extends Block> T registerBlock(String name, T block)
  {
    return registerBlock(name, block, true);
  }
  
  private static <T extends Block> T registerBlock(String name, T block, boolean createItemBlock)
  {
    block.setRegistryName(new ResourceLocation(Etherial.MODID, name));
    
    if (createItemBlock)
    {
      blockItems.add((BlockItem) new BlockItem(block, getItemProperties()).setRegistryName(new ResourceLocation(Etherial.MODID, name)));
    }
    
    blockRegistry.register(block);
    
    return block;
  }
  
  private static Item.Properties getItemProperties()
  {
    return new Item.Properties().group(itemGroup);
  }
  
  private static Block.Properties getBlockProperties(Material material)
  {
    return Block.Properties.create(material);
  }
  
  @SubscribeEvent
  public static void registerBlocks(final RegistryEvent.Register<Block> event)
  {
    Etherial.LOGGER.info("Registering blocks");
    
    blockRegistry = event.getRegistry();
    
    
  }
  
  @SubscribeEvent
  public static void registerItems(final RegistryEvent.Register<Item> event)
  {
    Etherial.LOGGER.info("Registering Items");
    
    itemRegistry = event.getRegistry();
  
    for (BlockItem blockItem :
      blockItems)
    {
      itemRegistry.register(blockItem);
    }
    
    registerItem
    (
      new ShearsItem(getItemProperties().maxDamage(60))
      {
        @Override
        public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving)
        {
          boolean _super = super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
          
          if (state.isIn(BlockTags.LEAVES))
          {
            Block block = state.getBlock();
            WorldUtil.spawnAsEntity(worldIn, pos, new ItemStack(block));
          }
          
          return _super;
        }
      }, "wooden_shears"
    );
  }
  
}
