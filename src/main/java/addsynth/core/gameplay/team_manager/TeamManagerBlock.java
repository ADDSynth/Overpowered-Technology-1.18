package addsynth.core.gameplay.team_manager;

import addsynth.core.ADDSynthCore;
import addsynth.core.game.RegistryUtil;
import addsynth.core.gameplay.client.GuiProvider;
import addsynth.core.gameplay.reference.Names;
import addsynth.core.gameplay.reference.TextReference;
import addsynth.core.util.command.PermissionLevel;
import addsynth.core.util.constants.Constants;
import addsynth.core.util.game.MessageUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;

public final class TeamManagerBlock extends Block {

  public TeamManagerBlock(){
    super(Block.Properties.of(Material.STONE, MaterialColor.METAL).sound(SoundType.STONE).strength(2.0f, Constants.block_resistance));
    RegistryUtil.register_block(this, Names.TEAM_MANAGER, ADDSynthCore.creative_tab);
  }

  @Override
  @SuppressWarnings({ "deprecation" })
  public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit){
    if(world.isClientSide){
      if(player.hasPermissions(PermissionLevel.COMMANDS)){
        GuiProvider.openTeamManagerGui();
      }
      else{
        MessageUtil.send_to_player(player, TextReference.you_dont_have_permission);
      }
    }
    return InteractionResult.SUCCESS;
  }

}
