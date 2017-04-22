package com.stardust.machine.registry;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.context.request.RequestContextListener;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class MachineRegistryServiceApplication {

	static Logger logger = Logger.getLogger(MachineRegistryServiceApplication.class);

	static {
		try{
			ClassLoader cl = MachineRegistryServiceApplication.class.getClassLoader();
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
			Resource[] resources = resolver.getResources("classpath:/log4j.properties") ;
			for(Resource log4j : resources) {
				PropertyConfigurator.configure(log4j.getURL());
			}
		}catch (Exception e){
			logger.error(e.getMessage());
		}
	}

	public static void main(String[] args) {
		final SpringApplication app = new SpringApplication(MachineRegistryServiceApplication.class);
		app.run(args);
	}

	@PostConstruct
	public void init() {
	}

	@Bean
	@ConditionalOnMissingBean(RequestContextListener.class)
	public RequestContextListener requestContextListener() {
		return new RequestContextListener();
	}
}
