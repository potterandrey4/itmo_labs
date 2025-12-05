package itmo.soa.labworkservice;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import itmo.soa.labworkservice.config.WebMvcConfig;
import itmo.soa.labworkservice.config.JpaConfig;

public class Application extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] { JpaConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebMvcConfig.class };
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}