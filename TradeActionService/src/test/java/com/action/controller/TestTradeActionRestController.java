package com.action.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.action.entity.Trade;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@RunWith(MockitoJUnitRunner.class)

public class TestTradeActionRestController {
	private MockMvc mockMvc;

	@InjectMocks
	TradeActionRestController tradeActionRestController;

	@Before
	public void init() {

		mockMvc = MockMvcBuilders.standaloneSetup(tradeActionRestController).build();
	}

	/*@Test
	public void testReviewTradeAction() throws Exception {

		ResultActions response = mockMvc
				.perform(post("/v1/action/review/trades").contentType(MediaType.APPLICATION_JSON).content(toJson()))
				.andExpect(status().isOk());
		int returnstatus = response.andReturn().getResponse().getStatus();
		assertThat(returnstatus).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	public void testCancelTradeAction() throws Exception {

		ResultActions response = mockMvc
				.perform(post("/v1/action/cancel/trades").contentType(MediaType.APPLICATION_JSON).content(toJson()))
				.andExpect(status().isOk());
		int returnstatus = response.andReturn().getResponse().getStatus();
		assertThat(returnstatus).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	public void testApproveTradeAction() throws Exception {

		ResultActions response = mockMvc
				.perform(post("/v1/action/approve/trades").contentType(MediaType.APPLICATION_JSON).content(toJson()))
				.andExpect(status().isOk());
		int returnstatus = response.andReturn().getResponse().getStatus();
		assertThat(returnstatus).isEqualTo(HttpStatus.OK.value());
	}

	@Test
	public void testRejectTradeAction() throws Exception {

		ResultActions response = mockMvc
				.perform(post("/v1/action/reject/trades1").contentType(MediaType.APPLICATION_JSON).content(toJson()))
				.andExpect(status().isOk());
		int returnstatus = response.andReturn().getResponse().getStatus();
		assertThat(returnstatus).isEqualTo(HttpStatus.OK.value());
	}*/
	
	@Test
	public void testReviewTradeAction1() throws Exception {
		List<Trade> trades = new ArrayList<>();
		MockHttpServletResponse response = mockMvc.perform(
				post("/v1//action/review/trades").contentType(MediaType.APPLICATION_JSON).content(toJson()))
				.andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

	}

	private String toJson() throws JsonGenerationException, JsonMappingException, IOException {
		List<Trade> trades = new ArrayList<>();
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		mapper.writeValue(out, trades);
		return out.toString();
	}
}
