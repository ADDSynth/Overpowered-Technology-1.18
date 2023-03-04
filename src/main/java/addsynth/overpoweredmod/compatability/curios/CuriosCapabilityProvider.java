package addsynth.overpoweredmod.compatability.curios;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

// copied from: https://github.com/TheIllusiveC4/Curios/blob/1.17.x/src/main/java/top/theillusivec4/curios/common/capability/CurioItemCapability.java
public final class CuriosCapabilityProvider implements ICapabilityProvider {

  final LazyOptional<ICurio> capability;
  
  public CuriosCapabilityProvider(ICurio curio){
    this.capability = LazyOptional.of(() -> curio);
  }
  
  @Nonnull
  @Override
  public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side){
    return CuriosCapability.ITEM.orEmpty(cap, capability);
  }

}
