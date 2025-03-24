package ru.joxaren.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalculatorController {

    @GetMapping("/calculator")
    public String calculatePage(@RequestParam(value = "firstArg", required = true) int firstArg,
                                @RequestParam(value = "secondArg", required = true) int secondArg,
                                @RequestParam(value = "operator", required = true) String operator,
                                Model model){

        String result = "Result is: ";

        if (operator.equals("multiplication"))
           result += (firstArg * secondArg);
        else if (operator.equals("addition"))
            result += (firstArg + secondArg);
        else if (operator.equals("subtraction"))
            result += (firstArg - secondArg);
        else if (operator.equals("division"))
            result += (firstArg - secondArg);
        else
            result = "Unknown operator";

        model.addAttribute("result", result);


        return "calculator/calculator";
    }

}
