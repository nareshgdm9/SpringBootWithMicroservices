package com.action.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.action.entity.Trade;
import com.action.service.ActionService;

@RestController
@RequestMapping(value = "/v1")
public class TradeActionRestController {
	private static Logger logger = LogManager.getLogger(TradeActionRestController.class);

	@Autowired
	ActionService actionService;

	@PostMapping(path = "/action/review/trades", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Trade[]> reviewTradeAction(@RequestBody List<Trade> listTrades) {

		logger.info("inside reviewTradeAction(@RequestBody List<Trade> listTrades) method ");

		ResponseEntity<Trade[]> updateTrades = actionService.updateTrades(listTrades, "reviewed");

		return updateTrades;

	}

	@PostMapping(path = "action/approve/trades", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Trade[]> approveTradeAction(@RequestBody List<Trade> listTrades) {

		logger.info("inside approveTradeAction(@RequestBody List<Trade> listTrades) method ");

		return actionService.updateTrades(listTrades, "approved");
	}

	@PostMapping(path = "action/reject/trades", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Trade[]> rejectTradeAction(@RequestBody List<Trade> listTrades) {

		logger.info("inside rejectTradeAction(@RequestBody List<Trade> listTrades) method ");

		return actionService.updateTrades(listTrades, "rejected");
	}

	@PostMapping(path = "action/cancel/trades", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Trade[]> cancelTradeAction(@RequestBody List<Trade> listTrades) {

		logger.info("inside rejectTradeAction(@RequestBody List<Trade> listTrades) method ");

		return actionService.updateTrades(listTrades, "cancel");
	}

	// for testing purpose
	@SuppressWarnings("unused")
	@GetMapping("/test")
	public ResponseEntity<Object> test() {
		System.out.println("inside ResponseEntity<Object> test()");
		// String[] userdetails = SecurityUtil.findLoggedInUsername();
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
