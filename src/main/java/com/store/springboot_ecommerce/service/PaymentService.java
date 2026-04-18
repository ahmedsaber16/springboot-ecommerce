package com.store.springboot_ecommerce.service;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.store.springboot_ecommerce.model.Payment;
import com.store.springboot_ecommerce.model.PaymentStatus;
import com.store.springboot_ecommerce.model.PaymentType;
import com.store.springboot_ecommerce.model.User;
import com.store.springboot_ecommerce.model.Wallet;
import com.store.springboot_ecommerce.model.WalletTransaction;
import com.store.springboot_ecommerce.dto.PaymentResponse;
import com.store.springboot_ecommerce.model.Order;
import com.store.springboot_ecommerce.model.OrderStatus;
import com.store.springboot_ecommerce.repository.OrderRepo;
import com.store.springboot_ecommerce.repository.PaymentRepo;
import com.store.springboot_ecommerce.repository.TransactionRepo;
import com.store.springboot_ecommerce.repository.UserRepo;
import com.store.springboot_ecommerce.repository.WalletRepo;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepo paymentRepo;
    private final OrderRepo orderRepo;
    private final WalletRepo walletRepo;
    private final TransactionRepo transactionRepo;
    private final UserRepo userRepo;

    @Transactional
    public PaymentResponse pay(Long orderId ){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();

        User user = userRepo.findByEmail(currentUser)
                        .orElseThrow(()-> new RuntimeException("user not authenticated"));
        Wallet wallet = walletRepo.findByUserId(user.getId())
                        .orElseThrow(()-> new RuntimeException("wallet not found"));

        Order order = orderRepo.findById(orderId)
                        .orElseThrow(()-> new RuntimeException("order not found"));


                        if(!order.getUser().getId().equals(user.getId())){
                            throw new RuntimeException("you are not authorized to pay for this order ");
                        }
        if(order.getStatus() == OrderStatus.PAID){
            throw new RuntimeException("order is already paid");
        }

        BigDecimal totalAmount = order.getTotalPrice();
        if(wallet.getBalance().compareTo(totalAmount) < 0){
            throw new RuntimeException("insufficient balance");
        }

        wallet.setBalance(wallet.getBalance().subtract(totalAmount));
        walletRepo.save(wallet);

        WalletTransaction transaction = new WalletTransaction();
        transaction.setWalletId(wallet.getId());
        transaction.setAmount(totalAmount);
        transaction.setType(PaymentType.DEBIT);
        transaction.setDescription("Payment for order #" + orderId );
        transactionRepo.save(transaction);

        order.setStatus(OrderStatus.PAID);
        orderRepo.save(order);

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(totalAmount);
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setTransactionId(UUID.randomUUID().toString());
        paymentRepo.save(payment);


        PaymentResponse res = new PaymentResponse();
        res.setAmount(payment.getAmount());
        res.setStatus(payment.getStatus().name());
        res.setTransactionId(payment.getTransactionId());
        res.setMessage("Payment processed successfully");

        return res;
    }
}