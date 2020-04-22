package tian.pusen.web;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tian.pusen.entity.Pet;
import tian.pusen.err.BusinessException;
import tian.pusen.err.ServiceCode;
import tian.pusen.util.Generator;
import tian.pusen.web.resp.RespBody;
import tian.pusen.web.resp.Response;
import tian.pusen.web.vo.Pets;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
    @GetMapping("/execute")
    public Response execute() {
        return new Response();
    }

    @GetMapping("/pets/{id}")
    public Response<Pet> getPet(@PathVariable Integer id) {
        Pet pet = Generator.generatorPet();
        pet.setId(id);
        return new Response(pet);
    }

    @GetMapping("/pets")
    public Response<Pets> getPets() {
        List<Pet> petList = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            Pet pet = Generator.generatorPet();
            petList.add(pet);
        }

        Pets pets = new Pets();
        pets.setPets(petList);
        return new Response(pets);
    }

    @GetMapping("/divedby0")
    public Response compute() {
        Response response = new Response();
        int i = 10/0;
        return response;
    }
    @GetMapping("/businessexecption")
    public Response busi() {
        Response response = new Response();
        if(true) {
            throw new BusinessException(ServiceCode.DATEBASE_CONNECT_ERROR);
        }
        return response;
    }
}
