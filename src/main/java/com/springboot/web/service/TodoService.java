package com.springboot.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.springboot.web.model.Todo;

//Service is used before the repository was created

//*


@Service
public class TodoService {
    private static List<Todo> todos = new ArrayList<Todo>();
    private static int todoCount = 3;
// ArrayList created to have few examples when database is not yet created 
    static {
        todos.add(new Todo(1, "myca", "Learn Spring MVC", new Date(),
                false));
        todos.add(new Todo(2, "myca", "Learn Struts", new Date(), false));
        todos.add(new Todo(3, "myca", "Learn Hibernate", new Date(),
                false));
    }

    public List<Todo> retrieveTodos(String user) {
        List<Todo> filteredTodos = new ArrayList<Todo>();
        for (Todo todo : todos) {
            if (todo.getUser().equalsIgnoreCase(user)) {
                filteredTodos.add(todo);
            }
        }
        return filteredTodos;
    }
    
    public Todo retrieveTodo(int id) {
        for (Todo todo : todos) {
            if (todo.getId()==id) {
                return todo;
            }
        }
        return null;
    }

    public void updateTodo(Todo todo){
    		todos.remove(todo);
    		todos.add(todo);
    }

    public void addTodo(String name, String desc, Date targetDate,
            boolean isDone) {
        todos.add(new Todo(++todoCount, name, desc, targetDate, isDone));
    }

    public void deleteTodo(int id) {
        Iterator<Todo> iterator = todos.iterator();
        while (iterator.hasNext()) {
            Todo todo = iterator.next();
            if (todo.getId() == id) {
                iterator.remove();
            }
        }
    }
    public String getLoggedUserName(ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}

		return principal.toString();
	}
}