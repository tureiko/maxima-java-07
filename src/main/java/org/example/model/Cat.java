package org.example.model;

public class Cat {
    private String name;
    private int weight;
    private boolean isAngry;
    private Long id;



    public Cat(String name, int weight, boolean isAngry,Long id) {
        this.name = name;
        this.weight = weight;
        this.isAngry = isAngry;
        this.id = id;
    }

    /*protected Cat() {

    }*/
   /* public <T> Cat(String name, int weight, T id) {
        this.name = name;
        this.weight = weight;
        this.id = Long.valueOf(id.toString());
    }*/
 /*   public Cat(String name, int weight) throws Exception {
        if (weight <0) {
            // throw new IncorrectCatWeightException("Неправильный кот");
        }
        this.name = name;
        this.weight = weight;
    }*/

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", weight=" + weight +
                ", isAngry=" + isAngry +
                ", id=" + id +
                '}';
    }

/* public Cat(String name, int weight, boolean isAngry) throws Exception {
        if (weight <0) {
            // throw new IncorrectCatWeightException("Неправильный кот");
        }
        this.name = name;
        this.weight = weight;
        this.isAngry = isAngry;
    }*/

    public String getName() {return name;}

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
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
