package addsynth.overpoweredmod.items;

import java.util.List;
import javax.annotation.Nullable;
import addsynth.core.compat.Compatibility;
import addsynth.core.game.item.constants.ItemValue;
import addsynth.overpoweredmod.compatability.curios.CuriosCapabilityProvider;
import addsynth.overpoweredmod.compatability.curios.RingEffects;
import addsynth.overpoweredmod.game.reference.Names;
import addsynth.overpoweredmod.items.register.OverpoweredItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

public final class Ring extends OverpoweredItem {

  public Ring(final int ring_id){
    super(Names.MAGIC_RING[ring_id], new Item.Properties().stacksTo(1));
  }

  @Override
  @Nullable
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt){
    return Compatibility.CURIOS.isLoaded() ? new CuriosCapabilityProvider(
      new ICurio(){
      
        @Override
        public ItemStack getStack(){
          return stack;
        }
        
        @Override
        public void onEquip(SlotContext slotContext, ItemStack prevStack){
        }
        
        @Override
        public void curioTick(SlotContext slotContext){
          final LivingEntity livingEntity = slotContext.entity();
          if(livingEntity.level.isClientSide == false){
            // Example Ring in the Curios mod checks the livingEntity.ticksExisted and modulos it with 20,
            // then Re-adds the effect every second. This is far superior than checking to see if the
            // entity has the effect, and then if the effect is about to run out.
            // But we still need the special case for the Extra Health effect.
            RingEffects.checkEntityHasEffect(stack, livingEntity);
          }
        }
        
        @Override
        public void onUnequip(SlotContext slotContext, ItemStack newStack){
          RingEffects.removeEffectsFromEntity(stack, slotContext.entity());
        }
        
      }
    ) : null;
  }

  @Override
  public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn){
    RingEffects.assignToolTip(stack, tooltip);
  }

  @Override
  public Rarity getRarity(ItemStack stack){
    return ItemValue.values()[RingEffects.get_ring_rarity(stack)].rarity;
  }

  @Override
  public boolean isFoil(final ItemStack stack){
    return RingEffects.get_ring_rarity(stack) >= ItemValue.EPIC.value;
  }

}
