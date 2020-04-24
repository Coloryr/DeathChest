package DeathChest.Color_yr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigOBJ {
    private String Version;
    private Cost Cost;
    private Map<String, PlaySet> PlaySet;
    private List<Integer> Disable;
    private MessageOBJ Message;

    public ConfigOBJ() {
        Version = DeathChest.Version;
        Cost = new Cost();
        PlaySet = new HashMap<>();
        Disable = new ArrayList<>();
        Message = new MessageOBJ();
    }

    public MessageOBJ getMessage() {
        return Message;
    }

    public List<Integer> getDisable() {
        return Disable;
    }

    public String getVersion() {
        return Version;
    }

    public Cost getCost() {
        return Cost;
    }

    public boolean havePlaySet(String player) {
        return PlaySet.containsKey(player);
    }

    public PlaySet getPlayerSet(String player) {
        return PlaySet.get(player);
    }

    public void setPlayerSet(String player, PlaySet set) {
        PlaySet.put(player, set);
        DeathChest.save();
    }
}
class Cost {
    private int SaveInChest;
    private int SaveInLocal;
    private int NoDrop;
    private boolean Enable;

    public boolean isEnable() {
        return Enable;
    }

    public Cost() {
        Enable = true;
        SaveInChest = 100;
        SaveInLocal = 50;
        NoDrop = 500;
    }

    public int getNoDrop() {
        return NoDrop;
    }

    public int getSaveInChest() {
        return SaveInChest;
    }

    public int getSaveInLocal() {
        return SaveInLocal;
    }
}