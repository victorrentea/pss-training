package victor.training.spring;

import static org.junit.Assert.assertNotNull;

import java.text.ParseException;

import junit.framework.Assert;
import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import victor.training.spring.model.Employee;
import victor.training.spring.service.HRService;
import victor.training.spring.service.MyWSClient;

//@ContextConfiguration(locations = { "classpath:/config-test.xml" })
@ContextConfiguration(classes = {ConfigSolution.class, TestConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class HRServiceTest {

	@Autowired
	private HRService hrService;
	
	// TODO How about using a mock in Tests to override a real implem bean 
//	@Autowired
//	private MyWSClient mockWsClient;

	@Test
	public void testSwitchPhoneSuccessful() {
		hrService.createEmployee(new Employee());
	}
	
	@Test
	public void getEmployee() {
		Employee e = hrService.getEmployeeById("1");
		System.out.println(e);
	}
	
	@Test
	public void getAllQuestions() {
		assertNotNull(hrService.getAllQuestions());
	}
	
	@Test
	public void removeEmployee() {
		hrService.removeEmployee("1");
	}
	
	@Test
	public void getPrimitiveProperty() {
		assertNotNull(hrService.getMyProperty());
	}


	@Autowired
	private MyWSClient mockClient;
	@Test
	public void callExternalWebService() throws ParseException {
		System.out.println("XX: " + mockClient.getClass());
		Mockito.when(mockClient.callWebService(Matchers.any())).thenReturn(10);
		int actual = hrService.callMyService(DateUtils.parseDate("2016-01-01", new String[]{"yyyy-MM-dd"}));
		Assert.assertEquals(10, actual);
//		verify(mockWsClient).callWebService("2016-01-01");
	}

}
