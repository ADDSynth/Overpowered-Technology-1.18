package addsynth.core.gameplay.team_manager.data;

import java.util.ArrayList;
import addsynth.core.util.network.NetworkUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.scores.Team;

public final class TeamDataUnit {

  public String name;
  public Component display_name;
  public int color;
  public Component prefix;
  public Component suffix;
  public boolean pvp;
  public boolean see_invisible_allys;
  public int nametag_option;
  public int death_message_option;
  public ArrayList<Component> players = new ArrayList<Component>();
  
  public final boolean matches(final Team team){
    return name.equals(team.getName());
  }
  
  public final void encode(final FriendlyByteBuf data){
    data.writeUtf(name);
    data.writeComponent(display_name);
    data.writeByte(color);
    data.writeBoolean(pvp);
    data.writeBoolean(see_invisible_allys);
    data.writeByte(nametag_option);
    data.writeByte(death_message_option);
    data.writeUtf(prefix.getString());
    data.writeUtf(suffix.getString());
    int i;
    final int length = players.size();
    final TextComponent[] player_names = new TextComponent[length];
    for(i = 0; i < length; i++){
      player_names[i] = (TextComponent)players.get(i);
    }
    NetworkUtil.writeTextComponentArray(data, player_names);
  }
  
  public static final TeamDataUnit decode(final FriendlyByteBuf data){
    final TeamDataUnit team = new TeamDataUnit();
    team.name = data.readUtf();
    team.display_name = data.readComponent();
    team.color = data.readByte();
    team.pvp = data.readBoolean();
    team.see_invisible_allys = data.readBoolean();
    team.nametag_option = data.readByte();
    team.death_message_option = data.readByte();
    team.prefix = new TextComponent(data.readUtf());
    team.suffix = new TextComponent(data.readUtf());
    team.players = new ArrayList<Component>();
    for(final Component t : NetworkUtil.readTextComponentArray(data)){
      team.players.add(t);
    }
    return team;
  }

}
