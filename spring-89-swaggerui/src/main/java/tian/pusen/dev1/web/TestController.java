package tian.pusen.dev1.web;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import tian.pusen.dev1.entity.Pet;
import tian.pusen.dev1.entity.User;
import tian.pusen.dev1.web.resp.RespCollections;
import tian.pusen.dev1.web.resp.Response;
import tian.pusen.dev1.util.Generator;

import java.util.ArrayList;
import java.util.List;

@Profile("dev1")
@RestController
public class TestController {
    @GetMapping("/pets/{id}")
    public Response getPet(@PathVariable Integer id) {
        Pet pet = Generator.generatorPet();
        pet.setId(id);
        return new Response(pet);
    }
    @GetMapping("/pets")
    public RespCollections getPets() {
        List<Pet> petList = new ArrayList<>();
        for(int i=0; i< 5; ++i) {
            Pet pet = Generator.generatorPet();
            petList.add(pet);
        }
        return new RespCollections(petList);
    }

    @ApiOperation(value="获取用户列表", notes="")
    @GetMapping(value="/users")
    public List<User>  getUserList() {
        // 处理"/users/"的GET请求，用来获取用户列表
        // 还可以通过@RequestParam从页面中传递参数来进行查询条件或者翻页信息的传递
        List<User> userList = new ArrayList<User>();
        for(int i=0; i< 3; ++i) {
            User user = Generator.generatorUser();
            userList.add(user);
        }
        return userList;
    }


}
