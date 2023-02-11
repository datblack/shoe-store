/**
 * DATN_FALL2022, 2022
 * ConfigIndexesImpl.java, BUI_QUANG_HIEU
 */
package com.pro2111.serviceimpl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import com.pro2111.service.ConfigIndexService;

/**
 * @author BUI_QUANG_HIEU
 *
 */
@Configuration
public class ConfigIndexServiceImpl implements ConfigIndexService{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void perform() throws Exception {
		try {
			Session session = sessionFactory.openSession();
			FullTextSession fullTextSession = Search.getFullTextSession(session);
			fullTextSession.createIndexer().startAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
