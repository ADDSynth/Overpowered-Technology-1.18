package addsynth.core.gameplay.blocks;

import addsynth.core.ADDSynthCore;
import addsynth.core.game.RegistryUtil;
import addsynth.core.gameplay.reference.Names;
import addsynth.core.util.constants.Constants;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public final class CautionBlock extends Block {

  public CautionBlock(){
    super(Block.Properties.of(Material.STONE, MaterialColor.COLOR_YELLOW).sound(SoundType.STONE).strength(2.0f, Constants.block_resistance));
    RegistryUtil.register_block(this, Names.CAUTION_BLOCK, ADDSynthCore.creative_tab);
  }

}
