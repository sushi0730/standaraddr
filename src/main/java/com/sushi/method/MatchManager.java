package com.sushi.method;

import com.sushi.pojo.BranchNode;
import com.sushi.pojo.RuleChain;
import com.sushi.pojo.RuleMatch;
import com.sushi.pojo.RuleNode;
import org.ansj.domain.Term;

import java.util.*;

/**
 * 实例管理类
 * 一个规则对应一个管理类
 * @Author sushi
 * @create 2019-11-18 11:15 PM
 */
public class MatchManager {
    //(ns_key,ns_map)的映射关系map
    private static Map<String, String> nsMap = new HashMap<>();
    static{
        initNsMap();
    }

    /**
     *
     * 初始化(ns_key,ns_map)的映射关系map
     */
    private static void initNsMap() {
        nsMap.put("沧林", "沧林路");
        nsMap.put("海发", "海发路");
    }

    private RuleChain ruleChain;
    //所有匹配实例
    private List<RuleMatch> matchList;

    public List<RuleMatch> getMatchList() {
        return matchList;
    }

    public void setMatchList(List<RuleMatch> matchList) {
        this.matchList = matchList;
    }

    public MatchManager(RuleChain ruleChain) {
        this.ruleChain = ruleChain;
        matchList = new ArrayList<>();
        matchList.add(new RuleMatch(ruleChain));
    }

    /**
     * 增加一个空实例
     */
    public void addOneRuleMatch(){
        matchList.add(new RuleMatch(ruleChain));
    }
    /**
     * 所有实例匹配单个分词
     * @param word
     */
    public void allMatchWord(Term word) {
        //遍历（当出现分支时，可能会增加实例个数）
        ListIterator<RuleMatch> iterator = matchList.listIterator();
        while (iterator.hasNext()) {
           matchWord(iterator,iterator.next(),word);
        }
    }

    /**
     * 单个实例匹配单个分词
     * @param curMatch
     */
    private void matchWord(ListIterator<RuleMatch> iterator, RuleMatch curMatch , Term word) {
        //未匹配结束的实例继续匹配
        if(curMatch.isFinishFlag()){
            return;
        }
        //获取当前匹配实例中的待执行规则
        RuleNode currentNode = curMatch.getCurrentNode();
        //如果遇到分支节点，增加实例，并且各实例的当前规则节点变为nextRuleNode，并迭代执行
        if (currentNode.isBranch()) {
            List<RuleNode> nextRuleNodes = ((BranchNode) currentNode).getNextRuleNodes();
            for (int i = nextRuleNodes.size() - 1; i >= 0; i--) {
                if(i==0){
                    curMatch.setCurrentNode(nextRuleNodes.get(i));
                    matchWord(iterator,curMatch,word);
                }else{
                    //copy 匹配实例，包括已命中words和当前的规则节点
                    RuleMatch newMatch = new RuleMatch(curMatch);
                    //规则节点变为nextRuleNode
                    newMatch.setCurrentNode(nextRuleNodes.get(i));
                    iterator.add(newMatch);
                    matchWord(iterator,newMatch,word);
                }
            }
            //如果是规则节点，则匹配，
        }else{
            //如果匹配成功，将word加入命中列表
            if(word.getNatureStr().equals(curMatch.getCurrentNode().getRule().getType())){
                curMatch.addMatchWord(word);
//              并且实例的当前规则节点变为nextRuleNode
                curMatch.setCurrentNode(curMatch.getCurrentNode().getNextRuleNode());
            //如果（非附加节点）匹配失败,将匹配实例置为结束状态，
            }else{
                //如果是附加节点，那么规则节点变为nextRuleNode 且当前word不变，即回调本身
                if(curMatch.getCurrentNode().isExt()){
                    curMatch.setCurrentNode(curMatch.getCurrentNode().getNextRuleNode());
                    matchWord(iterator,curMatch,word);
                }else{
                    curMatch.setFinishFlag(true);
//                  iterator.add(new RuleMatch(curMatch.getRuleChain()));
                }
            }
        }
    }

    /**
     * 清除内部所有实例
     */
    public void clear(){
        if(matchList != null){
            matchList.clear();
        }
    }

    /**
     * 获取匹配最长的结果
     *
     * @return
     */
    public RuleMatch getMostLongWords() {
        RuleMatch longWords = null;
        for (RuleMatch ruleMatch : matchList) {
            if (longWords == null) {
                longWords =ruleMatch;
            }else if(longWords.getMatchList().size() < ruleMatch.getMatchList().size()){
                longWords = ruleMatch;
            }
        }
        return longWords;
    }

    /**
     * 将所有实例置为 已结束状态,并清除 无匹配的结果
     */
    public void finishAllRuleMatch() {
        Iterator<RuleMatch> iterator = matchList.iterator();
        while (iterator.hasNext()) {
            RuleMatch next = iterator.next();
            if(next.getMatchList().size()>0){
                iterator.next().setFinishFlag(true);
            }else{
                iterator.remove();
            }
        }

    }

    /**
     * 获取最长匹配串的匹配地址
     * 同时进行特殊处理：
     * ns_key替换成ns
     * m接mq或m结尾 补上合适的q
     * #替换成合适的q
     * @return
     */
    public String getHighScoreString() {
        RuleMatch highScore = getHighScoreResult();
        if(highScore == null ){
            return null;
        }
        //输出结果
        List<Term> result = highScore.getMatchList();
        StringBuffer sb = new StringBuffer();
        for (int i = 0 ;i<result.size();i++) {
            Term term = result.get(i);
            //ns_key替换成ns
            if(term.getNatureStr().equals("ns_key") ){
                sb.append(nsMap.get(term.getName()));
                term.setName(nsMap.get(term.getName()));
            }
            //m接mq或m结尾 补上合适的q
            else if(term.getNatureStr().equals("m") && (i == result.size()-1||!result.get(i+1).getNatureStr().equals("q"))){
                sb.append(term.getName()).append(getSuitQ(highScore,i));
                term.setName(term.getName()+getSuitQ(highScore,i));
            } else if(term.getName().equals("#")){
                sb.append(getSuitQ(highScore,i));
                term.setName(getSuitQ(highScore,i));
            } else {
                sb.append(term.getName());
            }

        }
        return sb.toString();
    }

    /**
     * 获取合适的量词
     * 暂时 只返回 号或者室
     * @param highScore
     * @param index 词出现的坐标
     * @return
     */
    private String getSuitQ(RuleMatch highScore, int index) {
        boolean existShi = false;
        boolean existHao = false;
        List<Term> matchList = highScore.getMatchList();
        for (int i = 0; i < matchList.size(); i++) {
            if(matchList.get(i).getNatureStr().equals("q") || matchList.get(i).getNatureStr().equals("mq")) {
                if(matchList.get(i).getName().contains("室")){
                    existShi = true;
                }if(matchList.get(i).getName().contains("号") && i<index){
                    existHao = true;
                }

            }
        }
        if(!existShi && existHao){
            return "室";
        }
        return "号";
    }

    /**
     * 获取最高分的结果
     * 暂时 最高分即为最长
     * @return
     */
    private RuleMatch getHighScoreResult() {
        return getMostLongWords();
    }

    /**
     * 获取最高分匹配的分数
     * 暂定 最高分的长度
     * @return
     */
    public int getHighScore() {
        return getHighScoreResult().getMatchList().size();
    }
}
