package com.yiduofar.serviceplatform.validator;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author HuiBBao
 * @create 2023/2/7 15:02
 */
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;


    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (required) {
            return isMobile(s);
        } else {
            if (s.isEmpty()) {
                return true;
            } else {
                return isMobile(s);
            }
        }
    }

    private static final Pattern mobile_pattern = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[^1^4,\\D]))\\d{8}");

    public static boolean isMobile(String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return false;
        }
        Matcher matcher = mobile_pattern.matcher(mobile);
        return matcher.matches();
    }

}
