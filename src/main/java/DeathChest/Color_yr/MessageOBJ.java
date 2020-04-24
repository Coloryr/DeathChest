package DeathChest.Color_yr;

public class MessageOBJ {
    private String CantPlace;
    private String CantGen;
    private String Cost;
    private String Cost1;
    private String Cost2;
    private String NoMoney;
    private String NoMoney1;
    private String NoMoney2;
    private String ErrorChest;
    private String Mode;
    private String Gen;

    public MessageOBJ() {
        CantPlace = "§d[DeathChest]§c你有一些物品无法放进箱子";
        CantGen = "§d[DeathChest]§c无法生成箱子";
        Gen = "§d[DeathChest]§e你的死亡箱子生成在了%x%, %y%, %z%";
        Cost = "§d[DeathChest]§e生成死亡箱子花费了%Money%";
        Cost1 = "§d[DeathChest]§e保存到设置的箱子花费了%Money%";
        Cost2 = "§d[DeathChest]§e死亡不掉落花费了%Money%";
        NoMoney = "§d[DeathChest]§c你没有足够的钱生成死亡箱子";
        NoMoney1 = "§d[DeathChest]§c你没有足够的钱保存到设置的箱子";
        NoMoney2 = "§d[DeathChest]§c你没有足够的钱生成死亡箱子";
        ErrorChest = "§d[DeathChest]§c设置的箱子异常";
        Mode = "§d[DeathChest]§e你的死亡掉落保护模式为：%Mode%";
    }
    
    public String getGen() {
        return Gen;
    }

    public String getMode() {
        return Mode;
    }

    public String getNoMoney2() {
        return NoMoney2;
    }

    public String getNoMoney1() {
        return NoMoney1;
    }

    public String getCost2() {
        return Cost2;
    }

    public String getErrorChest() {
        return ErrorChest;
    }

    public String getCost1() {
        return Cost1;
    }

    public String getNoMoney() {
        return NoMoney;
    }

    public String getCost() {
        return Cost;
    }

    public String getCantGen() {
        return CantGen;
    }

    public String getCantPlace() {
        return CantPlace;
    }
}
