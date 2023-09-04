package addsynth.overpoweredmod.blocks;

import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.machines.data_cable.DataCable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public final class IronFrameBlock extends Block {

  public IronFrameBlock(){
    super(Block.Properties.of(Material.METAL, MaterialColor.WOOL).strength(3.5f, 300.0f));
    setRegistryName(Names.IRON_FRAME_BLOCK);
    DataCable.addAttachableBlock(this);
  }

}
