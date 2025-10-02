package jmg.scoutingapp;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayerService {
    private static final String FILE_PATH = "players.json";
    private static final ObjectMapper mapper = new ObjectMapper();

    // Load all players
    public static List<Player> loadPlayers() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                file.createNewFile();
                mapper.writeValue(file, new ArrayList<Player>());
            }
            return mapper.readValue(file, new TypeReference<List<Player>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Save a new player
    public static void savePlayer(Player player) {
        try {
            List<Player> players = loadPlayers();
            players.add(player);
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), players);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updatePlayer(Player updatedPlayer) {
        List<Player> players = loadPlayers();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getAppId().equals(updatedPlayer.getAppId())) {
                players.set(i, updatedPlayer);
                break;
            }
        }
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), players);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Player findById(String playerID) {
         List<Player> players = loadPlayers();
        for (Player player : players) {
            if (player.getAppId().equals(playerID)) {
                return player;
            }
        }
        return null; // not found
    }
}
  /*  public static List<Player> loadPlayers() {
    try {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            // Create a new file with empty JSON array
            file.createNewFile();
            mapper.writeValue(file, new ArrayList<Player>());
        }
        // Read existing players
        return mapper.readValue(file, new TypeReference<List<Player>>() {});
    } catch (Exception e) {
        e.printStackTrace();
        return new ArrayList<>();
    }
}

}*/




// Find player by ID
   // public static Player findById(String appId) {
     //   return loadPlayers().stream()
       //         .filter(p -> p.getAppId().equals(appId))
         //       .findFirst()
           //     .orElse(null);
    //}