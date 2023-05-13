package addsynth.energy.gameplay.machines.universal_energy_interface;

import addsynth.energy.gameplay.reference.TextReference;
import net.minecraft.network.chat.TranslatableComponent;

public enum TRANSFER_MODE {

  BI_DIRECTIONAL(true,  true,  TextReference.transfer_mode.bi_directional),
  RECEIVE(       true,  false, TextReference.transfer_mode.receive),
  EXTRACT(       false, true,  TextReference.transfer_mode.extract),
  // TODO: reimplement TRANSFER_MODE External and Internal?
  // EXTERNAL(      true,  true,  false, TextReference.transfer_mode.external),
  // INTERNAL(      false, false, true,  TextReference.transfer_mode.internal),
  NO_TRANSFER(   false, false, TextReference.transfer_mode.none);
  
  /** Allow external machines to give Energy to us. */
  public final boolean canReceive;
  /** Allow external machines to extract Energy from us. */
  public final boolean canExtract;

  public final TranslatableComponent title;

  private TRANSFER_MODE(final boolean receive, final boolean extract, final TranslatableComponent title){
    this.canReceive = receive;
    this.canExtract = extract;
    this.title = title;
  }
  
}
