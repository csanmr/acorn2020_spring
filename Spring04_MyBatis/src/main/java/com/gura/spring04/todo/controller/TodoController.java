package com.gura.spring04.todo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import com.gura.spring04.todo.dto.TodoDto;
import com.gura.spring04.todo.service.TodoService;

@Controller
public class TodoController {
	
	@Autowired
	private TodoService service;
	
	@RequestMapping("/todo/insert")
	public String insert(@ModelAttribute TodoDto dto) {
		service.addTodo(dto);
		return "todo/insert";
	}
	
	@RequestMapping("/todo/insertform")
	public String insertform() {
		//수행할 비즈니스 로직은 현재 없다.
		
		return "todo/insertform";
	}
	
	@RequestMapping("/todo/list")
	public ModelAndView list(ModelAndView mView) {
		service.getListTodo(mView);
		mView.setViewName("todo/list");
		return mView;
	}
}
