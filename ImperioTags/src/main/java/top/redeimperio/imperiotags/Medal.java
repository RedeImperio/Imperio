package top.redeimperio.imperiotags;

public class Medal {
    private final String name;
    private final String prefix;

    public Medal(String name, String prefix) {
        this.name = name;
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

}
