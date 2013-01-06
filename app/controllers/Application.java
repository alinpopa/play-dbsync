package controllers;

import com.google.common.collect.ImmutableList;
import models.source.Task;
import org.codehaus.jackson.JsonNode;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

import static play.libs.Json.fromJson;
import static play.libs.Json.toJson;

public class Application extends Controller {

    public static Result index() {
        return redirect(routes.Application.tasks());
    }

    public static Result tasks() {
        return ok(toJson(ImmutableList.of(Task.all(), models.destination.Task.all())));
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static Result newTask(){
        final JsonNode jsonNode = request().body().asJson();
        if(jsonNode == null){
            return badRequest("Expecting Json data");
        }
        models.destination.Task.create(fromJson(jsonNode, models.destination.Task.class));
        Task.create(fromJson(jsonNode, Task.class));

        System.out.println("Source tasks: " + Task.all());
        System.out.println("Destination tasks: " + models.destination.Task.all());
        return ok("ok");
    }

    public static Result deleteTask(Long id){
        Task.delete(id);
        models.destination.Task.delete(id);
        return redirect(routes.Application.tasks());
    }
}