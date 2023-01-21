package org.example.model;

public class Cat {

    private String name;
    private int weight;
    private boolean isAngry;
    private Long id;
    public Cat(String name, int weight, boolean isAngry, Long id ) {
        this.name = name;
        this.weight = weight;
        this.isAngry = isAngry;
        this.id = id;
    }


    protected Cat() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isAngry() {
        return isAngry;
    }

    public void setAngry(boolean angry) {
        isAngry = angry;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", isAngry=" + isAngry +
                '}';
    }
}
