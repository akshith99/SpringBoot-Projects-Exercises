package com.in28minutes.springboot.firstrestapi.survey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

	private static String Specific_Question_URL = "/surveys/Survey1/questions/Question1";

	private static String Generic_Questions_URL = "/surveys/Survey1/questions";

	@Autowired
	private TestRestTemplate template;

	// http://localhost:RANDOM_PORT/surveys/Survey1/questions/Question1

	@Test
	void retreiveSpecificSurveyQuestions_basicScenario() throws JSONException {
		
		HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();
		//admin, password

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> responseEntity = 
				template.exchange(Specific_Question_URL, HttpMethod.GET, httpEntity, String.class);
		
		//ResponseEntity<String> responseEntity = template.getForEntity(Specific_Question_URL, String.class);
		
		String expectedResponse = """
				{
					"id":"Question1",
					"description":"Most Popular Cloud Platform Today",
					"correctAnswer":"AWS"
				}
				""";

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);
		// assertEquals(expectedResponse.trim(), responseEntity.getBody());
	}

	@Test
	void retreiveAllSurveyQuestions_basicScenario() throws JSONException {
		
		HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();
		//admin, password

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> responseEntity = 
				template.exchange(Generic_Questions_URL, HttpMethod.GET, httpEntity, String.class);

		//ResponseEntity<String> responseEntity = template.getForEntity(Generic_Questions_URL, String.class);
		
		String expectedResponse = """
				 [
						{
							"id": "Question1"
						},
						{
							"id": "Question2"
						},
						{
							"id": "Question3"
						}
					]
				""";

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
		assertEquals("application/json", responseEntity.getHeaders().get("Content-Type").get(0));

		JSONAssert.assertEquals(expectedResponse, responseEntity.getBody(), false);

	}

	@Test
	void addNewSurveyQuestion_basicScenario() {

		String requestBody = """
								{
									
									"description": "My Favorite Programming Language",
									"options": [
												"C",
												"C++",
												"Java",
												"Python"
									],
									"correctAnswer": "Java"
								}
								""";

		HttpHeaders headers = createHttpContentTypeAndAuthorizationHeaders();
		
		//Created an extract method for the below lines
		
//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Content-Type", "application/json");
//		headers.add("Authorization", "Basic " + performBasicAuthEncoding("admin","password"));
		
		//admin, password

		HttpEntity<String> httpEntity = new HttpEntity<String>(requestBody, headers);

		ResponseEntity<String> responseEntity = template.exchange(Generic_Questions_URL, HttpMethod.POST, httpEntity,
				String.class);

		assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

		String locationHeader = responseEntity.getHeaders().get("Location").get(0);
		assertTrue(locationHeader.contains("/surveys/Survey1/questions/"));

		// DELETE
		// locationHeader
		ResponseEntity<String> responseEntityDelete = template.exchange(locationHeader, HttpMethod.DELETE, httpEntity,
				String.class);
		
		assertTrue(responseEntityDelete.getStatusCode().is2xxSuccessful());
		//template.delete(locationHeader);

	}
	
	private HttpHeaders createHttpContentTypeAndAuthorizationHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");
		headers.add("Authorization", "Basic " + performBasicAuthEncoding("admin","password"));
		return headers;
	}
	
	String performBasicAuthEncoding(String user, String password) {
		String combined = user + ":" + password;
		//Base64 Encoding => Bytes
		byte[] encodedBytes = Base64.getEncoder().encode(combined.getBytes());
		//Bytes => String
		return new String(encodedBytes);
	}

}
