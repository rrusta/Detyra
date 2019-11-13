package org.auk.net;

import com.google.gson.Gson;
import org.auk.data.ArticleDao;
import org.auk.models.Article;
import spark.ModelAndView;
import spark.ResponseTransformer;
import spark.template.handlebars.HandlebarsTemplateEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.auk.utils.Utils.print;
import static spark.Spark.*;

public class DetyraServer {

    private final static int PORT = 4567;

    public @interface ServerType {
        int SOCKET = 1;
        int SPARK = 2;
    }

    public DetyraServer(@ServerType int type) throws IOException {
        switch (type) {
            case ServerType.SOCKET -> init();
            case ServerType.SPARK -> initWithRoutes();
        }

    }

    private void initWithRoutes() {
        get("/hello", (req, res) -> "Hello Spark, my Love!");
        get("/hello/:name", (req, res) -> {
            String name = req.params(":name");
            return "Hello " + name;
        });

        get("/article", (request, response) -> {
            Article article = null;
            Map<String, Object> model = new HashMap<>();
            model.put("title", "Test");
            return new HandlebarsTemplateEngine().render(
                    new ModelAndView(model, "index.hbs")
            );
        });

        path("/articles", () -> {
            Gson gson = new Gson();
            get("/", "application/json", (res, req) -> new ArticleDao().getAll(), gson::toJson);
            get("/:slug", "application/json", (req, res) -> {
                //  String articleSlug = req.params(":slug");
                //   <a href></a>
                return new ArticleDao().findBySlug(req.params(":slug"));
            }, new JsonTransformer());
        });


        notFound("Hope you'll be luckier next time!");
    }

    private void init() throws IOException {
        final ServerSocket server = new ServerSocket(PORT);
        System.out.println("Server is listening on port " + PORT);

        while (true) {
            try (final Socket client = server.accept()) {
                PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

                // START of the received headers
                print("");
                String line = reader.readLine();
                while (!line.isEmpty()) {
                    System.out.println(line);
                    line = reader.readLine();
                }
                // END of the received headers

                print("");
                // Send the response
                // Send the headers
                print("HTTP/1.0 200 OK");
                print("Content-Type: text/html");
                print("Server: TechUp");
                // this blank line signals the end of the headers
                print("");
                // Send the HTML page
                print("<H1>Welcome to the Ultra Mini-WebServer</H2>");
                out.flush();

                Date today = new Date();
                String httpResponse = "HTTP/1.1 200 OK\r\n\r\n" + today;
                client.getOutputStream().write(httpResponse.getBytes(StandardCharsets.UTF_8));
            } catch (IOException ex) {
                System.out.println("Server exception: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }

    private static class JsonTransformer implements ResponseTransformer {

        private Gson gson = new Gson();

        @Override
        public String render(Object model) {
            return gson.toJson(model);
        }

    }
}
