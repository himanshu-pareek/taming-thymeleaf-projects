package dev.javarush.taming_thymeleaf.thyme_wizards.infrastructure.web;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalControllerAdvice {

  @ResponseStatus(HttpStatus.CONFLICT)
  @ExceptionHandler({
      DataIntegrityViolationException.class,
      ObjectOptimisticLockingFailureException.class
  })
  public ModelAndView handleConflict(HttpServletRequest request, Exception e) {
    ModelAndView modelAndView = new ModelAndView("error/409");
    modelAndView.addObject("url", request.getRequestURL());
    return modelAndView;
  }

}
