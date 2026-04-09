package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.domain.api.ILogServicePort;
import com.pragma.powerup.domain.spi.ILogPersistencePort;
import com.pragma.powerup.infrastructure.out.mongodb.adapter.LogsMongodbAdapter;
import com.pragma.powerup.infrastructure.out.mongodb.mapper.ILogEntityMapper;
import com.pragma.powerup.infrastructure.out.mongodb.repository.ILogsOrdersRepository;
import com.pragma.powerup.domain.usecase.LogUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final ILogsOrdersRepository logsPedidosRepository;
    private final ILogEntityMapper logPedidoEntityMapper;

    @Bean
    public ILogServicePort logPedidoServicePort(){
        return new LogUseCase(logOrderPersistencePort());
    }

    @Bean
    public ILogPersistencePort logOrderPersistencePort(){
        return new LogsMongodbAdapter(logsPedidosRepository, logPedidoEntityMapper);
    }
}