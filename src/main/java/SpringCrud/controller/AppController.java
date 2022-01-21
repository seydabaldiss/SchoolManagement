/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SpringCrud.controller;

import SpringCrud.model.Student;
import SpringCrud.model.User;

import java.util.List;

import SpringCrud.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import SpringCrud.services.StudentServices;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class AppController {

    @Autowired
    StudentServices service;

    @Autowired
    private UserServices service1;
    
    @RequestMapping("/")
    public String viewHomePage(Model model, User user, HttpServletResponse response) {
        //List<User> listUsers = service1.listAll();
        //model.addAttribute("listUsers", listUsers);
        //System.out.println("List:" + listUsers);
        
        Cookie cookie = new Cookie( "AdminGiris","Seyda" );
        Cookie cookie2 = new Cookie( "SchoolManagement","Student" );
        
        cookie.setMaxAge(30);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        
        cookie2.setMaxAge(30);
        cookie2.setSecure(true);
        cookie2.setHttpOnly(true);
        cookie2.setPath("/");
        
        
        response.addCookie(cookie);
        response.addCookie(cookie2);


        return "Anasayfa"; // index
    }

    @RequestMapping("/index")
    public String viewIndexPage(Model model, User user) {
        List<User> listUsers = service1.listAll();
        model.addAttribute("listUsers", listUsers);
        //System.out.println("List:" + listUsers);
        return "index"; // index
    }

    @RequestMapping("/studentlist")
    public String viewStudentPage(Model model, Student student) {
        List<Student> listStudent = service.listAll();
        model.addAttribute("listStudent", listStudent);
        return "student_list";
    }
    
    @RequestMapping("/profile")
    public String viewProfilePage(Model model, Student student) {
        List<Student> listStudent = service.listAll();
        model.addAttribute("listStudent", listStudent);
        return "profile";
    }
    
    @RequestMapping("/contact")
    public String viewContactPage(Model model, Student student) {
        List<Student> listStudent = service.listAll();
        model.addAttribute("listUsers", listStudent);
        //System.out.println("List:" + listUsers);
        return "Iletisim";
    }
    @RequestMapping("/rektor")
    public String viewCRektorPage(Model model, Student student) {
        List<Student> listStudent = service.listAll();
        model.addAttribute("listUsers", listStudent);
        //System.out.println("List:" + listUsers);
        return "Kurucu";
    }
    @RequestMapping("/bolum")
    public String viewCBolumPage(Model model, Student student) {
        List<Student> listStudent = service.listAll();
        model.addAttribute("listUsers", listStudent);
        //System.out.println("List:" + listUsers);
        return "Kadro";
    }
    
    @RequestMapping("/new")
    public String newUserPage(Model model) {
        User user = new User();
        model.addAttribute(user);
        return "new_student";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String saveUser(@ModelAttribute("user") User user) {
        user.setEnabled(true);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        service1.save(user);
        return "redirect:/index";
    }

    @RequestMapping("/newStudent")
    public String newStudentPage(Model model) {
        Student student = new Student();
        model.addAttribute(student);
        return "NewOgrenci";
    }

    @RequestMapping(value = "/save1", method = RequestMethod.POST)
    public String saveStudent(@ModelAttribute("student") Student student) {
        service.save(student);
        return "redirect:/studentlist";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView showEditUserPage(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("edit_student");
        User user = service1.get(id);
        mav.addObject("user", user);
        return mav;
    }
    
    @RequestMapping("/editOgrenci/{id}")
    public ModelAndView showEditStudentPage(@PathVariable(name = "id") Long id) {
        ModelAndView mav = new ModelAndView("EditOgrenci");
        Student student = service.get(id);
        mav.addObject("student", student);
        return mav;
    }

    @RequestMapping("/delete/{id}")
    public String deleteUserPage(@PathVariable(name = "id") Long id) {
        service1.delete(id);
        return "redirect:/index";
    }
    
    @RequestMapping("/deleteStudent/{id}")
    public String deleteStudentPage(@PathVariable(name = "id") Long id) {
        service.delete(id);
        return "redirect:/studentlist";
    }
}
