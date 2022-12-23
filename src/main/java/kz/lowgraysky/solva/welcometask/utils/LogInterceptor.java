package kz.lowgraysky.solva.welcometask.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.util.Map;

@Component
public class LogInterceptor extends BeanHelper implements HandlerInterceptor {

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HttpServletRequest requestCacheWrapperObject = new ContentCachingRequestWrapper(request);
//        requestCacheWrapperObject.getParameterMap();
//        Map<String, Object> inputMap = new ObjectMapper().readValue(requestCacheWrapperObject.getInputStream(), Map.class);
//        logger.info(String.format("Incoming request: %s", inputMap));
//        return true;
//    }
}
