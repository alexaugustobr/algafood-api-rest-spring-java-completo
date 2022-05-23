package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
class FluxoPedidoServiceTest {

	static EmissaoPedidoService emissaoPedidoService;
	static ApplicationEventPublisher eventPublisher;
	static PedidoRepository pedidoRepository;
	
	@InjectMocks
	FluxoPedidoService fluxoPedidoService;
	
	@BeforeAll
	public static void setup() {
		emissaoPedidoService = Mockito.mock(EmissaoPedidoService.class);
		eventPublisher = Mockito.mock(ApplicationEventPublisher.class);
		pedidoRepository = Mockito.mock(PedidoRepository.class);
		
		Pedido pedido = new Pedido();
		pedido.setCodigo("1");
		
		Mockito.when(emissaoPedidoService.buscarOuFalhar("1"))
				.thenReturn(pedido);

		Mockito.when(pedidoRepository.save(Mockito.any()))
				.thenReturn(pedido);
	}
	
	@Test
	void cancelar() {
		fluxoPedidoService.cancelar("1");
		Mockito.verify(eventPublisher, Mockito.times(1))
				.publishEvent(Mockito.any(PedidoCanceladoEvent.class));
	}
}