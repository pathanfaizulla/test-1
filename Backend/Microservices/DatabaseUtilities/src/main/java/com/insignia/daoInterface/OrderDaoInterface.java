package com.insignia.daoInterface;

import java.util.List;
import java.util.Optional;

import com.insignia.customExceptions.InvalidInputParametersException;
import com.insignia.entity.OrderDetails;

public interface OrderDaoInterface {

	public OrderDetails CreateOrder(OrderDetails orderdetails) throws InvalidInputParametersException;

	public OrderDetails updateOrder(OrderDetails orderDetails) throws InvalidInputParametersException;

	public void deleteOrder(Long orderSequenceNumber);

	public List<OrderDetails> getALLorderById(Long customerSequenceNumber) throws InvalidInputParametersException;

	public Optional<OrderDetails> findById(Long orderSequenceNumber);

	public List<OrderDetails> getAllOrders();

}
