package org.acme;

import java.util.List;

public class PojoWrapper {

    List<Pojo> pojos;

    public List<Pojo> getPojos() {
        return pojos;
    }

    public void setPojos(List<Pojo> pojos) {
        this.pojos = pojos;
    }

    @Override
    public String toString() {
        return "PojoWrapper{" +
                "pojos=" + pojos +
                '}';
    }
}
