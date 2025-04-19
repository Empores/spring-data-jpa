package com.learning.springdatajpa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.springdatajpa.controller.ProductController;
import com.learning.springdatajpa.entity.Product;
import com.learning.springdatajpa.repository.ProductRepository;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
 class SpringDataJpaApplicationTests {

	@Autowired
	private ProductController productController;

	@Autowired
	private MockMvc mockMvc;


	@MockitoBean
	private ProductRepository productRepository;

	@Before
	public void setupMvc(){
		this.mockMvc = MockMvcBuilders.standaloneSetup(ProductController.class).build();
	}

	@Test
	public void addProductTest() throws Exception {

		Product demoProduct = new Product("TV", 1000,"demo","electronics",null,null,null,null);
		when(productRepository.save(any())).thenReturn(demoProduct);
		mockMvc.perform(MockMvcRequestBuilders
				.post("/products")
				.content(convertObjectAsString(demoProduct))
				.contentType("application/json")
				.accept("application/json"))
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
	}

	public String convertObjectAsString(Object object){
		try{
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

	@Test
	public void getProductsTest() throws Exception {
		when(productRepository.findAll()).thenReturn(Arrays.asList(
				new Product(1,"TV", 1000,"demo1","electronics",null,null,null,null),
				new Product(2,"washing machine", 10000,"demo2","electronics",null,null,null,null)
		));
		mockMvc.perform(MockMvcRequestBuilders
				.get("/products")
				.accept("application/json"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.*").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1));
	}

	@Test
	public void getProductByIdTest() throws Exception {
		Product demoProduct = new Product(108,"TV", 1000,"demo","electronics",null,null,null,null);

		when(productRepository.findById(108)).thenReturn(Optional.of(demoProduct));
		mockMvc.perform(MockMvcRequestBuilders
				.get("/products/byid/"+108)
				.accept("application/json"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(108));
	}

	@Test
	public void updateProductTest() throws Exception {
		Product demoProduct = new Product(108,"TV", 1000,"demo","electronics",null,null,null,null);

		when(productRepository.findById(108)).thenReturn(Optional.of(demoProduct));
		when(productRepository.save(any())).thenReturn(new Product(108,"Fridge",5000,"demo1","home",null,null,null,null));
		mockMvc.perform(MockMvcRequestBuilders
			.put("/products/"+108)
				.content(convertObjectAsString(demoProduct))
				.contentType("application/json")
				.accept("application/json"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Fridge"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.description").value("demo1"));

	}

	@Test
	public void deleteProductTest() throws Exception {

		Mockito.doNothing().when(productRepository).deleteById(anyInt());
		when(productRepository.count()).thenReturn(1L);
		mockMvc.perform(MockMvcRequestBuilders
				.delete("/products/id",12)
		).andDo(print());
				//.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
	}

	@Test
	public void getSensitiveInfoEncryptor(){
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig config =new SimpleStringPBEConfig();
		config.setPassword("vani");
		config.setAlgorithm("PBEWithMD5AndDES");
		config.setKeyObtentionIterations(1000);
		config.setPoolSize(1);
		config.setProviderName("SunJCE");
		config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
		config.setStringOutputType("base64");
		encryptor.setConfig(config);
		System.out.println(encryptor.encrypt("Ragasiyam2"));
		//return encryptor;
	}

}
