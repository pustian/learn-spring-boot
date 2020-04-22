package tian.pusen.util;

import net.bytebuddy.utility.RandomString;
import org.springframework.context.annotation.Profile;
import tian.pusen.entity.Pet;
import tian.pusen.entity.User;

import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class Generator {
    public static Pet generatorPet() {
        Random random = new Random();
        Pet pet = new Pet();
        pet.setId(random.nextInt());
        pet.setSpecial(RandomString.make(6));
        pet.setName(RandomString.make(6));
        pet.setWeight(random.nextDouble());
        return pet;
    }
    public static User generatorUser() {
        Random random = new Random();
        User user = new User();
        user.setId(random.nextInt());
        user.setUsername(RandomString.make(6));
        user.setAddress(RandomString.make(20));
        user.setFamele(random.nextInt() % 2 == 0);
        user.setBirthday(new Date(random.nextInt()));
        Pet[] pets = new Pet[]{generatorPet(), generatorPet()};
        user.setPetList(Arrays.asList(pets));
        return user;
    }
}
