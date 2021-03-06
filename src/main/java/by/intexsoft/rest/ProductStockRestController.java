package by.intexsoft.rest;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.intexsoft.entity.Category;
import by.intexsoft.entity.Product;
import by.intexsoft.entity.Stock;
import by.intexsoft.repository.PriceRepository;
import by.intexsoft.service.CategoryService;
import by.intexsoft.service.ProductService;
import by.intexsoft.service.StockService;

/**
 * A controller that processes requests for information about products with
 * which the stock is running. The controller receives requests, processes the
 * information, and returns the responses to the user. The controller can
 * receive the object and send it to the {@link ProductService} repository, it
 * can handle get content of the {@link ProductService}.
 * 
 * @see {@link ProductService}, {@link RestController}, {@link Product},
 *      {@link PriceRepository}, {@link Stock}, {@link StockService}
 */
@RestController
public class ProductStockRestController {
	private static final String CONT_PRODUCT = "Cont product by stock and category.";
	private static final String FIND_PRODUCT_BY_STOCK_AND_CATEGORY = "Find product by stock and category.";
	private static final String APPLICATION_JSON = "application/json; charset=UTF-8";
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String FIND_PRODUCT = "Find product by stock id.";
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductRestController.class);
	private static final String PRODUCTS_NOT_FOUND = "Products not found.";
	private static final String MESSAGE = "Message";
	private StockService stockService;
	private ProductService productService;
	private CategoryService categoryService;

	@Autowired
	public ProductStockRestController(StockService stockService, ProductService productService,
			CategoryService categoryService) {
		this.productService = productService;
		this.stockService = stockService;
		this.categoryService = categoryService;
	}

	/**
	 * The controller method that processes requests for products in the stock.
	 */
	@RequestMapping(value = "/products/stock/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getProductsByStock(@PathVariable("id") int id) {
		LOGGER.info(FIND_PRODUCT);
		Stock stock = stockService.findOne(id);
		List<Product> products = productService.finByStock(stock);
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, APPLICATION_JSON);
		if (products == null) {
			headers.add(MESSAGE, PRODUCTS_NOT_FOUND);
			return new ResponseEntity<List<Product>>(headers, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Product>>(products, headers, HttpStatus.OK);
	}

	/**
	 * The controller method that processes requests for products in the warehouse
	 * of the corresponding category.
	 * 
	 * @see {@link Category}
	 */
	@RequestMapping(value = "/products/stock/{idStock}/category/{idCategory}", method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getByStockAndCategory(@PathVariable("idStock") int idStock,
			@PathVariable("idCategory") int idCategory) {
		LOGGER.info(FIND_PRODUCT_BY_STOCK_AND_CATEGORY);
		Stock stock = stockService.findOne(idStock);
		Category category = categoryService.findOne(idCategory);
		List<Product> products = productService.findByStockAndCategory(stock, category);
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, APPLICATION_JSON);
		if (products == null) {
			headers.add(MESSAGE, PRODUCTS_NOT_FOUND);
			return new ResponseEntity<List<Product>>(headers, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Product>>(products, headers, HttpStatus.OK);
	}

	/**
	 * The controller method that processes requests for the number of products in a
	 * warehouse of the corresponding category.
	 */
	@RequestMapping(value = "/products/stock/{idStock}/category/{idCategory}/count", method = RequestMethod.GET)
	public ResponseEntity<Integer> conuByStockAndCategory(@PathVariable("idStock") int idStock,
			@PathVariable("idCategory") int idCategory) {
		LOGGER.info(CONT_PRODUCT);
		Stock stock = stockService.findOne(idStock);
		Category category = categoryService.findOne(idStock);
		Integer countProducts = productService.countByStockAndCategory(stock, category);
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, APPLICATION_JSON);
		if (countProducts == null) {
			headers.add(MESSAGE, PRODUCTS_NOT_FOUND);
			return new ResponseEntity<Integer>(headers, HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(countProducts, headers, HttpStatus.OK);
	}
}
