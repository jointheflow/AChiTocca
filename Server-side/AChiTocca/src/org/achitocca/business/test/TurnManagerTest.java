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
		u1.setExternalUserId("u1");
		u1.setTurnWeight(1);
		
		User u2= new User();
		u2.setExternalUserId("u2");
		u2.setTurnWeight(1);
		
		User u3= new User();
		u3.setExternalUserId("u3");
		u3.setTurnWeight(1);
		
		testUsers3.add(u1);
		testUsers3.add(u2);
		testUsers3.add(u3);
		
		
		
		
		testUsers2.add(u1);
		testUsers2.add(u2);
		
		CircularList daysOfWeeks2 = new CircularList();
		daysOfWeeks2.add((short)1);
		daysOfWeeks2.add((short)2);
		daysOfWeeks2.add((short)3);
		testTurnDef3.setDaysOfWeek(daysOfWeeks2);
		
		User u4 = new User();
		u4.setExternalUserId("u4");
		u4.setTurnWeight(2);
		
		
		User u5 = new User();
		u5.setExternalUserId("u5");
		u5.setTurnWeight(-2);
			
		
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
	public void test3User() throws ParseException {
		TurnFactory.turnCreate("g1", testUsers3);
		
	}
	
	@Test
	public void test5UserWithMinus() throws ParseException {
		TurnFactory.turnCreate("g1", testUsers5);
		
	}
	
	
	@Test
	public void testaChiTocca() throws ParseException {
		System.out.println("Test a chi tocca!");		
		
		Turn aTurn = TurnFactory.turnCreate("g1", testUsers5);
		String aChiTocca = TurnFactory.aChiTocca(aTurn);
		System.out.println("Tocca a: "+ aChiTocca);
		System.out.println("Accetto il turno");
		aTurn = TurnFactory.doNext(aTurn,testUsers5);
		
		aChiTocca = TurnFactory.aChiTocca(aTurn);
		System.out.println("Tocca a: "+ aChiTocca);
		System.out.println("Accetto il turno");
		aTurn = TurnFactory.doNext(aTurn,testUsers5);
		
		aChiTocca = TurnFactory.aChiTocca(aTurn);
		System.out.println("Tocca a: "+ aChiTocca);
		System.out.println("Accetto il turno");
		aTurn = TurnFactory.doNext(aTurn,testUsers5);
		
		aChiTocca = TurnFactory.aChiTocca(aTurn);
		System.out.println("Tocca a: "+ aChiTocca);
		System.out.println("Accetto il turno");
		aTurn = TurnFactory.doNext(aTurn,testUsers5);
		
		aChiTocca = TurnFactory.aChiTocca(aTurn);
		System.out.println("Tocca a: "+ aChiTocca);
		System.out.println("Accetto il turno");
		aTurn = TurnFactory.doNext(aTurn,testUsers5);
		
		aChiTocca = TurnFactory.aChiTocca(aTurn);
		System.out.println("Tocca a: "+ aChiTocca);
	}
	

}
