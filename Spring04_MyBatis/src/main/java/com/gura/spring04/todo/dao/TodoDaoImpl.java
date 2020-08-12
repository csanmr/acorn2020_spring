package com.gura.spring04.todo.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gura.spring04.todo.dto.TodoDto;
@Repository
public class TodoDaoImpl implements TodoDao {

	@Autowired
	private SqlSession session;
	
	@Override
	public void insert(TodoDto dto) {
		// TODO Auto-generated method stub
		session.insert("todo.insert",dto);
	}

	@Override
	public void update(TodoDto dto) {
		// TODO Auto-generated method stub
		session.update("todo.update",dto);
	}

	@Override
	public void delete(int num) {
		// TODO Auto-generated method stub
		session.delete("todo.delete",num);
	}

	@Override
	public TodoDto getData(int num) {
		// TODO Auto-generated method stub
		TodoDto dto=session.selectOne("todo.getData",num);
		return dto;
	}

	@Override
	public List<TodoDto> getList() {
		// TODO Auto-generated method stub
		List<TodoDto> list=session.selectList("todo.getList");
		return list;
	}

}
