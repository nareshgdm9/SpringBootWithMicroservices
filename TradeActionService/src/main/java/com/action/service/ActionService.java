package com.action.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.action.config.SecurityUtil;
import com.action.entity.Audit;
import com.action.entity.Trade;
import com.action.repo.AuditRepo;

@Service
public class ActionService {

	private static Logger logger = LogManager.getLogger(ActionService.class);

	@Value("${app.listoftrades}")
	String listoftrades;

	@Value("${app.savetrades}")
	String savetrades;

	@Autowired
	RestTemplate restTemplate;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	AuditRepo auditRepo;

	public ResponseEntity<Trade[]> updateTrades(List<Trade> listTrades, String action) {

		logger.info("inside updateTrades(List<Trade> listTrades, String action) method ");

		List<Integer> trades = listTrades.stream().map(Trade::getTradeId).collect(Collectors.toList());
		String tradeids = trades.stream().map(String::valueOf).collect(Collectors.joining(","));
		auditmethod(action, tradeids);

		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter
				.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
		restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

		String[] userdetails = SecurityUtil.findLoggedInUsername();

		String username = userdetails[0];
		String password = "1234";
		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username, password);
		HttpEntity<List<Trade>> request = new HttpEntity<>(listTrades, headers);

		restTemplate.postForEntity(listoftrades, request, Trade[].class); // need to handle error happens

		listTrades.forEach(trade -> {
			trade.setStatus(action);
		});

		HttpEntity<List<Trade>> request1 = new HttpEntity<>(listTrades, headers);

		ResponseEntity<Trade[]> postForObject = restTemplate.postForEntity(savetrades, request1, Trade[].class);

		return postForObject;
	}

	public void auditmethod(String action, String trades) {  // these mwthods should br private
		String[] userdetails = SecurityUtil.findLoggedInUsername();

		Audit audit = new Audit();
		audit.setUsername(userdetails[0]);
		audit.setUserrole(userdetails[1]);
		audit.setCreatedDate(new Date());
		audit.setAction(action);
		audit.setTrades(trades);
		auditRepo.save(audit);
	}

}
