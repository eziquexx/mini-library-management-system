package com.jelee.librarymanagementsystem.global.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageProvider {
  private final MessageSource messageSource;

  public MessageProvider(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public String getMessage(String code) {
    return messageSource.getMessage(code, null, Locale.getDefault());
  }

  public String getMessage(String code, Object[] args) {
    return messageSource.getMessage(code, args, Locale.getDefault());
  }
}
