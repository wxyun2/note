# spring mvc中获取request等方法

### 获取request

```java
public static final HttpServletRequest getRequest() {
    	return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
            												  .getRequest();
    }
```

### 获取response

```java
public static final HttpServletResponse getResponse(){
        return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
                                                              .getResponse();
    }
```

