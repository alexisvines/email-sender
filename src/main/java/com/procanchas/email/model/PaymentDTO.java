package com.procanchas.email.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {

    public class PaymentMailDTO implements Serializable {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        private byte[] qrCode;

        private String qrCodeBase64;

        private String receptionistName;

        private String qrValue;

        private String clubUrl;

        private String clubName;

        private String clubAddress;

        private String cityName;

        private String date;

        private String initTime;

        private String endTime;

        private String presentationTime;

        private String fieldType;

        private String fieldName;

        private String fieldComforts;

        private String discountPercentage;

        private String discountValue;

        private String totalAmount;

        private String totalAmountWithDiscount;

        private Boolean isWithDiscount;

        private String clubPhone;

        private String playerName;

        private String playerEmail;

        private String playerRut;

        private String paymentNumber;

        private String paymentDate;

        private String appointmentNumber;

        private String paymentMethod;
}
