package kvalidator

import kvalidator.rules.*

public class AttributeBuilder {

    internal val rules: HashMap<String, List<Rule>> = hashMapOf()

    public fun attribute(attribute: String, op: RuleBuilder.() -> Unit) {
        val rb = RuleBuilder()
        op(rb)
        rules[attribute] = rb.rules
    }

    public fun attribute(attribute: String, rules: String) {
        this.rules[attribute] = parseRules(rules)
    }
}

public class RuleBuilder {

    internal val rules: ArrayList<Rule> = arrayListOf()

    public fun accepted() { rules.add(Accepted()) }
    public fun afterDate(date: String) { rules.add(AfterDate(date)) }
    public fun alpha() { rules.add(Alpha()) }
    public fun alphaDash() { rules.add(AlphaDash()) }
    public fun alphaNum() { rules.add(AlphaNum()) }
    public fun between(min: Int, max: Int) { rules.add(Between(min, max)) }
    public fun confirmed() { rules.add(Confirmed()) }
    public fun email() { rules.add(Email()) }
    public fun isArray() { rules.add(IsArray()) }
    public fun isBoolean() { rules.add(IsBoolean()) }
    public fun isDate() { rules.add(IsDate()) }
    public fun isIn(vararg values: String) { rules.add(IsIn(*values)) }
    public fun isInteger() { rules.add(IsInteger()) }
    public fun isNumeric() { rules.add(IsNumeric()) }
    public fun isString() { rules.add(IsString()) }
    public fun max(value: Int) { rules.add(Max(value)) }
    public fun min(value: Int) { rules.add(Min(value)) }
    public fun notIn(vararg values: String) { rules.add(NotIn(*values)) }
    public fun required() { rules.add(Required()) }
    public fun size(value: Int) { rules.add(Size(value)) }
    public fun url() { rules.add(Url()) }
}
