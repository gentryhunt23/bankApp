import dao.UserDao;
import models.User;

public class Test {

	User testU = UserDao.signUp("TestFirst", "TestLast", "Testpass");
	
	@org.junit.Test
	public void stringToUserTest() {
     org.junit.Assert.assertFalse( testU == null );
		
	}
}
