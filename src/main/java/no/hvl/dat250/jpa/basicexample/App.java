package no.hvl.dat250.jpa.basicexample;

import com.google.gson.Gson;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class App {
	
	static Todos todos = null;

	
	public static void main(String[] args) {

		if (args.length > 0) {
			port(Integer.parseInt(args[0]));
		} else {
			port(8081);
		}

		todos = new Todos();
		
		after((req, res) -> {
  		  res.type("application/json");
  		});

		// get all todos
        get("/todos", (req, res) -> todos.getAll());

        // get a todo with given id.
        get("/todos/:id", (req, res) -> todos.getID(Long.parseLong(req.params(":id"))));

        // add a todo
        post("/todos/add", (req, res) -> {
        	Gson gson = new Gson();
        	Todo todo = gson.fromJson(req.body(), Todo.class);
            return todos.add(todo);
        });

        // update todo with given id
        put("/todo/:id", (req, res) -> {
			Gson gson = new Gson();
			Todo todo = gson.fromJson(req.body(), Todo.class);
			return todos.updateById(Long.parseLong(req.params("id")), todo.getDescription(), todo.getSummary());
		});

        // delete todo with given id
		delete("/todos/:id", (req, res) -> todos.deleteId(Long.parseLong(req.params("id"))));
    }
}
