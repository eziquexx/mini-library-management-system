package com.jelee.librarymanagementsystem.global.exception;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class MessageProvider {
  private static MessageSource messageSource;

  public MessageProvider(MessageSource messageSource) {
    MessageProvider.messageSource = messageSource;
  }

  public static String getMessage(String code) {
      return messageSource.getMessage(code, null, Locale.getDefault());
  }
}
