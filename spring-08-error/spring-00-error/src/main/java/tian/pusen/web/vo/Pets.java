package tian.pusen.web.vo;

import tian.pusen.entity.Pet;
import tian.pusen.web.resp.RespBody;

import java.util.List;

public class Pets implements RespBody {
    private List<Pet> pets;

    public List<Pet> getPets() {
        return pets;
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
