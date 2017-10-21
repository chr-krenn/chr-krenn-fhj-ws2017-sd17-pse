package org.se.lab.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.se.lab.data.User;
import org.se.lab.data.UserDAO;

@Stateless
public class UserService
{
	private final Logger LOG = Logger.getLogger(UserService.class);
	
	@Inject
	private UserDAO dao;
	
	/*
	 * API Operations
	 */

	public void insert(User article)
	{
		LOG.debug("insert: " + article);

		try
		{
			dao.insert(article);
		}
		catch(Exception e)
		{
			LOG.error("Can't insert article " + article, e);
			throw new ServiceException("Can't insert article " + article);
		}
	}
	
	
	public void update(User article)
	{
		LOG.debug("update to " + article);

		// TODO
	}

	
	public void delete(int id)
	{
		LOG.info("delete: " + id);
		
		// TODO
	}
	
	
	public List<User> findAll()
	{
		LOG.debug("find all articles");

		try
		{
			List<User> list = dao.findAll();
			return list;
		}
		catch(Exception e)
		{
			LOG.error("Can't find all articles!", e);
			throw new ServiceException("Can't find all articles!");
		}
	}
	
	
	public User findById(int id) 
	{
		LOG.debug("find User with id=" + id);

		try
		{
			User article = dao.findById(id);
			return article;
		}
		catch(Exception e)
		{
			LOG.error("Can't find article with id " + id, e);
			throw new ServiceException("Can't find article with id " + id);
		}
	}
}
