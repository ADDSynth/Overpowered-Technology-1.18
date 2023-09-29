package addsynth.energy.gameplay.machines.circuit_fabricator;

import java.util.function.Supplier;
import addsynth.core.util.game.MinecraftUtility;
import addsynth.energy.ADDSynthEnergy;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

public final class ChangeCircuitFabricatorRecipe {

  private final BlockPos position;
  private final String recipe_output;

  public ChangeCircuitFabricatorRecipe(final BlockPos position, final String recipe_output){
    this.position = position;
    this.recipe_output = recipe_output;
  }

  public static final void encode(final ChangeCircuitFabricatorRecipe message, final FriendlyByteBuf buf){
    buf.writeInt(message.position.getX());
    buf.writeInt(message.position.getY());
    buf.writeInt(message.position.getZ());
    buf.writeUtf(message.recipe_output);
  }

  public static final ChangeCircuitFabricatorRecipe decode(final FriendlyByteBuf buf){
    return new ChangeCircuitFabricatorRecipe(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()), buf.readUtf());
  }

  public static void handle(final ChangeCircuitFabricatorRecipe message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    final ServerPlayer player = context.getSender();
    if(player != null){
      @SuppressWarnings("resource")
      final ServerLevel world = player.getLevel();
      context.enqueueWork(() -> {
        if(world.isLoaded(message.position)){
          final TileCircuitFabricator tile = MinecraftUtility.getTileEntity(message.position, world, TileCircuitFabricator.class);
          if(tile != null){
            tile.change_recipe(message.recipe_output);
            tile.ejectInvalidItems(player); // must stay here because we have access to the player?
          }
          else{
            ADDSynthEnergy.log.warn(new NullPointerException("No TileEntity exists at location: "+message.position+"."));
          }
        }
      });
      context.setPacketHandled(true);
    }
  }

}
