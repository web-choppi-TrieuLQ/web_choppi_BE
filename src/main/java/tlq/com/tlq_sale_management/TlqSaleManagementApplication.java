package tlq.com.tlq_sale_management;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TlqSaleManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TlqSaleManagementApplication.class, args);
    }

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "dgxahch2m",
                "api_key", "167376835152445",
                "api_secret", "UJ6S2AdMX3DjDV1bFN6_uxYiCQI",
                "secure", true
        ));
    }

}
