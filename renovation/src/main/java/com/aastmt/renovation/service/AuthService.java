import org.springframework.stereotype.Service;

import com.aastmt.renovation.dto.UserLoginDto;
import com.aastmt.renovation.dto.UserRegisterDto;
import com.aastmt.renovation.repository.UserRepository;
import com.aastmt.renovation.repository.WorkerRepository;
@Service
public class AuthService{
     


    private final UserRepository userrepo;
    private final WorkerRepository workerrepo;

    public AuthService(UserRepository userrepo, WorkerRepository workerrepo) {
        this.userrepo = userrepo;
        this.workerrepo = workerrepo;
    }
    
     public boolean register(UserRegisterDto DtoObj){
        //Check if the user's/worker's username,email,phone number exists in the database
        if(userrepo.existsByUsername(DtoObj.getUsername()) || workerrepo.existsByUsername(DtoObj.getUsername()) )
          return false;
        else if(userrepo.existsByEmail(DtoObj.getEmail()) || workerrepo.existsByEmail(DtoObj.getEmail()) )
          return false;
        else if(userrepo.existsByPhoneNumber(DtoObj.getPhonenumber()) || workerrepo.existsByPhoneNUmber(DtoObj.getUsername()) )
          return false;
        
        //after validation create user and save it in the database
        if (dtoObj.getRole() == Role.user) {
        
        User newUser = new User();
        
        //assign the data from dto object to the new user obj 
        newUser.setFname(dtoObj.getFname());
        newUser.setLname(dtoObj.getLname());
        newUser.setUsername(dtoObj.getUsername());
        newUser.setPassword(dtoObj.getPassword()); 
        newUser.setPhonenumber(dtoObj.getPhonenumber());
        newUser.setRole(dtoObj.getRole());
        
        //save the data in database
        userrepo.save(newUser);
        //same but for worker obviously
    } else if (dtoObj.getRole() == Role.worker) {
        
        Worker newWorker = new Worker();
        
        
        newWorker.setFname(dtoObj.getFname());
        newWorker.setLname(dtoObj.getLname());
        newWorker.setUsername(dtoObj.getUsername());
        newWorker.setPassword(dtoObj.getPassword()); 
        newWorker.setPhonenumber(dtoObj.getPhonenumber());
        newWorker.setRole(dtoObj.getRole());
        
       
        newWorker.setProfession(dtoObj.getProfession()); 
        
        
        workerrepo.save(newWorker);
    }

      return true;
     }


     public boolean login(UserLoginDto dtoObj) {
        //check login data if it is in the database or not
        if (dtoObj.getRole() == Role.user) {
            return userrepo.existsByUsernameAndPassword(dtoObj.getUsername(), dtoObj.getPassword());
        } 
        else if (dtoObj.getRole() == Role.worker) {
            return workerrepo.existsByUsernameAndPassword(dtoObj.getUsername(), dtoObj.getPassword());
        }
        
        return false;
    
    }



        

}