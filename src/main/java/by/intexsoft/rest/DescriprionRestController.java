package by.intexsoft.rest;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import by.intexsoft.entity.Description;
import by.intexsoft.entity.Product;
import by.intexsoft.service.DescriptionService;
import by.intexsoft.service.ProductService;

/**
 * A controller that processes requests for a description, the controller works
 * with a service that provides information. {@link RestController},
 * 
 * @see {@link DescriptionService}, {@link Description} {@link ResponseEntity}.
 */
@RestController
public class DescriprionRestController {
	private static final String DESCRIPTION_NOT_FOUND = "Description for product not found.";
	private static final String SAVE_DESCRIPTIONS_PRODUCT = "Save list descriptions for product.";
	private static final String DESCRIPTIONS_NOT_SAVE = "Descriptions do not save";
	private static final String APPLICATION_JSON = "application/json; charset=UTF-8";
	private static final String CONTENT_TYPE = "Content-Type";
	private static final String FIND_DESCRIPRION_BY_PRODUCT = "Find Descriprion by product in database.";
	private static final Logger LOGGER = LoggerFactory.getLogger(DescriprionRestController.class);
	private static final String MESSAGE = "Message";
	private DescriptionService descriptionService;
	private ProductService productService;

	@Autowired
	public DescriprionRestController(DescriptionService descriptionService, ProductService productService) {
		this.descriptionService = descriptionService;
		this.productService = productService;
	}

	/**
	 * A controller that processes requests for a description of the product.
	 * 
	 * @see {@link Product}
	 */
	@RequestMapping(value = "/description/product/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<Description>> getByProduct(@PathVariable("id") int id) {
		LOGGER.info(FIND_DESCRIPRION_BY_PRODUCT);
		Product product = productService.findOne(id);
		List<Description> descriptions = descriptionService.findByProduct(product);
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, APPLICATION_JSON);
		if (descriptions == null) {
			LOGGER.info(DESCRIPTION_NOT_FOUND);
			headers.add(MESSAGE, DESCRIPTION_NOT_FOUND);
			return new ResponseEntity<List<Description>>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Description>>(descriptions, headers, HttpStatus.OK);
	}

	/**
	 * A controller that processes requests to save a list of descriptions. the
	 * controller receives a list and sends it to the service for saving. After
	 * successful execution, the controller sends a response that the save was
	 * successful.
	 */
	@RequestMapping(value = "/description/save", method = RequestMethod.POST)
	public ResponseEntity<List<Description>> save(@RequestBody List<Description> descriptions) {
		LOGGER.info(SAVE_DESCRIPTIONS_PRODUCT);
		HttpHeaders headers = new HttpHeaders();
		headers.add(CONTENT_TYPE, APPLICATION_JSON);
		descriptions = descriptionService.saveListDescription(descriptions);
		if (descriptions == null) {
			headers.add(MESSAGE, DESCRIPTIONS_NOT_SAVE);
			return new ResponseEntity<List<Description>>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<Description>>(headers, HttpStatus.OK);
	}
}
