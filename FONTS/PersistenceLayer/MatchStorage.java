package PersistenceLayer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

import DomainLayer.DomainClasses.*;

/**
 * MatchStorage is a class responsible for saving and loading match data.
 * It implements the Storage interface to provide methods for saving and loading matches.
 * @author Kai Knox
 */
public class MatchStorage implements Storage<Map<String, Match>> {
    private final ProfileController profileController = ProfileController.getInstance();

    public void save(Map<String, Match> matches) {
        JSONArray matchesArray = new JSONArray();
        for (Map.Entry<String, Match> match : matches.entrySet()) {
            Match matchValue = match.getValue();
            JSONObject matchObject = new JSONObject();
 
            matchObject.put("id", matchValue.getId());
            matchObject.put("dictionary", matchValue.getDictionaryName());
            matchObject.put("size", matchValue.getSize());
            matchObject.put("boardSize", matchValue.getBoard().getSize());
            matchObject.put("current_turn", matchValue.getTurn());
            matchObject.put("score", matchValue.getScore());
            matchObject.put("skipCount", matchValue.getSkipCount());

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
                    JSONObject boxObject = new JSONObject();
                    boxObject.put("x", box.getColumn());
                    boxObject.put("y",  box.getRow());
                    boxObject.put("symbol", box.getSymbol());
                    boxObject.put("value", box.getValue());
                    
                    JSONArray crosscheckVArray = new JSONArray();
                    crosscheckVArray.addAll(box.getCrossCheck(0));
                    boxObject.put("crosscheck_v", crosscheckVArray);

                    JSONArray crosscheckHArray = new JSONArray();
                    crosscheckHArray.addAll(box.getCrossCheck(1));
                    boxObject.put("crosscheck_h", crosscheckHArray);
                    boardArray.add(boxObject);
                }
            }
            matchObject.put("board", boardArray);

            matchesArray.add(matchObject);
        }

        JsonUtils.save("/matches.json", matchesArray);
    }

    
    public Map<String, Match> load() {
        Map<String, Match> matches = new HashMap<>();
        JSONArray matchesArray = (JSONArray) JsonUtils.load("/matches.json");

        for (Object obj : matchesArray) {
            JSONObject matchObject = (JSONObject) obj;

            String id = (String) matchObject.get("id");
            String dictionary = (String) matchObject.get("dictionary");
            int size = ((Long) matchObject.get("size")).intValue();
            int boardSize = ((Long) matchObject.get("boardSize")).intValue();
            int currentTurn = ((Long) matchObject.get("current_turn")).intValue();
            int score = ((Long) matchObject.get("score")).intValue();
            int skipCount = ((Long) matchObject.get("skipCount")).intValue();
            Match match = new Match(id, size, dictionary);

            match.setScore(score);
            match.setSkipCount(skipCount);

            // Load bag
            Map<Letter, Integer> bagMap = new HashMap<>();
            int totalLetters = 0;
            JSONArray bagArray = (JSONArray) matchObject.get("bag");
            for (Object bagObj : bagArray) {
                JSONObject tileObject = (JSONObject) bagObj;
                String symbol = (String) tileObject.get("symbol");
                int value = ((Long) tileObject.get("value")).intValue();
                int count = ((Long) tileObject.get("count")).intValue();
                Letter letter = new Letter(symbol, value);
                bagMap.put(letter, count);
                totalLetters += count;
            }
            Bag bag = new Bag(bagMap, totalLetters);

            match.setBag(bag);
            // Load players
            JSONArray playersArray = (JSONArray) matchObject.get("players");
            for (Object playerObj : playersArray) {
                JSONObject playerObject = (JSONObject) playerObj;
                String playerId = (String) playerObject.get("id");
                String name = (String) playerObject.get("name");
                int playerScore = ((Long) playerObject.get("score")).intValue();
                String type = (String) playerObject.get("type");

                Player player;
                if (type.equals("Human")) {
                    Profile profile = profileController.getProfile(name);
                    player = new Human(playerId, profile);
                } else {
                    player = new IA(playerId, name);
                }
                player.setScore(playerScore);

                // Load rack
                JSONArray rackArray = (JSONArray) playerObject.get("rack");
                Rack rack = new Rack(bag, false);
                for (Object rackObj : rackArray) {
                    JSONObject tileObject = (JSONObject) rackObj;
                    String symbol = (String) tileObject.get("symbol");
                    int value = ((Long) tileObject.get("value")).intValue();
                    rack.addLetter(new Letter(symbol, value));
                }

                player.setRack(rack);
                match.setPlayer(player);
            }
            match.setTurn(currentTurn);

            // Load board
            Board board = new Board(boardSize);
            JSONArray boardArray = (JSONArray) matchObject.get("board");
            for (Object boardObj : boardArray) {
                JSONObject boxObject = (JSONObject) boardObj;
                int x = ((Long) boxObject.get("x")).intValue();
                int y = ((Long) boxObject.get("y")).intValue();
                String symbol = (String) boxObject.get("symbol");
                int value = ((Long) boxObject.get("value")).intValue();


                Box box = board.getBox(y, x);
                JSONArray crosscheckVArray = (JSONArray) boxObject.get("crosscheck_v");
                JSONArray crosscheckHArray = (JSONArray) boxObject.get("crosscheck_h");
                Set<String> crosscheckV = new HashSet<>();
                for (Object vObj : crosscheckVArray) {
                    crosscheckV.add((String) vObj);
                }
                Set<String> crosscheckH = new HashSet<>();
                for (Object hObj : crosscheckHArray) {
                    crosscheckH.add((String) hObj);
                }
                box.setCrossCheck(crosscheckH, crosscheckV);


                if (symbol != null && !symbol.isEmpty()) {
                    board.placeLetter(y, x, symbol, value);
                }
            }
            match.setBoard(board);
            matches.put(id, match);
        }

        return matches;
    }
}
