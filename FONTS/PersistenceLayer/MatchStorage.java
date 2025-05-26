package PersistenceLayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

import DomainLayer.DomainClasses.*;


public class MatchStorage implements Storage<Map<String, Match>> {
    private final ProfileController profileController = ProfileController.getInstance();

    public void save(Map<String, Match> matches) {
        JSONArray matchesArray = new JSONArray();
        for (Map.Entry<String, Match> match : matches.entrySet()) {
            Match matchValue = match.getValue();
            JSONObject matchObject = new JSONObject();
 
            matchObject.put("id", matchValue.getId());
            matchObject.put("dictionary", matchValue.getDictionary());
            matchObject.put("size", matchValue.getSize());
            matchObject.put("current_turn", matchValue.getTurn());

            // Save bag
            JSONArray bagArray = new JSONArray();
            for (Map.Entry<Letter, Integer> entry : matchValue.getBag().getLetters().entrySet()) { // Assuming the map key is a String
                Letter tile = entry.getKey();
                int count = entry.getValue();
                JSONObject tileObject = new JSONObject();
                tileObject.put("symbol", tile.getSymbol());
                tileObject.put("value", tile.getValue());
                tileObject.put("count", count);
                bagArray.add(tileObject);
            }
            matchObject.put("bag", bagArray);

            // Save players
            JSONArray playersArray = new JSONArray();
            for (Map.Entry<String,Player> entry : matchValue.getPlayers().entrySet()) { // Assuming getPlayers() returns a List or Set of Player
                Player player = entry.getValue();
                JSONObject playerObject = new JSONObject();
                playerObject.put("id", player.getID());
                playerObject.put("name", player.getName());
                playerObject.put("score", player.getScore());
                playerObject.put("type", player.isHuman() ? "Human" : "AI");

                // Save rack
                JSONArray rackArray = new JSONArray();
                for (Letter letter : player.getRack().getLetters()) { // Assuming getTiles() returns a List or Set of Box
                    JSONObject tileObject = new JSONObject();
                    tileObject.put("symbol", letter.getSymbol());
                    tileObject.put("value", letter.getValue());
                    rackArray.add(tileObject);
                }
                playerObject.put("rack", rackArray);
                playersArray.add(playerObject);
            }
            matchObject.put("players", playersArray);


            // Save board
            JSONArray boardArray = new JSONArray();
            Board board = matchValue.getBoard();
            for (int i = 0; i < board.getSize(); i++) {
                for (int j = 0; j < board.getSize(); j++) {
                    Box box = board.getBox(i, j);
                    if (box != null && !box.isEmpty()) {
                    JSONObject boxObject = new JSONObject();
                    boxObject.put("x", box.getRow());
                    boxObject.put("y", box.getColumn());
                    boxObject.put("symbol", box.getSymbol());
                    boxObject.put("value", box.getValue());
                    boxObject.put("crosscheck_v", box.getCrossCheck(0));
                    boxObject.put("crosscheck_h", box.getCrossCheck(1));
                    boardArray.add(boxObject);
                    }
                }
            }
            matchObject.put("board", boardArray);

            matchesArray.add(matchObject);
        }

        JsonUtils.save("/matches.json", matchesArray);
    }

    public Map<String, Match> load() {
        Map<String, Match> matches = new HashMap<>();
        JSONArray matchesArray = JsonUtils.load( "/matches.json");

        for (Object obj : matchesArray) {
            JSONObject matchObject = (JSONObject) obj;

            String id = (String) matchObject.get("id");
            String dictionary = (String) matchObject.get("dictionary");
            int size = (int) matchObject.get("size");
            int currentTurn = (int) matchObject.get("current_turn");

            Match match = new Match(id, size);

            // Load bag
            Map<Letter, Integer> bagMap = new HashMap<>();
            int totalLetters = 0;
            JSONArray bagArray = (JSONArray) matchObject.get("bag");
            for (Object bagObj : bagArray) {
                JSONObject tileObject = (JSONObject) bagObj;
                String symbol = (String) tileObject.get("symbol");
                long value = (long) tileObject.get("value");
                long count = (long) tileObject.get("count");
                Letter letter = new Letter(symbol, (int) value);
                bagMap.put(letter, (int) count);
                totalLetters += count;
            }
            Bag bag = new Bag(bagMap, totalLetters);

            // Load players
            List<Player> players = new ArrayList<>();
            JSONArray playersArray = (JSONArray) matchObject.get("players");
            for (Object playerObj : playersArray) {
                JSONObject playerObject = (JSONObject) playerObj;
                String playerId = (String) playerObject.get("id");
                String name = (String) playerObject.get("name");
                long score = (long) playerObject.get("score");
                String type = (String) playerObject.get("type");

                Player player;
                if (type.equals("Human")) {
                    Profile profile = profileController.getProfile(playerId);
                    player = new Human(playerId, profile, dictionary);
                } else {
                    player = new IA(playerId, name);
                }
                player.setScore((int) score);

                // Load rack
                JSONArray rackArray = (JSONArray) playerObject.get("rack");
                Rack rack = new Rack(bag);
                for (Object rackObj : rackArray) {
                    JSONObject tileObject = (JSONObject) rackObj;
                    String symbol = (String) tileObject.get("symbol");
                    long value = (long) tileObject.get("value");
                    rack.addLetter(new Letter(symbol, (int) value));
                }

                player.setRack(rack);
                match.setPlayer(player);
            }
            match.setTurn(currentTurn);


            // Load board
            Board board = new Board(size);
            JSONArray boardArray = (JSONArray) matchObject.get("board");
            for (Object boardObj : boardArray) {
                JSONObject boxObject = (JSONObject) boardObj;
                int x = (int) (long) boxObject.get("x");
                int y = (int) (long) boxObject.get("y");
                String symbol = (String) boxObject.get("symbol");
                long value = (long) boxObject.get("value");

                if (symbol != null && !symbol.isEmpty()) {
                    board.placeLetter(x, y, symbol, (int) value);
                }
            }

            matches.put(id, match);
        }

        return matches;
    }
}
