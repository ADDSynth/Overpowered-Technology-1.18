package addsynth.core.gameplay.team_manager.network_messages;

import java.util.ArrayList;
import java.util.function.Supplier;
import addsynth.core.gameplay.team_manager.data.ObjectiveDataUnit;
import addsynth.core.gameplay.team_manager.data.TeamData;
import addsynth.core.gameplay.team_manager.data.TeamDataUnit;
import addsynth.core.util.game.data.CombinedNameComponent;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

/** Sent from the server to the client, to sync the {@link TeamData} */
public final class TeamManagerSyncMessage {

  private final ArrayList<CombinedNameComponent> non_team_players;
  private final TeamDataUnit[] teams;
  private final ObjectiveDataUnit[] objectives;
  private final String[] display_slot_objectives;

  public TeamManagerSyncMessage(ArrayList<CombinedNameComponent> non_team_players, TeamDataUnit[] teams, ObjectiveDataUnit[] objectives, String[] display_slot_objectives){
    this.non_team_players = non_team_players;
    this.teams = teams;
    this.objectives = objectives;
    this.display_slot_objectives = display_slot_objectives;
  }

  /** Send data to Clients. */
  public static final void encode(final TeamManagerSyncMessage message, final FriendlyByteBuf data){
    CombinedNameComponent.encodeArray(data, message.non_team_players);
    data.writeInt(message.teams.length);
    for(final TeamDataUnit t : message.teams){
      t.encode(data);
    }
    data.writeInt(message.objectives.length);
    for(final ObjectiveDataUnit o : message.objectives){
      o.encode(data);
    }
    data.writeUtf(message.display_slot_objectives[0]);
    data.writeUtf(message.display_slot_objectives[1]);
    data.writeUtf(message.display_slot_objectives[2]);
  }

  /** Receiving data on client side. */
  public static final TeamManagerSyncMessage decode(final FriendlyByteBuf data){
    final ArrayList<CombinedNameComponent> non_team_players = new ArrayList<CombinedNameComponent>();
    for(final CombinedNameComponent t : CombinedNameComponent.decodeArray(data)){
      non_team_players.add(t);
    }
    int length = data.readInt();
    final TeamDataUnit[] teams = new TeamDataUnit[length];
    int i;
    for(i = 0; i < length; i++){
      teams[i] = TeamDataUnit.decode(data);
    }
    length = data.readInt();
    final ObjectiveDataUnit[] objectives = new ObjectiveDataUnit[length];
    for(i = 0; i < length; i++){
      objectives[i] = ObjectiveDataUnit.decode(data);
    }
    final String[] display_slot_objectives = {data.readUtf(), data.readUtf(), data.readUtf()};
    return new TeamManagerSyncMessage(non_team_players, teams, objectives, display_slot_objectives);
  }

  public static void handle(final TeamManagerSyncMessage message, final Supplier<NetworkEvent.Context> context_supplier){
    final NetworkEvent.Context context = context_supplier.get();
    context.enqueueWork(() -> {
      // The decode method is executed on the Network Thread, which runs independantly from the main Client Thread.
      // That explains why clients using the Team manager would get random crashes. It's becuase the Network Thread
      // was altering the data. This is probably my first experience of a race condition between two threads.
      // That is why we ENQUEUE work on the Client thread.
      TeamData.syncClientData(message.non_team_players, message.teams, message.objectives, message.display_slot_objectives);
    });
    context.setPacketHandled(true);
  }

}
