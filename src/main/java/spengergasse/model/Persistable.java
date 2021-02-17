package spengergasse.model;

public abstract class Persistable {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isNew(){
        return id==null;
    }
}
