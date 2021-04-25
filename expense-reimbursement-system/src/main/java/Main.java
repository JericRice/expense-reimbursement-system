
import frontcontroller.FrontController;
import io.javalin.Javalin;

/**
 * init
 * */
public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create(javalinConfig -> {
            javalinConfig.addStaticFiles("/webapp");
        }).start(9001).error(404,ctx -> {
            ctx.redirect("/404"); // not functional < -- Fix
        });

        FrontController frontController = new FrontController(app);
    }
}
