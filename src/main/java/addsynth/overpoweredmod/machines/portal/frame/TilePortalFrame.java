package addsynth.overpoweredmod.machines.portal.frame;

import javax.annotation.Nullable;
import addsynth.core.game.inventory.SlotData;
import addsynth.core.game.inventory.filter.TagFilter;
import addsynth.core.game.tiles.TileStorageMachine;
import addsynth.material.util.MaterialTag;
import addsynth.overpoweredmod.game.tags.OverpoweredItemTags;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITagManager;

public final class TilePortalFrame extends TileStorageMachine implements MenuProvider {

  public static final TagFilter filter = new TagFilter(OverpoweredItemTags.portal_fuel);

  public TilePortalFrame(BlockPos position, BlockState blockstate){
    super(Tiles.PORTAL_FRAME.get(), position, blockstate, new SlotData[]{new SlotData(filter, 1)});
  }

  public final int check_item(){
    final ItemStack stack = input_inventory.getStackInSlot(0);
    if(stack.isEmpty()){ return -1; }
    final Item item = stack.getItem();
    final ITagManager<Item> tag_manager = ForgeRegistries.ITEMS.tags();
    if(tag_manager.getTag(MaterialTag.RUBY.BLOCKS           ).contains(item)){ return 0; }
    if(tag_manager.getTag(MaterialTag.TOPAZ.BLOCKS          ).contains(item)){ return 1; }
    if(tag_manager.getTag(MaterialTag.CITRINE.BLOCKS        ).contains(item)){ return 2; }
    if(tag_manager.getTag(Tags.Items.STORAGE_BLOCKS_EMERALD ).contains(item)){ return 3; }
    if(tag_manager.getTag(Tags.Items.STORAGE_BLOCKS_DIAMOND ).contains(item)){ return 4; }
    if(tag_manager.getTag(MaterialTag.SAPPHIRE.BLOCKS       ).contains(item)){ return 5; }
    if(tag_manager.getTag(Tags.Items.STORAGE_BLOCKS_AMETHYST).contains(item)){ return 6; }
    if(tag_manager.getTag(Tags.Items.STORAGE_BLOCKS_QUARTZ  ).contains(item)){ return 7; }
    return -1;
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new ContainerPortalFrame(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return getBlockState().getBlock().getName();
  }

}
