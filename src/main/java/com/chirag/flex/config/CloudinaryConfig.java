package com.chirag.flex.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "djz9jmfeh",
                "api_key", "198632467812969",
                "api_secret", "jn5fQWcctxOLD_vJV8Y1SPWlrLo"
        ));
    }
}