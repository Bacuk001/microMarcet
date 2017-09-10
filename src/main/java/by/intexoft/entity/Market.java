package by.intexoft.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.AbstractPersistable;

/**
 * This class is the essence of the table with information about the stores that
 * will be served by the application.
 * 
 * @see {@link AbstractPersistable}
 */
@Entity
@Table
public class Market extends AbstractPersistable<Integer> {
	private static final long serialVersionUID = -8634209561396656881L;
	/**
	 * The name of the field of this class, which will be used to communicate with
	 * the essence of warehouses.
	 */
	public static final String USER_PROPERTY_VALUE = "users";

	/**
	 * The name of the field that stores products on the market.
	 */
	public static final String PRODUCT_PROPERTY_NAME = "products";
	/**
	 * The field contains the name of the store.
	 */
	@Column
	public String name;
	/**
	 * The field will update the store address with other information regarding the
	 * location of the store.
	 */
	@Column
	public String addres;
	/**
	 * The field will update the store address with other information regarding the
	 * location of the store.
	 */
	@Column
	public String about;
	/**
	 * The name of the field on which the bundle is carried out with the warehouse
	 * objects. We organize communication in tables many to many.
	 */
	@ManyToMany(mappedBy = Stock.MARKET_PROPERTY_NAME)
	public List<Stock> stocks;
	/**
	 * List of users that support this market.
	 */
	@OneToMany(fetch = LAZY, mappedBy = User.MERKET_PROPERTY_NAME, cascade = ALL)
	public List<User> user;

	/**
	 * The list of orders sent to the store for processing.
	 */
	@OneToMany(fetch = LAZY, mappedBy = Order.MARCET_PROPERTY_NAME, cascade = ALL)
	public List<Order> orders;

	/**
	 * Products that are available in the store.
	 */
	@ManyToMany(mappedBy = Product.MARKET_PROPERTY_NAME)
	public List<Product> pruducts;

}
