package kvalidator

import kvalidator.rules.*

public class AttributeBuilder {

    internal val rules: HashMap<String, List<Rule>> = hashMapOf()

    public fun attribute(attribute: String, op: RuleBuilder.() -> Unit) {
        val rb = RuleBuilder()
        op(rb)
        rules[attribute] = rb.rules
    }
}

public class RuleBuilder {

    internal val rules: ArrayList<Rule> = arrayListOf()

    public fun accepted(): Accepted = Accepted()
    public fun afterDate(date: String): AfterDate = AfterDate(date)
    public fun alpha(): Alpha = Alpha()
    public fun alphaDash(): AlphaDash = AlphaDash()
    public fun alphaNum(): AlphaNum = AlphaNum()
    public fun between(min: Int, max: Int): Between = Between(min, max)
    public fun confirmed(): Confirmed = Confirmed()
    public fun email(): Email = Email()
    public fun isArray(): IsArray = IsArray()
    public fun isBoolean(): IsBoolean = IsBoolean()
    public fun isDate(): IsDate = IsDate()
    public fun isInteger(): IsInteger = IsInteger()
    public fun isNumeric(): IsNumeric = IsNumeric()
    public fun isString(): IsString = IsString()
    public fun max(value: Int): Max = Max(value)
    public fun min(value: Int): Min = Min(value)
    //public fun notIn(vararg values: String): NotIn = NotIn(*values)
    public fun required(): Required = Required()
    public fun size(value: Int): Size = Size(value)
    public fun url(): Url = Url()
}
