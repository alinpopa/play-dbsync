package models.destination;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

@Entity
public class Task extends Model {
    @Id
    public Long id;

    @Required
    public String label;

    public static Finder<Long, Task> find = new Finder<Long, Task>("destination", Long.class, Task.class);

    public static List<Task> all(){
        return find.all();
    }

    public static void create(Task task){
        task.save("destination");
    }

    public static void delete(Long id){
        find.ref(id).delete("destination");
    }
}
