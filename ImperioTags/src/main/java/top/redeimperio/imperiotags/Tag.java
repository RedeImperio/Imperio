package top.redeimperio.imperiotags;

public class Tag {
    private final String name;
    private final String prefix;
    private final String chatColor;

    public Tag(String name, String prefix, String chatColor) {
        this.name = name;
        this.prefix = prefix;
        this.chatColor = chatColor;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getChatColor() {
        return chatColor;
    }
}
