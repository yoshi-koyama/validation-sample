package com.validationsample.validation;

/**
 * ビザ申請書
 */
public class VisaApplicationForm {

    /**
     * 名前
     */
    @ValidVisaAge
    private Integer age;

    public VisaApplicationForm(Integer age) {
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }
}
