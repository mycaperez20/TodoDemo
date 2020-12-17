package com.springboot.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.springboot.web.model.Todo;
import com.springboot.web.repository.TodoRepository;
import com.springboot.web.service.LoginService;
import com.springboot.web.service.TodoService;

@Controller

public class TodoController {

	@Autowired
	TodoRepository todoRepository;
	
	@Autowired
	TodoService todoService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
	

	@RequestMapping(value = "/list-todos", method = RequestMethod.GET)
	public String showTodos(ModelMap model) {
		String name = todoService.getLoggedUserName(model);
		model.put("todos", todoRepository.findByUser(name));
		return "list-todos";
	}

	
	@RequestMapping(value = "/add-todo", method = RequestMethod.GET)
	public String showAddTodoPage(ModelMap model) {
		Todo hh = new Todo();
		hh.setId(0);
		hh.setUser("");

		model.addAttribute("todo", new Todo(0, todoService.getLoggedUserName(model), "Default Desc", new Date(), false));
		return "todo";
	}

	@RequestMapping(value = "/delete-todo", method = RequestMethod.GET)
	public String deleteTodo(@RequestParam int id) {
		todoRepository.deleteById(id);
		return "redirect:/list-todos";
	}

	@RequestMapping(value = "/update-todo", method = RequestMethod.GET)
	public String showUpdateTodoPage(@RequestParam int id, ModelMap model) {
		Optional<Todo> todo = todoRepository.findById(id);
		model.put("todo", todo);
		return "todo";
	}

	@RequestMapping(value = "/update-todo", method = RequestMethod.POST)
	public String updateTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

		if (result.hasErrors()) {
			return "todo";
		}
		todo.setUser(todoService.getLoggedUserName(model));
		todoRepository.save(todo);
		return "redirect:/list-todos";
	}

	@RequestMapping(value = "/add-todo", method = RequestMethod.POST)
	public String addTodo(ModelMap model, @Valid Todo todo, BindingResult result) {

		if (result.hasErrors()) {
			return "todo";
		}
		todo.setUser(todoService.getLoggedUserName(model));
		todoRepository.save(todo);
		return "redirect:/list-todos";
	}
}
