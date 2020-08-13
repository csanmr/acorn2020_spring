package com.gura.spring05.file.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gura.spring05.file.dto.FileDto;

@Repository
public class FileDaoImpl implements FileDao {
	@Autowired
	private SqlSession session;
	
	public List<FileDto> getList(FileDto dto) {
		List<FileDto> list=session.selectList("file.getList", dto);
		return list;
	}

	//검색 키워드에 맞는 row의 갯수를 리턴하는 메소드 FileService에서 조건에 맞는 dto를 넘겨줌
	@Override
	public int getCount(FileDto dto) {
		// TODO Auto-generated method stub
		return session.selectOne("file.getCount", dto);
	}
	
	//파일 정보를 저장하는 메소드
	@Override
	public void insert(FileDto dto) {
		// TODO Auto-generated method stub
		session.insert("file.insert", dto);
	}
	//인자로 전달되는 번호에 해당하는 파일 정보를 리턴하는 메소드
	//parametertype=int resulttype=FileDto sqlid=getData
	@Override
	public FileDto getData(int num) {
		// TODO Auto-generated method stub
		return session.selectOne("file.getData", num);
	}

	@Override
	public void delete(int num) {
		session.delete("file.delete", num);
	}
}
