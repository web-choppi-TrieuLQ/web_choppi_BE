package tlq.com.tlq_sale_management.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tlq.com.tlq_sale_management.config.VNPayConfig;
import tlq.com.tlq_sale_management.dto.request.ShoppingCartRequest.PaymentDTO;
import tlq.com.tlq_sale_management.dto.response.SystemRespone.ApiResponse;
import tlq.com.tlq_sale_management.model.ShoppingCart;
import tlq.com.tlq_sale_management.service.ShoppingCartService.ShoppingCartServiceImpl;
import tlq.com.tlq_sale_management.service.UserService.UserService;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    @Autowired
    ShoppingCartServiceImpl shoppingCartService;

    @Autowired
    UserService userService;

    @PostMapping("")
    private ResponseEntity<Object> payment(@RequestBody PaymentDTO paymentDTO) throws UnsupportedEncodingException {

        String amount = String.valueOf(paymentDTO.getTotalPayment() * 100);
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
//        String vnp_ReturnUrl = "http://localhost:3000/paymentOk/" + paymentDTO.getIdAccount();
        String vnp_ReturnUrl = "http://localhost:3000/payment/" + paymentDTO.getIdAccount();
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);

        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        System.out.println("đây là paymentUrl: " + paymentUrl);



//        User user = userService.findById(paymentDTO.getIdAccount());
//
//        List<ShoppingCart> shoppingCartList = user.getShoppingCarts();

        ApiResponse<String> response = new ApiResponse<>();
        response.setResult(paymentUrl);

        return new ResponseEntity<>(response, HttpStatus.OK);


//        ShoppingCart shoppingCart = iShoppingCartService.findShoppingCartByUserAndStatus(user,"unpaid");
//        List<ProductDTO> list = iProductService.detailCartIndividual(shoppingCart.getId());


//        Product product;
//        List<Product> productDTOListError = new ArrayList<>();
//
//
//        for (ProductDTO temp : list){
//            product = iProductService.findById(temp.getIdProduct());
//            if (product == null){
//                return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
//            } else {
//                if (temp.getQuantity() > product.getQuantity()){
//                    productDTOListError.add(iProductService.findById(temp.getIdProduct()));
//                }
//
//            }
//        }
//
//
//        if (productDTOListError.isEmpty()){
//            ApiResponse<String> response = new ApiResponse<>();
//            response.setDataRes(paymentUrl);
//            response.setFlag("FOUND");
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        } else {
//            ApiResponse<List<Product>> response =  new ApiResponse<>();
//            response.setDataRes(productDTOListError);
//            response.setFlag("NOT");
//            return new ResponseEntity<>(response, HttpStatus.OK);
//        }



    }

    @GetMapping("/{userId}")
    public  ResponseEntity<Object> handlePaymentInfo(
            @PathVariable Long userId,
            @RequestParam(value = "vnp_Amount", required = false) String amount,
            @RequestParam(value = "vnp_BankCode", required = false) String bankCode,
            @RequestParam(value = "vnp_BankTranNo", required = false) String bankTranNo,
            @RequestParam(value = "vnp_CardType", required = false) String cardType,
            @RequestParam(value = "vnp_OrderInfo", required = false) String orderInfo,
            @RequestParam(value = "vnp_PayDate", required = false) String payDate,
            @RequestParam(value = "vnp_ResponseCode", required = false) String statusCode,
            @RequestParam(value = "vnp_TmnCode", required = false) String tmnCode,
            @RequestParam(value = "vnp_TransactionNo", required = false) String transactionNo,
            @RequestParam(value = "vnp_TransactionStatus", required = false) String transactionStatus,
            @RequestParam(value = "vnp_TxnRef", required = false) String txnRef,
            @RequestParam(value = "vnp_SecureHash", required = false) String secureHash) {

        log.info("bat dau ham payment");
        // Xử lý thông tin thanh toán ở đây
        System.out.println("status: "+ transactionStatus);
        if (!Objects.equals(transactionStatus, "00")){
            return new ResponseEntity<>("NO", HttpStatus.OK);
        }

        List<ShoppingCart> shoppingCartList = shoppingCartService.getCartsByUserId(userId);

        for (int i = 0; i < shoppingCartList.size(); i++) {
            shoppingCartService.delete(shoppingCartList.get(i));
        }

        log.info("ket thuc ham payment-ok");

        return new ResponseEntity<>("thanh toan thanh cong", HttpStatus.OK);
    }

}
