package com.spring.account.services.impl;

import com.spring.account.dtos.payment.PaymentDto;
import com.spring.account.entities.payment.Payment;
import com.spring.account.entities.payment.PaymentMapper;
import com.spring.account.entities.payment.exceptions.PaymentExistsException;
import com.spring.account.entities.payment.exceptions.PaymentNotExistsException;
import com.spring.account.entities.user.User;
import com.spring.account.entities.user.exceptions.UserNotExistException;
import com.spring.account.repos.PaymentRepository;
import com.spring.account.repos.UserRepository;
import com.spring.account.services.bases.PaymentService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

/**
 * @author Alex Giazitzis
 */
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    PaymentRepository paymentRepository;
    PaymentMapper  paymentMapper;
    UserRepository userRepository;

    @Transactional
    @Override
    public void createPayments(final List<PaymentDto> paymentDtoList) {

        for (PaymentDto dto : paymentDtoList) {

            Optional<User> optionalUser = userRepository.findUserByEmailIgnoreCase(dto.getEmail());
            if (optionalUser.isEmpty()) {
                throw new UserNotExistException();
            }

            User    user    = optionalUser.get();
            Payment payment = paymentMapper.getPayment(dto, user);

            if (!user.getPaymentSet().add(payment)) {
                throw new PaymentExistsException();
            }

            paymentRepository.save(payment);
            userRepository.save(user);

        }

    }

    @Override
    public PaymentDto getPayment(final Long userId, final YearMonth period) {

        Optional<Payment> optionalPayment = paymentRepository.findByPeriodAndUserId(period, userId);
        if (optionalPayment.isEmpty()) {
            throw new PaymentNotExistsException();
        }

        return paymentMapper.getDto(optionalPayment.get());

    }

    @Override
    public List<PaymentDto> getAllPayments(final Long userId) {
        return paymentMapper.getDtoList(paymentRepository.findAllByUserIdOrderByPeriodDesc(userId));
    }

    @Override
    public void updatePayment(final PaymentDto paymentDto) {

        Optional<User> optionalUser = userRepository.findUserByEmailIgnoreCase(paymentDto.getEmail());

        if (optionalUser.isEmpty()) {
            throw new UserNotExistException();
        }

        User user = optionalUser.get();

        Payment payment = paymentMapper.getPayment(paymentDto, user);

        Optional<Payment> optionalPayment = user.getPaymentSet().stream().filter(p -> p.equals(payment)).findAny();
        if (optionalPayment.isPresent()) {

            Payment p = optionalPayment.get();
            p.setSalary(payment.getSalary());
            paymentRepository.save(p);
            userRepository.save(user);
            return;

        }

        user.getPaymentSet().add(payment);
        paymentRepository.save(payment);
        userRepository.save(user);

    }

}
