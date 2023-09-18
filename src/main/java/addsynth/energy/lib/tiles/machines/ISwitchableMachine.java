package addsynth.energy.lib.tiles.machines;

/** This interface is used when the TileEntity has a power switch. */
public interface ISwitchableMachine {

  public void togglePowerSwitch();

  public boolean get_switch_state();

}
