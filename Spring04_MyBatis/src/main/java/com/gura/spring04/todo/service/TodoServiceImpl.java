package com.gura.spring04.todo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.gura.spring04.todo.dao.TodoDao;
import com.gura.spring04.todo.dto.TodoDto;
@Service
public class TodoServiceImpl implements TodoService {
	@Autowired
	private TodoDao dao;
	
	@Override
	public void addTodo(TodoDto dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateTodo(TodoDto dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteTodo(int num) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getListTodo(ModelAndView mView) {
		// TODO Auto-generated method stub
		List<TodoDto> list=dao.getList();
		mView.addObject("list", list);
	}

}
