package base;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import domain.PersonDomainModel;

public class Person_Test {

private static PersonDomainModel p1;

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		Date D = null;
		try {
			D = new SimpleDateFormat("yyyy-MM-dd").parse("1972-07-31");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		p1 = new PersonDomainModel();
		p1.setFirstName("Jackson");
		p1.setLastName("Jorss");
		p1.setBirthday(D);
		p1.setCity("Newark");
		p1.setPostalCode(19717);
		p1.setStreet("279 The Green");
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		PersonDomainModel per;	
		PersonDAL.deletePerson(p1.getPersonID());
		per = PersonDAL.getPerson(p1.getPersonID());
		assertNull("The Person shouldn't be in the database",per);		
	}
	
	@Test
	public void AddPersonTest()
	{		
		// checking if the person is already in the database
		PersonDomainModel per;		
		per = PersonDAL.getPerson(p1.getPersonID());		
		assertNull("The Person shouldn't have been in the database",per);		
		
		// adding the person to the database
		PersonDAL.addPerson(p1);	
		
		// getting the person from the database
		per = PersonDAL.getPerson(p1.getPersonID());
		System.out.println(p1.getPersonID() + " found");
		assertNotNull("The Person should have been added to the database",per);
	}
	
	@Test
	public void UpdatePersonTest()
	{		
		PersonDomainModel per;
		final String newLastName = "Jones";
		
		
		//making sure the person is not already in the database
		per = PersonDAL.getPerson(p1.getPersonID());		
		assertNull("The Person shouldn't have been in the database",per);		
		PersonDAL.addPerson(p1);
		
		//making sure the name has not changed/updated
		per = PersonDAL.getPerson(p1.getPersonID());
		assertTrue("Name has not changed", per.getLastName() != newLastName);
		
		//locally updating last name
		p1.setLastName(newLastName);
		// updating database
		PersonDAL.updatePerson(p1);
		
		per = PersonDAL.getPerson(p1.getPersonID());

		// Testing if the last name changed on database
		assertTrue("Name Did Change",p1.getLastName() == newLastName);
	}

	@Test
	public void DeletePersonTest()
	{	
		// making sure p1 isn't already in nthe database
		PersonDomainModel per;		
		per = PersonDAL.getPerson(p1.getPersonID());		
		assertNull("The Person shouldn't have been in the database",per);	
		
		PersonDAL.addPerson(p1);			
		per = PersonDAL.getPerson(p1.getPersonID());
		System.out.println(p1.getPersonID() + " found");
		assertNotNull("The Person is now in the database",per);
		
		
		// deleting
		PersonDAL.deletePerson(p1.getPersonID());
		per = PersonDAL.getPerson(p1.getPersonID());		
		assertNull("The Person is no longer in the database",per);	
		
	}
}
