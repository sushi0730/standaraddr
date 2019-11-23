package com.sushi.pojo;

import org.ansj.domain.Term;

import java.util.ArrayList;
import java.util.List;

/**
 * 规则链实例类
 * @Author sushi
 * @create 2019-11-18 10:25 PM
 */
public class RuleMatch {
    //规则链当前执行的节点
    private RuleNode currentNode;
    //命中的结果
    private List<Term> matchList;
    //规则链
    private RuleChain ruleChain;
    //是否完成匹配
    private boolean finishFlag;

    public RuleMatch(RuleChain ruleChain) {
        this.ruleChain = ruleChain;
        currentNode = ruleChain.getFirstNode();
        matchList = new ArrayList<>();
    }

    /**
     * 复制一个实例
     * @param curMatch
     */
    public RuleMatch(RuleMatch curMatch) {
        currentNode = curMatch.getCurrentNode();
        matchList = new ArrayList<>(curMatch.getMatchList());
        ruleChain = curMatch.getRuleChain();
    }

    public boolean isFinishFlag() {
        return finishFlag;
    }

    public void setFinishFlag(boolean finishFlag) {
        this.finishFlag = finishFlag;
    }

    public RuleNode getCurrentNode() {
        return currentNode;
    }

    public void setCurrentNode(RuleNode currentNode) {
        this.currentNode = currentNode;
    }

    public List<Term> getMatchList() {
        return matchList;
    }

    public void setMatchList(List<Term> matchList) {
        this.matchList = matchList;
    }

    public RuleChain getRuleChain() {
        return ruleChain;
    }

    public void setRuleChain(RuleChain ruleChain) {
        this.ruleChain = ruleChain;
    }

    /**
     * 新增匹配到的分词 到 matchList
     */
    public void addMatchWord(Term word) {
        if (matchList == null) {
            matchList = new ArrayList<>();
        }
        matchList.add(word);
    }
}
