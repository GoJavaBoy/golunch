package co.uk.golunch.model;

abstract public class AbstractBaseEntity {
    protected Integer id;
    protected String name;

    public AbstractBaseEntity() {
    }

    public AbstractBaseEntity(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public int id() {
//        Assert.notNull(id, "Entity must have id");
//        return id;
//    }

    public boolean isNew() {
        return this.id == null;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " id: " + id + " name: " + name;
    }
}
