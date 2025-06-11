package examen.restservices;

import examen.persistence.repository.jdbc.UserRepositoryJDBC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("examen/requests")
public class CompanieRestController {
//    @Autowired
//    private UserRepositoryJDBC repositoryJDBC;

    @GetMapping("/test")
    public  String test(@RequestParam(value="name", defaultValue="Hello") String name) {
        return name.toUpperCase();
    }

//    @PostMapping
//    public Zbor create(@RequestBody Zbor zbor){
//        System.out.println("Creating zbor");
//        Zbor saved = repository.add(zbor);
//        messagingTemplate.convertAndSend(
//                "/topic/zbor-created",
//                saved
//        );
//        return saved;
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getById(@PathVariable Integer id){
//        System.out.println("Get by id "+id);
//        Zbor request=repository.findOne(id);
//        if (request==null)
//            return new ResponseEntity<String>("Entity not found", HttpStatus.NOT_FOUND);
//        else
//            return new ResponseEntity<Zbor>(request, HttpStatus.OK);
//    }
//
//    @GetMapping
//    public Collection<Zbor> filterByStatus(@RequestParam(value="destinatie", required=false)String destinatie, @RequestParam (value="dataPlecarii", required=false)String dataPlecarii) {
//        Collection<Zbor> all;
//        if(dataPlecarii!=null && destinatie!=null){
//            System.out.println("Filtering zboruri by dataPlecarii "+ dataPlecarii + " destinatie "+ destinatie);
//            all= (Collection<Zbor>) repository.filter(destinatie, dataPlecarii);
//        } else{
//            all= (Collection<Zbor>) repository.getAll();
//        }
//        return all;
//
//    }
}
