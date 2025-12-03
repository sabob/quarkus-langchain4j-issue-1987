package org.acme;

public class Pojo {

    private String name;

    public String getName() {
        return name;
    }

    public Pojo setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "Pojo{" +
                "name='" + name + '\'' +
                '}';
    }
}
