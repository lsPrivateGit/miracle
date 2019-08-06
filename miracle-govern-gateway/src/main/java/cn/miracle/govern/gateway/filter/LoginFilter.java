package cn.miracle.govern.gateway.filter;

import cn.miracle.framework.common.model.response.CommonCode;
import cn.miracle.framework.common.model.response.ResponseResult;
import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 类功能描述
 *
 * @author Leon
 * @version 2019/7/27 22:32
 */
//@Component
public class LoginFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 返回true表示要执行此过虑器
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        // 从header中取jwt
        String authorization = getJwtFromHeader(request);
        if (StringUtils.isBlank(authorization)) {
            accessDenied();
            return null;
        }
        // TODO
        /*long expire = authService.getExpire(token);
        if (expire < 0) {
            accessDenied();
            return null;
        }*/
        return null;
    }

    private void accessDenied() {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletResponse response = currentContext.getResponse();
        currentContext.setSendZuulResponse(false);
        currentContext.setResponseStatusCode(200);
        ResponseResult responseResult = new ResponseResult(CommonCode.UNAUTHENTICATED);
        currentContext.setResponseBody(JSON.toJSONString(responseResult));
        response.setContentType("application/json;charset=utf-8");
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (StringUtils.isBlank(authorization)) {
            return null;
        }
        if (!StringUtils.startsWith(authorization, "Bearer ")) {
            return null;
        }
        return StringUtils.substringAfter(authorization, "Bearer ");
    }
}