package org.achitocca.business.test;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.achitocca.business.TurnFactory;
import org.achitocca.business.model.util.CircularList;
import org.achitocca.business.model.Turn;
import org.achitocca.business.model.TurnDefinition;
import org.achitocca.business.model.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mortbay.log.Log;

public class TurnManagerTest {
	ArrayList<User> testUsers1 = new ArrayList<User>();
	TurnDefinition testTurnDef1  = new TurnDefinition();
	ArrayList<User> testUsers2 = new ArrayList<User>();
	TurnDefinition testTurnDef2  = new TurnDefinition();
	ArrayList<User> testUsers3 = new ArrayList<User>();
	TurnDefinition testTurnDef3  = new TurnDefinition();
	TurnDefinition testTurnDef5  = new TurnDefinition();
	ArrayList<User> testUsers5 = new ArrayList<User>();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		User u1= new User();
		u1.setUserId("u1");
		u1.setTurnWeight(1);
		
		User u2= new User();
		u2.setUserId("u2");
		u2.setTurnWeight(1);
		
		User u3= new User();
		u3.setUserId("u3");
		u3.setTurnWeight(1);
		
		testUsers1.add(u1);
		testUsers1.add(u2);
		testUsers1.add(u3);
		
		CircularList daysOfWeeks1 = new CircularList();
		daysOfWeeks1.add((short)1);
		daysOfWeeks1.add((short)2);
		
		testTurnDef1.setDaysOfWeek(daysOfWeeks1);
		
		
		testUsers2.add(u1);
		testUsers2.add(u2);
		
		CircularList daysOfWeeks2 = new CircularList();
		daysOfWeeks2.add((short)1);
		daysOfWeeks2.add((short)2);
		daysOfWeeks2.add((short)3);
		testTurnDef3.setDaysOfWeek(daysOfWeeks2);
		
		User u4 = new User();
		u4.setUserId("u4");
		u4.setTurnWeight(2);
		
		
		User u5 = new User();
		u5.setUserId("u5");
		u5.setTurnWeight(-2);
		
		
		testUsers3.add(u4);
		testUsers3.add(u5);
		
		
		CircularList daysOfWeeks5 = new CircularList();
		daysOfWeeks5.add((short)1);
		daysOfWeeks5.add((short)2);
		daysOfWeeks5.add((short)3);
		daysOfWeeks5.add((short)4);
		daysOfWeeks5.add((short)5);
		testTurnDef5.setDaysOfWeek(daysOfWeeks5);
		testUsers5.add(u1);
		testUsers5.add(u2);
		testUsers5.add(u3);
		testUsers5.add(u4);
		testUsers5.add(u5);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test3u2d() throws ParseException {
		TurnFactory.turnCreate("g1", new Date(), testUsers1, testTurnDef1, 0);
		
	}
	
	@Test
	public void test3u3d() throws ParseException {
		TurnFactory.turnCreate("g1", new Date(), testUsers1, testTurnDef3, 0);
		
	}
	@Test
	public void test2u3d() throws ParseException {
		TurnFactory.turnCreate("g1", new Date(), testUsers2, testTurnDef3, 0);
		TurnFactory.turnCreate("g1", new Date(), testUsers2, testTurnDef3, 1);
		TurnFactory.turnCreate("g1", new Date(), testUsers2, testTurnDef3, 2);
	}
	
	@Test
	public void test2u2dNegativePositive() throws ParseException {
		TurnFactory.turnCreate("g1", new Date(), testUsers3, testTurnDef1, 0);
		TurnFactory.turnCreate("g1", new Date(), testUsers3, testTurnDef3, 0);
		
	}
	
	@Test
	public void testaChiTocca() throws ParseException {
		DateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String dateString = "12-11-2015";
		Date dateObject = sdf.parse(dateString);
		
		Date Date16_11_2015 = sdf.parse("16-11-2015");
		
		Turn aTurn = TurnFactory.turnCreate("g1", dateObject, testUsers5, testTurnDef5, 0);
		String aChiTocca = TurnFactory.aChiTocca(Date16_11_2015, aTurn);
		System.out.println(aChiTocca);
	}
	

}
