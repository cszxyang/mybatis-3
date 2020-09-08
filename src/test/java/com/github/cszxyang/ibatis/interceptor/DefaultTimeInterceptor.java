package com.github.cszxyang.ibatis.interceptor;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ReflectUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.*;

@Intercepts(@Signature(method = "handleResultSets",
  type = ResultSetHandler.class, args = {Statement.class}))
public class DefaultTimeInterceptor implements Interceptor {

  private static final Date DEFAULT_TIME = DateUtil.parse("1970-01-01 00:00:00");
  private final Logger logger = LoggerFactory.getLogger(DefaultTimeInterceptor.class);

  @Override
  @SuppressWarnings("unchecked")
  public Object intercept(Invocation invocation) throws Throwable {
    Object proceed = invocation.proceed();
    if (Objects.nonNull(proceed)) {
      if (proceed instanceof List) {
        logger.info("DefaultTimeInterceptor.intercept 获取到结果集，准备进入拦截逻辑");
        return handleDefaultTime((List<Object>) proceed);
      }
    }
    return proceed;
  }

  private List<Object> handleDefaultTime(List<Object> proceedList) {
    if (CollectionUtils.isNotEmpty(proceedList)) {
      List<Object> resList = new ArrayList<>();
      proceedList.forEach(proceed -> {
        if (Objects.nonNull(proceed)) {
          try {
            resList.add(processData(proceed));
          } catch (IllegalAccessException e) {
            e.printStackTrace();
          }
        } else {
          resList.add(null);
        }
      });
      return resList;
    }
    return proceedList;
  }

  private Object processData(Object proceed) throws IllegalAccessException {
    if (isDomain(proceed)) {
      return processDomain(proceed);
    }
    return proceed;
  }

  private Object processDomain(Object domainObj) throws IllegalAccessException {
    logger.info("DefaultTimeInterceptor.processDomain entered...");
    Field[] fields = ReflectUtil.getFields(domainObj.getClass());
    for (Field field : fields) {
      if (Date.class.equals(field.getType())) {
        field.setAccessible(true);
        Date date = (Date)field.get(domainObj);
        if (isDefaultDate(date)) {
          logger.info("DefaultTimeInterceptor.processDomain transforming data...");
          field.set(domainObj, null);
        }
      }
    }
    return domainObj;
  }

  private boolean isDefaultDate(Date date) {
    if (Objects.nonNull(date)) {
      return DEFAULT_TIME.equals(date);
    }
    return false;
  }

  private boolean isDomain(Object data) {
    return true;
  }

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {
  }
}
