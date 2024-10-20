package coupon.config;


import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;

@Component
public class CouponRoutingContextCleanupFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        chain.doFilter(request, response);
        CouponRoutingContext.removeCouponCreatedAt();
        CouponRoutingContext.clearCurrentCouponId();
    }
}
