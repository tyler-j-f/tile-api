package com.tylerfitzgerald.demo_api.scheduler.tasks;

import com.tylerfitzgerald.demo_api.erc721.token.TokenInitializeException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

public interface TaskInterface {
  public void execute()
      throws ExecutionException, InterruptedException, TokenInitializeException,
          ClassNotFoundException, InvocationTargetException, NoSuchMethodException,
          InstantiationException, IllegalAccessException;
}
