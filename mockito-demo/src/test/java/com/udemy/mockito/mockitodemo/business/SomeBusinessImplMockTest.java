package com.udemy.mockito.mockitodemo.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SomeBusinessImplMockTest {
	
	@Mock
	private DataService dataServiceMock;
	
	@InjectMocks
	private SomeBusinessImpl businessImpl;

	//Using mockito annotations
	@Test
	void findTheGreatestFromAllData_basicScenario() {
		when(dataServiceMock.retrieveAllData()).thenReturn(new int [] {25, 15, 5});
		int  result = businessImpl.findTheGreatestFromAllData();
		assertEquals(25, result);
	}
	
	@Test
	void findTheGreatestFromAllData_OneValue() {
		when(dataServiceMock.retrieveAllData()).thenReturn(new int [] {35});
		int  result = businessImpl.findTheGreatestFromAllData();
		assertEquals(35, result);
	}
	
	@Test
	void findTheGreatestFromAllData_EmptyArray() {
		when(dataServiceMock.retrieveAllData()).thenReturn(new int [] {});
		int  result = businessImpl.findTheGreatestFromAllData();
		assertEquals(Integer.MIN_VALUE, result);
	}
	
	
	
	
	//This is without mockito annotations
	
//	@Test
//	void findTheGreatestFromAll_OneValue() {
//		
//		DataService dataServiceMock = mock(DataService.class);
//		
//		when(dataServiceMock.retrieveAllData()).thenReturn(new int [] {35});
//		SomeBusinessImpl businessImpl = new SomeBusinessImpl(dataServiceMock);
//		
//		//The result is inline here but in the above method it ain't
//		assertEquals(35, businessImpl.findTheGreatestFromAllData());
//	}

}

