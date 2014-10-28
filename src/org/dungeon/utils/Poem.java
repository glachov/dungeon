package org.dungeon.utils;

/**
 * Poem class that defines a data structure to store poems. This class is immutable.
 * This class' only static method is used to
 * <p/>
 * Created by Bernardo Sulzbach on 28/10/2014.
 */
public class Poem {

    private final String title;
    private final String author;
    private final String content;

    Poem(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}
