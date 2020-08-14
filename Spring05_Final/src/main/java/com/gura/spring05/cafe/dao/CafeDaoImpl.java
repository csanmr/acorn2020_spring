package com.gura.spring05.cafe.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gura.spring05.cafe.dto.CafeDto;

@Repository
public class CafeDaoImpl implements CafeDao {
	@Autowired
	private SqlSession session;

	@Override
	public List<CafeDto> getList(CafeDto dto) {
		// TODO Auto-generated method stub
		return session.selectList("cafe.getList", dto);
	}

	@Override
	public int getCount(CafeDto dto) {
		// TODO Auto-generated method stub
		return session.selectOne("cafe.getCount", dto);
	}

	@Override
	public void insert(CafeDto dto) {
		// TODO Auto-generated method stub
		session.insert("cafe.insert", dto);
	}

	@Override
	public CafeDto getData(int num) {
		// TODO Auto-generated method stub
		return session.selectOne("cafe.getData", num);
	}

	@Override
	public void addViewCount(int num) {
		// TODO Auto-generated method stub
		session.update("cafe.addViewCount", num);
	}

	@Override
	public void delete(int num) {
		// TODO Auto-generated method stub
		session.delete("cafe.delete", num);
	}

	@Override
	public void update(CafeDto dto) {
		// TODO Auto-generated method stub
		session.update("cafe.update", dto);
	}
	//키워드가 들어있는 CafeDto를 전달받아서 글정보를 리턴하는 메소드
	@Override
	public CafeDto getData(CafeDto dto) {
		// TODO Auto-generated method stub
		return session.selectOne("cafe.getData2", dto);
	}
}
