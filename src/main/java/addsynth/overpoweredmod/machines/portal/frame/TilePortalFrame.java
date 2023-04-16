package addsynth.overpoweredmod.machines.portal.frame;

import javax.annotation.Nullable;
import addsynth.core.game.inventory.SlotData;
import addsynth.core.game.tiles.TileStorageMachine;
import addsynth.material.util.MaterialTag;
import addsynth.material.util.MaterialsUtil;
import addsynth.overpoweredmod.machines.Filters;
import addsynth.overpoweredmod.registers.Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public final class TilePortalFrame extends TileStorageMachine implements MenuProvider {

  public TilePortalFrame(BlockPos position, BlockState blockstate){
    super(Tiles.PORTAL_FRAME, position, blockstate, new SlotData[]{new SlotData(Filters.portal_frame, 1)});
  }

  public final int check_item(){
    final ItemStack stack = input_inventory.getStackInSlot(0);
    if(stack.isEmpty()){ return -1; }
    final Item item = stack.getItem();
    if(MaterialsUtil.match(item, MaterialTag.RUBY.BLOCKS)){     return 0; }
    if(MaterialsUtil.match(item, MaterialTag.TOPAZ.BLOCKS)){    return 1; }
    if(MaterialsUtil.match(item, MaterialTag.CITRINE.BLOCKS)){  return 2; }
    if(MaterialsUtil.match(item, MaterialTag.EMERALD.BLOCKS)){  return 3; }
    if(MaterialsUtil.match(item, MaterialTag.DIAMOND.BLOCKS)){  return 4; }
    if(MaterialsUtil.match(item, MaterialTag.SAPPHIRE.BLOCKS)){ return 5; }
    if(MaterialsUtil.match(item, MaterialTag.AMETHYST.BLOCKS)){ return 6; }
    if(MaterialsUtil.match(item, MaterialTag.QUARTZ.BLOCKS)){   return 7; }
    return -1;
  }

  @Override
  @Nullable
  public AbstractContainerMenu createMenu(int id, Inventory player_inventory, Player player){
    return new ContainerPortalFrame(id, player_inventory, this);
  }

  @Override
  public Component getDisplayName(){
    return new TranslatableComponent(getBlockState().getBlock().getDescriptionId());
  }

}
