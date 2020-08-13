package com.gura.spring05.users.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gura.spring05.users.dto.UsersDto;

@Repository
public class UsersDaoImpl implements UsersDao {
	@Autowired
	private SqlSession session; //sqlsession에 의존

	@Override			//(parameter type)
	public boolean isExist(String inputId) { // session에 inputId가 있는지 select해본다
		// TODO Auto-generated method stub
		//입력한 아이디가 존재하는지 id를 select 해본다. users.isExist에서 users(mapper namespace).isExist(sql id)
		String id=session.selectOne("users.isExist",inputId);
		//String id에서 String은 result type
		
		if(id==null) { //존재하지 않는 아이디
			return false;
		}else { //존재하는 아이디
			return true;
		}
	}

	@Override
	public void insert(UsersDto dto) {
		// TODO Auto-generated method stub
		session.insert("users.insert",dto);
	}

	@Override
	public void update(UsersDto dto) {
		// TODO Auto-generated method stub
		session.update("users.update",dto);
	}

	//이나졸 전달되는 id에 해당되는 사용자 정보를 리턴하는 메소드
	@Override
	public UsersDto getData(String id) {
		// TODO Auto-generated method stub
		UsersDto dto=session.selectOne("users.getData", id);
		return dto;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub
		UsersDto dto=session.selectOne("users.delete", id);
	}

	@Override
	public void updatePwd(UsersDto dto) {
		// TODO Auto-generated method stub
		//update 문의 영향을 받은 row의 갯수가 리턴된다.
		session.update("users.updatePwd", dto);
	}
	
}
