package br.com.lacerda;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculatorController {

	@RequestMapping("/calc")
	public Calculator calc(
			@RequestParam(value = "operation") String operation,
			@RequestParam(value = "numA") double numA, 
			@RequestParam("numB") double numB) {
		return new Calculator(operation, numA, numB);
	}

}
