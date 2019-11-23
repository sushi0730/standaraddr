package com.sushi.pojo;

/**
 * 用于存储规则的节点
 * @Author sushi
 * @create 2019-11-16 4:48 PM
 */
public class RuleNode {
    //规则
   private Rule rule;
   //下一个规则节点
   private RuleNode nextRuleNode;
   //是否是分支节点，分支节点是子类，且不存储规则
   private boolean isBranch;
   //是否是附加节点，即节点里的规则可满足也可以不满足
   private boolean isExt;

    public RuleNode() {
    }

    public RuleNode(Rule rule) {
        this.rule = rule;
    }


    public RuleNode(String type) {
        rule = new Rule(type);
    }

    public boolean isExt() {
        return isExt;
    }

    public void setExt(boolean ext) {
        isExt = ext;
    }

    public RuleNode(String type, boolean isExt) {
        rule = new Rule(type);
        this.isExt = isExt;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public RuleNode getNextRuleNode() {
        return nextRuleNode;
    }

    public void setNextRuleNode(RuleNode nextRuleNode) {
        this.nextRuleNode = nextRuleNode;
    }

    public boolean isBranch() {
        return isBranch;
    }

    public void setBranch(boolean branch) {
        isBranch = branch;
    }
}
