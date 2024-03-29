package control.tower.address.service;

import control.tower.address.service.command.interceptors.CreateAddressCommandInterceptor;
import control.tower.address.service.command.interceptors.RemoveAddressCommandInterceptor;
import control.tower.address.service.core.errorhandling.AddressServiceEventsErrorHandler;
import control.tower.core.config.XStreamConfig;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@EnableDiscoveryClient
@SpringBootApplication
@Import({ XStreamConfig.class })
public class AddressServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AddressServiceApplication.class, args);
	}

	@Autowired
	public void registerCreateAddressCommandInterceptor(ApplicationContext context, CommandBus commandBus) {
		commandBus.registerDispatchInterceptor(
				context.getBean(CreateAddressCommandInterceptor.class)
		);
		commandBus.registerDispatchInterceptor(
				context.getBean(RemoveAddressCommandInterceptor.class)
		);
	}

	@Autowired
	public void configure(EventProcessingConfigurer configurer) {
		configurer.registerListenerInvocationErrorHandler("address-group",
				configuration -> new AddressServiceEventsErrorHandler());
	}

}
