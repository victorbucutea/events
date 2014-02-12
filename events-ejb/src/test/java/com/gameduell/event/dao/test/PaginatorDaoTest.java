package com.gameduell.event.dao.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.gameduell.event.dao.Paginator;
import com.gameduell.event.model.Event;

public class PaginatorDaoTest extends ContainerTest {

	@Test
	public void emptyDbPagination() {
		Paginator<Event> paginator = lookupPaginatorDao();
		
		List<Event> next = paginator.next();
		
		assertEquals(0, next.size());
		paginator.close();
	}
	
	@Test
	public void pagination() { 
		createInitialDataSet(100);
		Paginator<Event> paginator = lookupPaginatorDao();
		
		paginator.setPageSize(20);
		
		paginator.next();
		paginator.next();
		paginator.next();
		paginator.next();
		
		List<Event> page = paginator.next();
		assertEquals(20, page.size());
		Event event = page.get(0);
		assertEquals("Event 80",event.getName());
		
		event = page.get(19);
		assertEquals("Event 99",event.getName());
		
		paginator.close();
		
	}
	
	@Test
	public void pageLargerThanDataSet() {
		createInitialDataSet(20);
		Paginator<Event> paginator = lookupPaginatorDao();
		paginator.setPageSize(30);
		List<Event> next = paginator.next();
		assertEquals(20,next.size());
		paginator.close();
	}
	
}
