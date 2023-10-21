import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.annotation.Id;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import java.util.List;

@SpringBootApplication
public class PeopleManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(PeopleManagementApplication.class, args);
    }
}

@Entity
class Person {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int age;

    public Person() {
    }

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

interface PersonRepository extends JpaRepository<Person, Long> {
}

@RestController
@RequestMapping("/people")
class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<Person> getPeople() {
        return personRepository.findAll();
    }

    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        return personRepository.save(person);
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable Long id, @RequestBody Person updatedPerson) {
        Person person = personRepository.findById(id).orElse(null);
        if (person != null) {
            person.setName(updatedPerson.getName());
            person.setAge(updatedPerson.getAge());
            return personRepository.save(person);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void deletePerson(@PathVariable Long id) {
        personRepository.deleteById(id);
    }
}
