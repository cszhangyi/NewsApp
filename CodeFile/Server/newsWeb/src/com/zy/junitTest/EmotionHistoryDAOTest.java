package com.zy.junitTest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import com.zy.web.dao.CategoryDAO;
import com.zy.web.dao.EmotionHistoryDAO;
import com.zy.web.model.Category;

public class EmotionHistoryDAOTest {
	EmotionHistoryDAO emotionHistoryDAO;

	@Before
	public void Init() throws IOException, ClassNotFoundException {
		emotionHistoryDAO = new EmotionHistoryDAO();
	}

	@Test
	public void testGetTypes() throws SQLException {
		String list = null;
		emotionHistoryDAO.insertValue(9001, 2);
		
	}
}
