package com.clownstore.product;

import com.clownstore.product.exception.ProductExistsException;
import com.clownstore.product.model.Product;
import com.clownstore.product.repository.ProductRepository;
import com.clownstore.product.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductApplicationTests {

	@Mock
	ProductRepository repository;

	@InjectMocks
	ProductService service;

	@Test
	@DisplayName("Test if duplicated products can save")
	void test1() {
		Product mock = new Product("abc","Coffe", BigDecimal.TEN, 10, "dark coffe", Map.of("origin", "arabic"));
		when(repository.findByNameContaining(mock.getName())).thenReturn(Optional.of(mock));
		assertThrows(ProductExistsException.class, ()-> service.save(mock));

		verify(repository, never()).save(any(Product.class));
	}

	@Test
	@DisplayName("Test if a saved entity updates price by name")
	void test2(){
		Product mock = new Product("abc","Coffe", BigDecimal.TEN, 10, "dark coffe", Map.of("origin", "arabic"));
		when(repository.findByNameContaining(mock.getName())).thenReturn(Optional.of(mock));

		service.updatePriceByName(BigDecimal.ONE, mock.getName());

		verify(repository).save(mock);

		assertEquals(BigDecimal.ONE, mock.getPrice());
	}
}
