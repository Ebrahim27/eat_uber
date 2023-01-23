package com.ebi.snap_food.anotation;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;



public class AttackProtectionCreate implements ConstraintValidator<AttackProtection, String> {
    @Override
    public void initialize(AttackProtection constraintAnnotation) {

    }

    @Override
    public boolean isValid(String attack, ConstraintValidatorContext constraintValidatorContext) {

        Pattern pattern;
        //sqlInjection
        pattern = Pattern.compile("[\\s]*((delete)|(exec)|(drop\\s*table)|(insert)|(shutdown)|(update)|(\\bor\\b))");
        if (pattern.matcher(String.valueOf(attack)).find())
            return false;
        //Server-Side Include Injection
        pattern = Pattern.compile("<!--#(include|exec|echo|config|printenv)\\s+.*");
        if (pattern.matcher(String.valueOf(attack)).find())
            return false;

        //XML encoded:
        pattern = Pattern.compile("&lt;!--#(include|exec|echo|config|printenv)\\s+.*");
        if (pattern.matcher(String.valueOf(attack)).find())
            return false;

        //XPath Expanded Syntax Injection
        pattern = Pattern.compile("/?(ancestor(-or-self)?|descendant(-or-self)?|following(-sibling))");
        if (pattern.matcher(String.valueOf(attack)).find())
            return false;
        //JavaScript Injection
        pattern = Pattern.compile("<\\s*script\\b[^>]*>[^<]+<\\s*/\\s*script\\s*>");
        if (pattern.matcher(String.valueOf(attack)).find())
            return false;

        pattern = Pattern.compile("&lt;\\s*script\\b[^&gt;]*&gt;[^&lt;]+&lt;\\s*/\\s*script\\s*&gt;");
        if (pattern.matcher(String.valueOf(attack)).find())
            return false;

        pattern = Pattern.compile(".*?Exception in thread.*");
        if (pattern.matcher(String.valueOf(attack)).find())
            return false;


        return true;
    }

}