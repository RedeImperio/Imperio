package top.redeimperio.imperiotags;

public class Tag {
    private final String name;
    private final String prefix;

    public Tag(String name, String prefix) {
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
