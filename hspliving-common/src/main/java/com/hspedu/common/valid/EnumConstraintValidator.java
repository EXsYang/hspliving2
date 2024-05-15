package com.hspedu.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yangda
 * @create 2024-03-17-19:00
 * @description: 自定义校验器EnumConstraintValidator
 *
 * 注意要想让校验规则，或是自定义注解和自定义校验器生效，
 * 需要在Controller层，封装前端参数时，加上 @Valid注解！！！校验框架才会生效！！
 *
 * ConstraintValidator<> 自定义校验器需要实现的接口
 * Constraint: 限制;约束;限定;严管
 * <p>
 * 1. EnumConstraintValidator 是真正的校验器，即校验的逻辑是写在这里的
 * 2. EnumConstraintValidator需要实现接口 ConstraintValidator<A extends Annotation, T>
 * 3. <EnumValidate,Integer> 表示该校验器是针对 @EnumValidate注解 传入的 Integer类型的数据进行校验
 */
public class EnumConstraintValidator implements ConstraintValidator<EnumValidate, Integer> {


    //定义一个Set集合，用于收集EnumValidate传入的values
    private Set<Integer> set = new HashSet<>();
    // private String regStr = "";

    /*
        constraintAnnotation 就是标注在对象属性上的@EnumValidate注解
        通过该注解可以获取到EnumValidate注解的各个属性传入的值
    */
    @Override
    public void initialize(EnumValidate constraintAnnotation) {
        // 测试，看是否能够得到注解传入的values
        // 通过注解获取values,在Java基础的反射讲过
        int[] values = constraintAnnotation.values();
        for (int value : values) {
            // System.out.println("EnumValidate注解指定的values= " + value);
            set.add(value);
        }

        // regStr = constraintAnnotation.regexp();

    }

    // 这里是判断最终校验结果的方法
    /**
     * @param value   就是将来在前端的表单中传入的数据，即要校验的字段 前端表单字段值 封装到 有EnumValidate注解标注 的对象属性的值
     * @param context  ConstraintValidatorContext 是一个强大的工具，它不仅允许验证器自定义错误报告，还能通过各种方法精确控制验证过程中的错误反馈。
     * @return 如果返回true校验成功-通过，如果返回false就是校验失败！-没有通过
     * 如果 isValid 方法返回 false（即验证失败），
     * 则验证框架（如 Spring 的 Validation）会自动使用 @EnumValidate 注解中
     * 定义的 message 属性值作为错误消息返回给前端。
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // System.out.println("前端表单传入的value= " + value);

        return set.contains(value);

        // String strValue = String.valueOf(value);

        // compile:编译
        // Pattern pattern = Pattern.compile(regStr);
        // Matcher matcher = pattern.matcher(strValue);
            // return matcher.find();

        // return value.toString().matches(regStr);

        //---------------------
        // 如果你想在验证失败时提供自定义的错误消息而不是默认的 {javax.validation.constraints.Enum.message}，可以这样做
        // if (!set.contains(value)) {

        /**
         * context.disableDefaultConstraintViolation(); 禁用默认违规信息。
         * 即禁用自定义注解@EnumValidate中的 String message() default "{com.hspedu.common.valid.EnumValidate.message}";消息模板
         * 当你调用 context.disableDefaultConstraintViolation(); 方法时，
         * 你实际上是在告诉验证框架不要使用上述定义的默认消息模板来生成错误消息。
         * 也就是禁用的下面的message = "显示状态的值需要是0或者1~" 中的信息
         * ，禁用后可以在后面的 .buildConstraintViolationWithTemplate 再自己自定义错误信息
         *
         * @EnumValidate(values = {0,1},message = "显示状态的值需要是0或者1~")
         *  private Integer isshow; 也就是禁用的message = "显示状态的值需要是0或者1~"
         */
        //     context.disableDefaultConstraintViolation();  // 禁用默认违规信息

        //     创建新的错误消息
        //     context.buildConstraintViolationWithTemplate("输入的值 " + value + " 不在允许的枚举值内")
        //            .addPropertyNode("fieldName") // 指明违规的具体字段
        //            .addConstraintViolation();  // 添加自定义违规信息
        //     return false;
        // }
        // return true;
    }
}
