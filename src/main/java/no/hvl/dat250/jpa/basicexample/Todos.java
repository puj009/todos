package no.hvl.dat250.jpa.basicexample;

import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

public class Todos {
    private static final String PERSISTENCE_UNIT_NAME = "todos";
    private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    EntityManager em = factory.createEntityManager();

    private HashMap todos = new HashMap();



    public String getAll(){
        Query q = em.createQuery("select t from Todo t");
        List<Todo> todoList = q.getResultList();
        for (Todo todo : todoList) {
            todos.put(todo.getId(), todo.getDescription());
        }
        Gson gson = new Gson();

        String jsonInString = gson.toJson(todos);

        return jsonInString;
    }

    public String getID(Long id){
        Query er = em.createQuery("select t.id from Todo t");
        List<Long> idList = er.getResultList();
        Gson gson = new Gson();
        if (!idList.contains(id)) {
            return gson.toJson("Ups, no result for this id.");
        }
        Query q = em.createQuery("update Todo t set where t.id = " + id);
        List<Todo> todoList = q.getResultList();
        todos.put(todoList.get(0).getId(), todoList.get(0).getDescription());
        String jsonInString = gson.toJson(todos);

        return jsonInString;
    }

    public String add (Todo todo) {
        Todo todo1 = new Todo();
        todo1.setDescription(todo.getDescription());
        todo1.setSummary(todo.getSummary());

        Gson gson = new Gson();
        em.persist(todo1);
        em.getTransaction().commit();
        return gson.toJson(todo1);
    }

    public String updateById(Long id, String description, String summary){
        Query er = em.createQuery("select t.id from Todo t");
        List<Long> idList = er.getResultList();
        Gson gson = new Gson();
        if (!idList.contains(id)) {
            return gson.toJson("Ups, no result for this id.");
        }

        Query q = em.createQuery("update Todo t set t.description, t.summary where t.id = " + id);
        Todo todo = (Todo) q.getResultList().get(0);

        todo.setDescription(description);
        todo.setSummary(summary);
        String jsonInString = gson.toJson(todos);

        return jsonInString;
    }

    public String deleteId(Long id) {
        Query er = em.createQuery("select t.id from Todo t");
        List<Long> idList = er.getResultList();
        Gson gson = new Gson();
        if (!idList.contains(id)) {
            return gson.toJson("Ups, no result for this id.");
        }

        Query q = em.createQuery("delete t from Todo t where t.id = " + id);

        return gson.toJson("Deleted Todo with id = " + id);
    }
}
