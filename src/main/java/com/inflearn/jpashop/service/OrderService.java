package com.inflearn.jpashop.service;

import com.inflearn.jpashop.domain.Delivery;
import com.inflearn.jpashop.domain.Member;
import com.inflearn.jpashop.domain.Order;
import com.inflearn.jpashop.domain.OrderItem;
import com.inflearn.jpashop.domain.item.Item;
import com.inflearn.jpashop.repository.ItemRepository;
import com.inflearn.jpashop.repository.MemberRepository;
import com.inflearn.jpashop.repository.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final MemberRepository memberRepository;
  private final ItemRepository itemRepository;

  //주문
  /**
   * 주문
   */
  @Transactional
  public Long order(Long memberId, Long itemId, int count) {

    //엔티티 조회
    Member member = memberRepository.findOne(memberId);
    Item item = itemRepository.findOne(itemId);

    //배송정보 생성
    Delivery delivery = new Delivery();
    delivery.setAddress(member.getAddress());

    //주문상품 생성
    OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

    //주문 생성
    Order order = Order.createOrder(member, delivery, orderItem);

    //주문 저장
    orderRepository.save(order);

    return order.getId();
  }

  //취소
  /**
   * 주문 취소
   */
  @Transactional
  public void cancelOrder(Long orderId) {
    //주문 엔티티 조회
    Order order = orderRepository.findOne(orderId);
    //주문 취소
    order.cancel();
  }

  //검색
//  public List<Order> findOrders(OrderSearch orderSearch) {
//    return orderRepository.findAll(orderSearch);
//  }
}
