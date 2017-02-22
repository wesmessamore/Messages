import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    static ArrayList<String> messages = new ArrayList<>();
    static User user;

    public static void main(String[] args) {
        Spark.init();

        Spark.get("/",
                (request, response) -> {
                    HashMap m = new HashMap();


                    if (user == null) {
                        return new ModelAndView(m, "login.html");
                    } else {
                        m.put("name", user.name);
                        m.put("messages", messages);
                        return new ModelAndView(m, "messages.html");
                    }
                },
                new MustacheTemplateEngine()
        );

        Spark.post("/create-user", (request, response) -> {
            String name = request.queryParams("userName");

            user = new User(name);

            response.redirect("/");
            return "";
        });

        Spark.post("/create-message", (request, response) -> {
            String message = request.queryParams("message");
            messages.add(message);
            response.redirect("/");
            return "";
        });
    }
}
