package com.sushi.pojo;

import org.ansj.domain.Term;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author sushi
 * @create 2019-11-18 8:59 PM
 */
public class RuleChain {
    //该规则的名称
    private String name;
    //规则链初始节点
    private RuleNode firstNode;


    /**
     * 初始化ruleChain
     * @param chainName 该规则的名称
     * @param regex 该规则的具体内容
     */
    public RuleChain(String chainName,String regex) {
        setName(chainName);
        initRuleChain(regex);
    }

    public RuleChain(String regex) {
        initRuleChain(regex);
    }



    /**
     * 根据规则生成规则链
     * !暂时未实现智能生成,所以生成写死的规则!
     * ns|ns_key - [f] - [b] - (mq | m - [q])+
     */
    public void initRuleChain(String regex){
        firstNode = new BranchNode();
        RuleNode ns = new RuleNode("ns");
        RuleNode ns_key = new RuleNode("ns_key");

        ((BranchNode) firstNode).addNextRuleNode(ns);
        ((BranchNode) firstNode).addNextRuleNode(ns_key);

        RuleNode f = new RuleNode("f",true);
        ns.setNextRuleNode(f);
        ns_key.setNextRuleNode(f);

        RuleNode b = new RuleNode("b",true);
        f.setNextRuleNode(b);

        BranchNode secBranch = new BranchNode();
        b.setNextRuleNode(secBranch);

        RuleNode mq = new RuleNode("mq");
        RuleNode m = new RuleNode("m");
        RuleNode q = new RuleNode("q",true);
        m.setNextRuleNode(q);

        secBranch.addNextRuleNode(mq);
        secBranch.addNextRuleNode(m);

        mq.setNextRuleNode(secBranch);
        q.setNextRuleNode(secBranch);
    }

    public static void main(String[] args) {
        RuleChain m = new RuleChain("");
        System.out.println(m);
    }

    public RuleNode getFirstNode() {
        return firstNode;
    }

    public void setFirstNode(RuleNode firstNode) {
        this.firstNode = firstNode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
