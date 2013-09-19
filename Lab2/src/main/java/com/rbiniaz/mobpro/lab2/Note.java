package com.rbiniaz.mobpro.lab2;

/**
 * Created by rachel on 9/18/13.
 */
public class Note {
    private long id;
    private String name;
    private String contents;

    public Note(long id, String name, String contents) {
        this.id = id;
        this.name = name;
        this.contents = contents;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contents='" + contents + '\'' +
                '}';
    }
}
