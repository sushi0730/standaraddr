package com.sushi.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * 分支节点，不存储规则，主要用作分支
 * @Author sushi
 * @create 2019-11-18 8:45 PM
 */
public class BranchNode extends RuleNode {

    public BranchNode() {
        setBranch(true);
    }

    private List<RuleNode> nextRuleNodes;

    public List<RuleNode> getNextRuleNodes() {
        return nextRuleNodes;
    }

    public void setNextRuleNodes(List<RuleNode> nextRuleNodes) {
        this.nextRuleNodes = nextRuleNodes;
    }
    public void addNextRuleNode(RuleNode ruleNode){
        if (nextRuleNodes == null) {
            nextRuleNodes = new ArrayList<>();
        }
        nextRuleNodes.add(ruleNode);
    }
}
