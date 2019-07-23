package net.ruixin;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Component
public class MyFilter extends ZuulFilter {
    private static final long EXPIRE_TIME = 30 * 60 * 1000;
    private static Logger log = LoggerFactory.getLogger(MyFilter.class);

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
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpSession session = request.getSession();
//        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        Boolean access=false;
        Object paramToken=request.getParameter("token");
        Object sessionToken=session.getAttribute("token");
        if (sessionToken!=null&&sessionToken.equals(paramToken)) {
            access = true;
        }
        if (!access) {
            log.warn("token错误");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            try {
                ctx.getResponse().getWriter().write("token错误");
            } catch (Exception e) {
            }

            return null;
        }
        // 设置过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        String token = JWT.create().withAudience("1231").withExpiresAt(date).sign(Algorithm.HMAC256("yucheng697094"));
        ctx.addZuulRequestHeader("token", token);
        log.info("ok");
        return null;
    }
}

