package com.gura.spring05.cafe.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gura.spring05.cafe.dto.CafeCommentDto;
@Repository
public class CafeCommentDaoImpl implements CafeCommentDao {
	@Autowired SqlSession session;
	
	@Override
	public List<CafeCommentDto> getList(int ref_group) {
		// TODO Auto-generated method stub
		return session.selectList("cafeComment.getList",ref_group);
	}

	@Override
	public void delete(int num) {
		// TODO Auto-generated method stub
		session.update("cafeComment.delete", num);
	}

	@Override
	public void insert(CafeCommentDto dto) {
		// TODO Auto-generated method stub
		session.insert("cafeComment.insert", dto);
	}

	@Override
	public int getSequence() {
		// TODO Auto-generated method stub
		return session.selectOne("cafeComment.getSequence");
	}

	@Override
	public void update(CafeCommentDto dto) {
		// TODO Auto-generated method stub
		session.update("cafeComment.update", dto);
	}

	@Override
	public CafeCommentDto getData(int num) {
		// TODO Auto-generated method stub
		return session.selectOne("cafeComment.getData", num);
	}

}
