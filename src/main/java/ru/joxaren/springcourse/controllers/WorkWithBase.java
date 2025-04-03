package ru.joxaren.springcourse.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.joxaren.springcourse.dao.PersonDAO;

@Controller
@RequestMapping("/database-action")
public class WorkWithBase {

    private final PersonDAO personDao;

    public WorkWithBase(PersonDAO personDao) {
        this.personDao = personDao;
    }

    @GetMapping()
    public String index(){
        return "action/index";
    }

    @GetMapping("/delete-all")
    public String deleteAll(){

        personDao.deleteAll();

        return "action/index";
    }

}
