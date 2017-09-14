package org.sgolub;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.sgolub.web.ApplicationRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ApplicationTest {

	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ApplicationRestController restController;

	private MockMvc mockMvc;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void contexLoads() throws Exception {
		assertThat(restController).isNotNull();
	}

	@Test
	public void readOnePage() throws Exception {
		mockMvc.perform(get("/rest/users/list-paginated?size=30&page=0")).andExpect(status().isOk())
				.andExpect(content().contentType(contentType)).andExpect(jsonPath("$.totalElements", is(12)))

				// .andExpect(jsonPath("$.id",
				// is(this.bookmarkList.get(0).getId().intValue())))
				// .andExpect(jsonPath("$.uri", is("http://bookmark.com/1/" +
				// userName)))
				;
	}
	@Test
	public void readAllTogetherPage() throws Exception {
		mockMvc.perform(get("/rest/users/find-sorted-paged?sFirstName=asc&fFirstName=ob&sLastName=aSC"))
			.andExpect(status().isOk())
			.andExpect(content().contentType(contentType))
			.andExpect(jsonPath("$.totalElements", is(2)))
			.andExpect(jsonPath("$.content.length()", is(2)))
			.andExpect(jsonPath("$.content[1].lastName", is("Kennedy")));
	}
	
	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);
		assertThat(this.mappingJackson2HttpMessageConverter).isNotNull();
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
